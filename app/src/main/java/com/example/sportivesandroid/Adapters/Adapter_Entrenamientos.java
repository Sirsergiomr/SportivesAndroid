package com.example.sportivesandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_Entrenamientos extends RecyclerView.Adapter<Adapter_Entrenamientos.AdaptadorViewHolder>{
        JSONArray lista;
        Context context;

        public Adapter_Entrenamientos(Context context, JSONArray lista){
            this.context = context;
            this.lista = lista;
        }

        @NonNull
        @Override
        public Adapter_Entrenamientos.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrenamiento,parent,false);
            Adapter_Entrenamientos.AdaptadorViewHolder holder = new Adapter_Entrenamientos.AdaptadorViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter_Entrenamientos.AdaptadorViewHolder holder, int position) {
            try {
                JSONObject entrada = (JSONObject) lista.get(position);
                holder.tv_nombre.setText(entrada.getString("nombre_maquina"));
                holder.tv_hora_total.setText(entrada.getString("tiempo_uso"));
                holder.tv_fecha.setText(entrada.getString("fecha"));
                holder.tv_hora.setText(entrada.getString("hora"));
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
            TextView tv_nombre, tv_fecha, tv_hora_total,tv_hora ;

            public AdaptadorViewHolder(@NonNull View itemView) {
                super(itemView);
                layout_item = itemView.findViewById(R.id.entrenamiento_row);
                tv_nombre  = itemView.findViewById(R.id.tv_entrenamiento);
                tv_fecha   = itemView.findViewById(R.id.tv_fecha_entrenamiento);
                tv_hora_total = itemView.findViewById(R.id.tv_total_tiempo);
                tv_hora = itemView.findViewById(R.id.tv_hora_entrenamiento);
            }
        }
    }
