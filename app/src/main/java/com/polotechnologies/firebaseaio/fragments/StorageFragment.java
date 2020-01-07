package com.polotechnologies.firebaseaio.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    Query query;

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

        getActivity().setTitle("Cloud Storage");

        storageRecyclerView.setHasFixedSize(false);
        storageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mStorageItems = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        getLatestData(new StorageFragment.OnDataReceiveCallback(){
            @Override
            public void onDataReceived(DataSnapshot mDataSnapshot) {
                mStorageItems.clear();
                for (DataSnapshot storedDataSnapshot : mDataSnapshot.getChildren()){
                    StorageItems latestStoredItems = storedDataSnapshot.getValue(StorageItems.class);;

                    mStorageItems.add(latestStoredItems);
                }

                storageRecyclerAdapter = new StorageRecyclerAdapter(mContext,mStorageItems);
                storageRecyclerView.setAdapter(storageRecyclerAdapter);
            }

        });


        newStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewStorageActivity = new Intent(getActivity(),NewStorageActivity.class);
                startActivity(startNewStorageActivity);
            }
        });

    }

    public interface OnDataReceiveCallback {
        void onDataReceived(DataSnapshot mDataSnapshot);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLatestData(new StorageFragment.OnDataReceiveCallback(){
            @Override
            public void onDataReceived(DataSnapshot mDataSnapshot) {
                mStorageItems.clear();
                for (DataSnapshot storedDataSnapshot : mDataSnapshot.getChildren()){
                    StorageItems latestStoredItems = storedDataSnapshot.getValue(StorageItems.class);;

                    mStorageItems.add(latestStoredItems);
                }

                storageRecyclerAdapter = new StorageRecyclerAdapter(mContext,mStorageItems);
                storageRecyclerView.setAdapter(storageRecyclerAdapter);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getLatestData(new StorageFragment.OnDataReceiveCallback(){
            @Override
            public void onDataReceived(DataSnapshot mDataSnapshot) {
                mStorageItems.clear();
                for (DataSnapshot storedDataSnapshot : mDataSnapshot.getChildren()){
                    StorageItems latestStoredItems = storedDataSnapshot.getValue(StorageItems.class);;

                    mStorageItems.add(latestStoredItems);
                }

                storageRecyclerAdapter = new StorageRecyclerAdapter(mContext,mStorageItems);
                storageRecyclerView.setAdapter(storageRecyclerAdapter);
            }

        });
    }

    private void getLatestData(final StorageFragment.OnDataReceiveCallback callback) {

        query = mDatabaseReference.child("FirebaseAIO/storageImages");
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
