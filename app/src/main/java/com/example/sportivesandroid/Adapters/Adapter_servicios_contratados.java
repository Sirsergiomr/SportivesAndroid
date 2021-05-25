package com.example.sportivesandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataEditor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sportivesandroid.Entrenamientos;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_servicios_contratados extends RecyclerView.Adapter<Adapter_servicios_contratados.AdaptadorViewHolder>{
    JSONArray lista;
    Context context;

    public Adapter_servicios_contratados(Context context, JSONArray lista){
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        Adapter_servicios_contratados.AdaptadorViewHolder holder = new Adapter_servicios_contratados.AdaptadorViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        try {
            JSONObject entrada = (JSONObject) lista.get(position);
            String foto = entrada.getString("imagen");
            Glide.with(context)
                    .load(Tags.MEDIA + foto)
                    .placeholder(R.drawable.logo_sportives_titans_3)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Por tener la caché fallaba al cargar las fotos del recycler
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_sportives_titans_3)
                    .fitCenter()
                    .into(holder.imagen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return lista.length();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout_item;
        TextView tv_nombre, tv_fecha;
        ImageView imagen;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.iv_image);
        }
    }
}
