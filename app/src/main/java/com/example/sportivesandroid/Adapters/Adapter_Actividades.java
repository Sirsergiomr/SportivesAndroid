package com.example.sportivesandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.Entrenamientos;
import com.example.sportivesandroid.MainActivity;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_Actividades extends RecyclerView.Adapter<com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder>{
        JSONArray lista;
        Context context;

        public Adapter_Actividades(Context context, JSONArray lista){
            this.context = context;
            this.lista = lista;
        }

        @NonNull
        @Override
        public com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad,parent,false);
            com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder holder = new com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder holder, int position) {
            try {
                JSONObject entrada = (JSONObject) lista.get(position);
                String fecha = entrada.getString(Tags.FECHA);
                holder.tv_nombre.setText(entrada.getString(Tags.NOMBRE));
                holder.tv_fecha.setText(fecha);
                holder.layout_item.setOnClickListener(v -> {
                    System.out.println("Entrenamientos de día "+fecha);
                    Intent entrenamientos = new Intent(context, Entrenamientos.class);
                    entrenamientos.putExtra("fecha",fecha);
                    context.startActivity(entrenamientos);
                });
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

            public AdaptadorViewHolder(@NonNull View itemView) {
                super(itemView);
                layout_item = itemView.findViewById(R.id.actividad_row);
                tv_nombre  = itemView.findViewById(R.id.tv_actividad);
                tv_fecha   = itemView.findViewById(R.id.tv_fecha_actividad);
            }
        }
    }
