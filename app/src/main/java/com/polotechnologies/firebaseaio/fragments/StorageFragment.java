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
import com.google.firebase.database.ValueEventListener;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.adapters.StorageRecyclerAdapter;
import com.polotechnologies.firebaseaio.data_models.StorageItems;
import com.polotechnologies.firebaseaio.ui.NewStorageActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    RecyclerView storageRecyclerView;
    StorageRecyclerAdapter storageRecyclerAdapter;
    List<StorageItems> mStorageItems;
    DatabaseReference mDatabaseReference;

    FloatingActionButton newStorage;

    Context mContext;


    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity().getApplicationContext();
        newStorage = getActivity().findViewById(R.id.fabNewStorage);
        storageRecyclerView = getActivity().findViewById(R.id.storageRecycler);

        storageRecyclerView.setHasFixedSize(false);
        storageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mStorageItems = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("FirebaseAIO/storageImages");
        getLatestData();


        newStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewStorageActivity = new Intent(getActivity(),NewStorageActivity.class);
                startActivity(startNewStorageActivity);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getLatestData();

    }

    public void getLatestData(){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mStorageItems.clear();
                for (DataSnapshot storageDataSnapshot : dataSnapshot.getChildren()){
                    StorageItems latestStoredItems = storageDataSnapshot.getValue(StorageItems.class);

                    mStorageItems.add(latestStoredItems);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        storageRecyclerAdapter = new StorageRecyclerAdapter(mContext,mStorageItems);
        storageRecyclerView.setAdapter(storageRecyclerAdapter);
    }
}
