package com.polotechnologies.firebaseaio.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.adapters.RealtimeRecyclerAdapter;
import com.polotechnologies.firebaseaio.adapters.StorageRecyclerAdapter;
import com.polotechnologies.firebaseaio.data_models.RealtimeItems;
import com.polotechnologies.firebaseaio.data_models.StorageItems;
import com.polotechnologies.firebaseaio.ui.NewRealtimeActivity;
import com.polotechnologies.firebaseaio.ui.NewStorageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealtimeFragment extends Fragment {

    RecyclerView realtimeRecyclerView;
    RealtimeRecyclerAdapter realtimeRecyclerAdapter;
    List<RealtimeItems> mRealtimeItems;

    DatabaseReference mDatabaseReference;
    Query query;

    FloatingActionButton newRealtime;

    Context mContext;

    public RealtimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_realtime, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mContext = getActivity().getApplicationContext();
        newRealtime = getActivity().findViewById(R.id.fabNewRealTime);
        realtimeRecyclerView = getActivity().findViewById(R.id.realTimeRecycler);

        getActivity().setTitle("Real Time Database");

        realtimeRecyclerView.setHasFixedSize(false);
        realtimeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRealtimeItems = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        getLatestData(new StorageFragment.OnDataReceiveCallback(){
            @Override
            public void onDataReceived(DataSnapshot mDataSnapshot) {
                mRealtimeItems.clear();
                for (DataSnapshot storedRealtimeDataSnapshot : mDataSnapshot.getChildren()){
                    RealtimeItems latestRealtimeItems = storedRealtimeDataSnapshot.getValue(RealtimeItems.class);;

                    mRealtimeItems.add(latestRealtimeItems);
                }

                realtimeRecyclerAdapter = new RealtimeRecyclerAdapter(mContext,mRealtimeItems);
                realtimeRecyclerView.setAdapter(realtimeRecyclerAdapter);
            }

        });

        newRealtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewRealTimeActivity = new Intent(getActivity(),NewRealtimeActivity.class);
                startActivity(startNewRealTimeActivity);
            }
        });

    }

    public interface OnDataReceiveCallback {
        void onDataReceived(DataSnapshot mDataSnapshot);
    }


    private void getLatestData(final StorageFragment.OnDataReceiveCallback callback) {

        query = mDatabaseReference.child("FirebaseAIO/realTimeImages");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onDataReceived(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
