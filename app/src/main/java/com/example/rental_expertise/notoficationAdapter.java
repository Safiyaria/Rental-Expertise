package com.example.rental_expertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import Model.Renters;

public class notoficationAdapter  extends RecyclerView.Adapter<notoficationAdapter.viewHolder> {

    List<Renters> mData;
    Context mContext;

    public notoficationAdapter(List<Renters> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_list_layout, parent, false);
        return new viewHolder(view);
    }

    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.nam.setText(mData.get(position).getName());
        holder.contact.setText(mData.get(position).getContactNo());
        holder.adres.setText(mData.get(position).getAddress());

    }

    public int getItemCount() {
        return mData.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {

        public TextView nam, contact, adres;
        public Button showButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nam = itemView.findViewById(R.id.nam);
            contact = itemView.findViewById(R.id.cnctNo);
            adres = itemView.findViewById(R.id.adres);
            showButton = itemView.findViewById(R.id.showBtn);

        }
    }

}