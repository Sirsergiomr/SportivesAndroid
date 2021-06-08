package com.example.sportivesandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Functions;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Anuncios extends RecyclerView.Adapter<Adapter_Anuncios.AdaptadorViewHolder>{
    JSONArray lista;
    Context context;
    Activity activity;
    FragmentManager fm;
    private final static int WEB_VIEW_REQUEST = 321;
    DialogX xd;

    public Adapter_Anuncios(Context context, JSONArray lista, FragmentManager fm, Activity activity){
        this.lista=lista;
        this.context=context;
        this.fm = fm;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oferta_info,parent,false);
        AdaptadorViewHolder holder = new AdaptadorViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {

        try {
            JSONObject entrada = (JSONObject) lista.get(position);

            String descripcion = entrada.getString("descripcion");
            String nombre_anuncio= entrada.getString("nombre");
            int precio = entrada.getInt("precio");
            String pk_anuncio = entrada.getString("pk");
            System.out.println("Adater_Anuncios "+pk_anuncio+" : Comprobar precio: "+precio);

            holder.nombre.setText(nombre_anuncio);
            holder.descripcion.setText(descripcion);
            if(precio!=0){
                holder.precio.setText(precio+"");
                holder.tv_e.setVisibility(View.VISIBLE);
            }else{
                holder.precio.setText("");
                holder.tv_e.setVisibility(View.GONE);

            }
            String foto = entrada.getString("imagen");
            System.out.println("Imagen = "+foto);

            Glide.with(context)
                    .load(Tags.MEDIA + foto)
                    .placeholder(R.drawable.logo_sportives_titans_3)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Por tener la caché fallaba al cargar las fotos del recycler
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_sportives_titans_3)
                    .fitCenter()
                    .into(holder.imagen);

        holder.layout_item.setOnClickListener(v -> {
            if( holder.tv_descripción_oferta2.getVisibility()== View.GONE){

                holder.tv_descripción_oferta2.setVisibility(View.VISIBLE);
                holder.tv_descripción_oferta2.setText(descripcion);

                if(precio!=0){
                    holder.bt_contratar.setVisibility(View.VISIBLE);
                }
                holder.tv_title_2.setText(nombre_anuncio);
                holder.tv_title_2.setVisibility(View.VISIBLE);
                holder.descripcion.setVisibility(View.GONE);
                holder.nombre.setVisibility(View.GONE);
            }else{
                holder.tv_descripción_oferta2.setVisibility(View.GONE);
                holder.tv_title_2.setVisibility(View.GONE);
                holder.bt_contratar.setVisibility(View.GONE);
                holder.descripcion.setVisibility(View.VISIBLE);
                holder.nombre.setVisibility(View.VISIBLE);
            }
        });
            holder.bt_contratar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    xd = new DialogX(Sportives.getCurrentActivity(),R.layout.dialog_message_title);
                    xd.dialog_confirmacion("Aceptar", "Cancelar",
                            "¿Realizar Pago?", "La siguiente operación va ha realizar un cobro.", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (precio*100 <= 0 || precio*100 <100){
                                        Toast.makeText(context, R.string.precio_min, Toast.LENGTH_LONG).show();
                                    }else{
                                        pagar(pk_anuncio,precio*100);
                                    }
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
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(position == lista.length()){
            holder.nombre.setText("");
            holder.descripcion.setText("Ya no quedán anuncios :D");
            holder.precio.setText("");
            holder.tv_e.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.logo_sportives_titans_3)
                    .placeholder(R.drawable.logo_sportives_titans_3)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Por tener la caché fallaba al cargar las fotos del recycler
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_sportives_titans_3)
                    .fitCenter()
                    .into(holder.imagen);
        }

    }

    @Override
    public int getItemCount() {
        return lista.length()+1;
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout_item;
        TextView nombre, descripcion,precio,tv_descripción_oferta2, tv_title_2,tv_e;
        ImageView imagen;
        Button bt_contratar;
        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tv_name_oferta);
            descripcion = itemView.findViewById(R.id.tv_descripción_oferta);
            precio = itemView.findViewById(R.id.tv_precio_oferta);
            imagen = itemView.findViewById(R.id.iv_oferta);
            tv_descripción_oferta2 = itemView.findViewById(R.id.tv_descripción_oferta2);
            layout_item = itemView.findViewById(R.id.item_oferta);
            bt_contratar = itemView.findViewById(R.id.bt_contratar);
            tv_title_2 = itemView.findViewById(R.id.tv_name_oferta2);
            tv_e = itemView.findViewById(R.id.tv_euro);

        }
    }

    public void pagar(String pk_anuncio ,int precio){

        try {
            JSONObject data = new JSONObject();
            data.put("anuncio_id", pk_anuncio);
            data.put("total", precio);
            Call<String> call = RetrofitClient.getClient().create(UserServices.class)
            .hacer_pago(ApiUtils.getAuthenticationWhith(data));

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject json = new JSONObject(response.body());
                        String result = json.getString(Tags.RESULT);
                        String mensaje = json.getString(Tags.MESSAGE);
                        if (result.contains(Tags.OK)) {
                            Toast.makeText(context,"Todo ok",Toast.LENGTH_LONG).show();
                            xd.dismiss();
                        }else if(result.contains("001")){
                            DialogX  xd = new DialogX(activity,R.layout.dialog_message_title);
                            xd.dialog_confirmacion("Cambiar tarjeta", "", "Tu tarjeta no ha sido aceptada", "Es necesario cambiar tu tarjeta, si no quieres simplemente cierra esta ventana",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(Preferences.getString(Tags.TARJETA)!=null && Preferences.getString(Tags.TARJETA)!=""){
                                                Functions.eraser_cards(fm);
                                            }
                                            Functions.crearTarjeta(Sportives.getCurrentActivity(),WEB_VIEW_REQUEST);
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            xd.dismiss();
                                        }
                                    });
                        }else if(result.contains("error")){
                            DialogX xd = new DialogX(activity,R.layout.dialog_message_title);
                            xd.dialog_confirmacion("Aceptar", "", "¡Alerta!",mensaje,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            xd.dismiss();
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            xd.dismiss();
                                        }
                                    });                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
