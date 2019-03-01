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
import com.polotechnologies.firebaseaio.data_models.RealtimeItems;
import com.polotechnologies.firebaseaio.data_models.StorageItems;

import java.util.ArrayList;
import java.util.List;

public class RealtimeRecyclerAdapter extends RecyclerView.Adapter<RealtimeRecyclerAdapter.ViewHolder> {

    Context mContext;
    private List<RealtimeItems> mRealTimeItems = new ArrayList<>();

    public RealtimeRecyclerAdapter(Context mContext, List<RealtimeItems> mRealTimeItems) {
        this.mContext = mContext;
        this.mRealTimeItems = mRealTimeItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view =LayoutInflater.from(mContext).inflate(R.layout.card_realtime_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RealtimeItems realTimeItemsList = mRealTimeItems.get(i);

        viewHolder.realTimeMainImageName.setText(realTimeItemsList.getRealtimeImageName());
        Glide.with(mContext)
                .load(realTimeItemsList.getRealtimeImageUrl())
                .into(viewHolder.realTimeMainImage);


    }

    @Override
    public int getItemCount() {
        return mRealTimeItems.size();
    }

    public void addStorage (List<RealtimeItems> realTimeItems){
        mRealTimeItems = realTimeItems;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView realTimeMainImageName;
        ImageView realTimeMainImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            realTimeMainImageName = itemView.findViewById(R.id.realtimeName);
            realTimeMainImage = itemView.findViewById(R.id.realtimeImage);
        }
    }
}
