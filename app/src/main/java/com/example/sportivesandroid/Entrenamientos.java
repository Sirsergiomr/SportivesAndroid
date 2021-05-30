package com.example.sportivesandroid;

import android.os.Bundle;
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
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    }else{
                        Toast.makeText(Entrenamientos.this,"Tenemos un problema",Toast.LENGTH_LONG).show();
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


    public void lista_entrenamientos(){
        adapter = new Adapter_Entrenamientos(Entrenamientos.this, lista, this);
        rv_entrenamietos.setAdapter(adapter);
        rv_entrenamietos.setLayoutManager(new LinearLayoutManager(Entrenamientos.this));
    }
}
