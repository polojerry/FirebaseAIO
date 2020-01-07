package com.polotechnologies.firebaseaio.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.polotechnologies.firebaseaio.R;
import com.polotechnologies.firebaseaio.data_models.StorageItems;

import java.util.ArrayList;
import java.util.List;

public class StorageRecyclerAdapter extends RecyclerView.Adapter<StorageRecyclerAdapter.ViewHolder> {

    Context mContext;
    private List<StorageItems> mStorageItems = new ArrayList<>();

    public StorageRecyclerAdapter(Context mContext, List<StorageItems> mStorageItems) {
        this.mContext = mContext;
        this.mStorageItems = mStorageItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view =LayoutInflater.from(mContext).inflate(R.layout.card_storage_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StorageItems storageItemsList = mStorageItems.get(i);

        Glide.with(mContext)
                .load(storageItemsList.getStorageUrl())
                .into(viewHolder.storageMainImage);


    }

    @Override
    public int getItemCount() {
        return mStorageItems.size();
    }

    public void addStorage (List<StorageItems> storageItems){
        mStorageItems = storageItems;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView storageMainImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storageMainImage = itemView.findViewById(R.id.storageImage);
        }
    }
}
