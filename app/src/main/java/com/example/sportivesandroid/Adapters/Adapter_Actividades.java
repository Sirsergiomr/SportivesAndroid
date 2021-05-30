package com.example.sportivesandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.Entrenamientos;
import com.example.sportivesandroid.MainActivity;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Tags;
import com.example.sportivesandroid.ui.qr.qrFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Actividades extends RecyclerView.Adapter<com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder> {
    private JSONArray lista;
    private Context context;
    private Activity activity;
    private Fragment fragment;
    public Adapter_Actividades(Context context, JSONArray lista, Activity activity, Fragment fragment) {
        this.context = context;
        this.lista = lista;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder holder = new com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.sportivesandroid.Adapters.Adapter_Actividades.AdaptadorViewHolder holder, int position) {
        try {
            JSONObject entrada = (JSONObject) lista.get(position);
            String fecha = entrada.getString(Tags.FECHA);
            String pk = entrada.getString(Tags.PK);
            holder.tv_nombre.setText(entrada.getString(Tags.NOMBRE));
            holder.tv_fecha.setText(fecha);
            holder.layout_item.setOnClickListener(v -> {
                System.out.println("Entrenamientos de día " + fecha);
                Intent entrenamientos = new Intent(context, Entrenamientos.class);
                entrenamientos.putExtra("fecha", fecha);
                context.startActivity(entrenamientos);
            });

            holder.iv_delete_2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    DialogX xd = new DialogX(activity, R.layout.dialog_message_title);
                    xd.dialog_confirmacion("Aceptar", "Cancelar",
                            "¿Borrar Actividad?", "Si borra una actividad se borrarán todos los entrenamientos.", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    eraser_activity(pk, fecha);
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
    }


    @Override
    public int getItemCount() {
        return lista.length();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout_item;
        TextView tv_nombre, tv_fecha;
        ImageView iv_delete_2;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.actividad_row);
            tv_nombre = itemView.findViewById(R.id.tv_actividad);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_actividad);
            iv_delete_2 = itemView.findViewById(R.id.iv_delete_2);
        }
    }


    public void eraser_activity(String actividad_id, String fecha) {
        JSONObject data = new JSONObject();
        try {
            data.put("actividad_id", actividad_id);
            data.put("fecha", fecha);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .eraser_activity(ApiUtils.getAuthenticationWhith(data));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject respuesta = new JSONObject(response.body());
                    String resultado = respuesta.getString(Tags.RESULT);
                    if (resultado.equals(Tags.OK)) {
                        notifyDataSetChanged();
                        Toast.makeText(Sportives.getContext(), "Se ha podido eliminar esta actividad", Toast.LENGTH_LONG).show();
                        if (fragment instanceof qrFragment) {
                            qrFragment qrf = (qrFragment) fragment;
                            qrf.milist();
                        }
                    } else {
                        Toast.makeText(Sportives.getContext(), "No se ha podido eliminar esta actividad", Toast.LENGTH_LONG).show();
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
