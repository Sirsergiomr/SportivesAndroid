package com.example.sportivesandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataEditor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sportivesandroid.Entrenamientos;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Functions;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_servicios_contratados extends RecyclerView.Adapter<Adapter_servicios_contratados.AdaptadorViewHolder>{
    JSONArray lista, transac;
    Context context;
    Activity activity;
    FragmentManager fm;
    public Adapter_servicios_contratados(Context context, JSONArray lista, Activity activity, JSONArray transac, FragmentManager fm){
        this.context = context;
        this.lista = lista;
        this.activity= activity;
        this.transac = transac;
        this.fm = fm;
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
            String descripcion = entrada.getString("descripcion");
            String nombre_anuncio= entrada.getString("nombre");
            Glide.with(context)
                    .load(Tags.MEDIA + foto)
                    .placeholder(R.drawable.logo_sportives_titans_3)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Por tener la caché fallaba al cargar las fotos del recycler
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_sportives_titans_3)
                    .fitCenter()
                    .into(holder.imagen);


            JSONObject entrada2 = (JSONObject) transac.get(position);
            String tr_pk = entrada2.getString("pk");

            holder.imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogX xd = new DialogX(activity,R.layout.dialog_message_title);
                    xd.dialog_confirmacion("Ok", "Borrar", nombre_anuncio, descripcion,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    xd.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    xd.dialog_confirmacion("Confirmar", "Cancelar", "¡Advertencia!", "Si te desuscribes no hay reembolso, ¿Estas seguro de querer hacer esto?",
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    erasertransaction(tr_pk);
                                                    xd.dismiss();
                                                }
                                            }, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    xd.dismiss();
                                                }
                                            }, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    xd.dismiss();
                                                }
                                            });
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    xd.dismiss();
                                }
                            });
                }
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
        ImageView imagen;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.iv_image);
        }
    }

    public void erasertransaction(String tr_pk){
        JSONObject data = new JSONObject();
        try {
            data.put("transacion_pk",tr_pk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .eraser_transaction(ApiUtils.getAuthenticationWhith(data));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        notifyDataSetChanged();
                        Functions.refreshFragment(fm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
