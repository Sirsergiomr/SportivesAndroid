package com.example.sportivesandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.Entrenamientos;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Entrenamientos extends RecyclerView.Adapter<Adapter_Entrenamientos.AdaptadorViewHolder> {
    JSONArray lista;
    Context context;
    Entrenamientos entrenamientos;
    public Adapter_Entrenamientos(Context context, JSONArray lista, Entrenamientos entrenamientos) {
        this.context = context;
        this.lista = lista;
        this.entrenamientos = entrenamientos;
    }

    @NonNull
    @Override
    public Adapter_Entrenamientos.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrenamiento, parent, false);
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

            String entrenamiento_id = entrada.getString("pk");
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    System.out.println("Pk entrenamiento = " + entrenamiento_id);
                    DialogX xd = new DialogX(Sportives.getCurrentActivity(), R.layout.dialog_message_title);
                    xd.dialog_confirmacion("Aceptar", "Cancelar",
                            "¿Borrar Entrenamiento?", "La siguiente operación va ha borar este ejercicio.", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    eraser_entrenamiento(entrenamiento_id);
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
        TextView tv_nombre, tv_fecha, tv_hora_total, tv_hora;
        ImageView iv_delete;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.entrenamiento_row);
            tv_nombre = itemView.findViewById(R.id.tv_entrenamiento);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_entrenamiento);
            tv_hora_total = itemView.findViewById(R.id.tv_total_tiempo);
            tv_hora = itemView.findViewById(R.id.tv_hora_entrenamiento);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            layout_item = itemView.findViewById(R.id.entrenamiento_row);
        }
    }

    public void eraser_entrenamiento(String entrenamiento_id) {
        JSONObject data = new JSONObject();
        try {
            data.put("entrenamiento_id", entrenamiento_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .eraser_entrenamientos(ApiUtils.getAuthenticationWhith(data));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject respuesta = new JSONObject(response.body());
                    String resultado = respuesta.getString(Tags.RESULT);
                    if (resultado.equals(Tags.OK)) {
                        notifyDataSetChanged();
                        entrenamientos.milist();
                        Toast.makeText(Sportives.getContext(), "Se ha podido eliminar este entrenamiento", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Sportives.getContext(), "No se ha podido eliminar este entrenamiento", Toast.LENGTH_LONG).show();
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
