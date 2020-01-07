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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.polotechnologies.firebaseaio.R;

import com.polotechnologies.firebaseaio.data_models.FirestoreItems;
import com.polotechnologies.firebaseaio.ui.NewFirestoreActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirestoreFragment extends Fragment {

    RecyclerView fireStoreRecyclerView;

    List<FirestoreItems> mFireStoreItems;

    FirebaseFirestore mFirebaseFirestore;
    Query query;

    FloatingActionButton newFireStore;
    FirestoreRecyclerAdapter adapter;

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
        newFireStore = getActivity().findViewById(R.id.fabNewFireStore);
        fireStoreRecyclerView = getActivity().findViewById(R.id.fireStoreRecycler);

        getActivity().setTitle("Firestore Database");

        fireStoreRecyclerView.setHasFixedSize(false);
        fireStoreRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mFireStoreItems = new ArrayList<>();

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        loadData();
        newFireStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewFireStoreActivity = new Intent(getActivity(), NewFirestoreActivity.class);
                startActivity(startNewFireStoreActivity);
            }
        });

    }

    private void loadData() {

        query = FirebaseFirestore.getInstance().collection("cities");

        FirestoreRecyclerOptions<FirestoreItems> options = new FirestoreRecyclerOptions.Builder<FirestoreItems>()
                .setQuery(query, FirestoreItems.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<FirestoreItems, FireStoreHolder>(options) {
            @Override
            public void onBindViewHolder(FireStoreHolder holder, int position, FirestoreItems model) {

                Glide.with(mContext)
                        .load(model.getFirestoreImageUrl())
                        .into(holder.fireStoreMainImage);
                holder.fireStoreMainImageName.setText(model.getFirestoreImageName());

            }

            @Override
            public FireStoreHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.card_fire_store_item, group, false);

                return new FireStoreHolder(view);
            }
        };

        adapter.notifyDataSetChanged();
        fireStoreRecyclerView.setAdapter(adapter);

    }

    public class FireStoreHolder extends  RecyclerView.ViewHolder{

        TextView fireStoreMainImageName;
        ImageView fireStoreMainImage;
        //TextView fireStoreMainImageTime;


        public FireStoreHolder(@NonNull View itemView) {
            super(itemView);
            fireStoreMainImageName = itemView.findViewById(R.id.fireStoreImageName);
            fireStoreMainImage = itemView.findViewById(R.id.fireStoreImage);
            //fireStoreMainImageTime = itemView.findViewById(R.id.firestoreTime);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    public interface OnDataReceiveCallback {
        void onDataReceived(DataSnapshot mDataSnapshot);
    }

    private void getLatestData(final FirestoreFragment.OnDataReceiveCallback callback) {

    }


}
