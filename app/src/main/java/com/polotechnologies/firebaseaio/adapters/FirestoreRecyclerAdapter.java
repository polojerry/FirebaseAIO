package com.polotechnologies.firebaseaio.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.data_models.FirestoreItems;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRecyclerAdapter extends RecyclerView.Adapter<FirestoreRecyclerAdapter.ViewHolder> {

    Context mContext;
    private List<FirestoreItems> mFireStoreItems = new ArrayList<>();

    public FirestoreRecyclerAdapter(Context mContext, List<FirestoreItems> mFireStoreItems) {
        this.mContext = mContext;
        this.mFireStoreItems = mFireStoreItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view =LayoutInflater.from(mContext).inflate(R.layout.card_firestore_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FirestoreItems fireStoreItemsList = mFireStoreItems.get(i);

        viewHolder.fireStoreMainImageName.setText(fireStoreItemsList.getFirestoreImageName());

        Glide.with(mContext)
                .load(fireStoreItemsList.getFirestoreImageUrl())
                .into(viewHolder.fireStoreMainImage);


    }

    @Override
    public int getItemCount() {
        return mFireStoreItems.size();
    }

    public void addStorage (List<FirestoreItems> FireStoreItems){
        mFireStoreItems = FireStoreItems;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView fireStoreMainImageName;
        ImageView fireStoreMainImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fireStoreMainImageName = itemView.findViewById(R.id.fireStoreImageName);
            fireStoreMainImage = itemView.findViewById(R.id.fireStoreImage);
        }
    }
}
