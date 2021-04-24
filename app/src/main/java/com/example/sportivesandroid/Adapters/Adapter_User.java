package com.example.sportivesandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.R;

public class Adapter_User extends RecyclerView.Adapter<Adapter_User.AdaptadorViewHolder>{

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio,parent,false);
        AdaptadorViewHolder holder = new AdaptadorViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout_item;
        TextView id, nom;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
