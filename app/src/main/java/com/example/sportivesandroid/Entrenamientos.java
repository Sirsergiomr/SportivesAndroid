package com.example.sportivesandroid;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.Adapters.Adapter_Actividades;
import com.example.sportivesandroid.Adapters.Adapter_Entrenamientos;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Activity to list workouts .
 *
 * This activity lists the workouts for a specific date
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class Entrenamientos extends AppCompatActivity{
    private Adapter_Entrenamientos adapter;
    private RecyclerView rv_entrenamietos;
    JSONArray lista;
    String fecha="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamientos);

        Sportives.setCurrentActivity(this);

        if(getIntent().getExtras()!= null){
            fecha = getIntent().getExtras().getString("fecha");
        }

        rv_entrenamietos = findViewById(R.id.rv_entrenamientos);

        milist();
    }
    /**
     * lists the workouts and send this list to Adapter_Entrenamientos
     * @see Adapter_Entrenamientos
     * */
    public void milist(){
        JSONObject data = new JSONObject();

        try {
            data.put(Tags.FECHA,fecha);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .get_entrenamientos(ApiUtils.getAuthenticationWhith(data));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        lista =   json.getJSONArray(Tags.LISTA);
                        System.out.println("Lista = "+lista+"---------------------");
                        lista_entrenamientos();
                    }else if(result.contains("error")||result.contains("001")){
                        lista =   json.getJSONArray(Tags.LISTA);
                        System.out.println("Lista = "+lista+"---------------------");
                        lista_entrenamientos();
                        DialogX xd = new DialogX(Entrenamientos.this,R.layout.dialog_message_title);
                        xd.dialog_confirmacion("Aceptar", "", "¡Alerta!",json.get(Tags.MESSAGE).toString(),
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
                                });
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

    /**
     * Init the adapter and set on recyclerview
     * */
    public void lista_entrenamientos(){
        adapter = new Adapter_Entrenamientos(Entrenamientos.this, lista, this);
        rv_entrenamietos.setAdapter(adapter);
        rv_entrenamietos.setLayoutManager(new LinearLayoutManager(Entrenamientos.this));
    }
}
