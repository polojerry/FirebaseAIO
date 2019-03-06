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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.adapters.FirestoreRecyclerAdapter;
import com.polotechnologies.firebaseaio.data_models.FirestoreItems;
import com.polotechnologies.firebaseaio.ui.NewFirestoreActivity;
import com.polotechnologies.firebaseaio.ui.NewRealtimeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirestoreFragment extends Fragment {

    RecyclerView fireStoreRecyclerView;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;

    List<FirestoreItems> mFireStoreItems;
    FirebaseFirestore mFirestoreDb;

    FloatingActionButton newFirestore;

    Context mContext;


    public FirestoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mContext = getActivity().getApplicationContext();
        newFirestore = getActivity().findViewById(R.id.fabNewFirestore);
        fireStoreRecyclerView = getActivity().findViewById(R.id.fireStoreRecycler);

        fireStoreRecyclerView.setHasFixedSize(false);
        fireStoreRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mFireStoreItems = new ArrayList<>();

        mFirestoreDb = FirebaseFirestore.getInstance();

        getLatestData();

        newFirestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewFireStoreActivity = new Intent(getActivity(),NewFirestoreActivity.class);
                startActivity(startNewFireStoreActivity);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLatestData();
    }

    @Override
    public void onStart() {
        super.onStart();
        getLatestData();
    }

    public void getLatestData(){

        mFirestoreDb.collection("fireStoreImages").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        mFireStoreItems.clear();
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> dataSnapshots = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot documentSnapshot : dataSnapshots){
                                FirestoreItems firestoreItems = documentSnapshot.toObject(FirestoreItems.class);
                                mFireStoreItems.add(firestoreItems);
                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(mContext, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter(mContext,mFireStoreItems);
        fireStoreRecyclerView.setAdapter(firestoreRecyclerAdapter);
    }
}
