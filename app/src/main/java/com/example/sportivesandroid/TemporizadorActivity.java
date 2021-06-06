package com.example.sportivesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemporizadorActivity extends AppCompatActivity {
    private String name = " ";
    private String id_maquina = null;
    private TextView tv_nombre, tv_tiempo;
    private ImageView bt_stop_timer, bt_play_timer, bt_pause_timer;
    private boolean start_stop = false;
    private Chronometer crono;
    private long timepause;
    DialogX dialog = null;
    Timer tiempo;
    TimerTask  reloj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("nombre_actividad") != null) {
                name = getIntent().getExtras().getString("nombre_actividad");
            }
            if (getIntent().getExtras().getString("id_maquina") != null) {
                id_maquina = getIntent().getExtras().getString("id_maquina");
            }
        }
        tiempo = new Timer();
        tv_nombre = findViewById(R.id.tv_nombre_temporizdor);
        tv_nombre.setText(name);

        crono =findViewById(R.id.chronometrer);
        crono.setFormat("00:%s");
        bt_stop_timer = findViewById(R.id.bt_stop_timer);
        bt_play_timer = findViewById(R.id.bt_play_timer);
        bt_pause_timer = findViewById(R.id.bt_pause_timer);
        if(start_stop ==false){
            crono.setBase(SystemClock.elapsedRealtime()-timepause);
            start_stop =true;
            crono.start();
            timerbackground();
        }
        bt_play_timer.setOnClickListener(v -> {
            if(start_stop ==false){
                crono.setBase(SystemClock.elapsedRealtime()-timepause);
                start_stop =true;
                crono.start();
            }
        });
        bt_pause_timer.setOnClickListener(v -> {
            start_stop = false;
            timepause = SystemClock.elapsedRealtime()-crono.getBase();
            crono.stop();
        });
        bt_stop_timer.setOnClickListener(v -> {
            start_stop = false;
            timepause = SystemClock.elapsedRealtime()-crono.getBase();
            crono.stop();
            dialogConfirmacion();
        });
    }
    private String dosdigitos(int x) {
        return (x<=9) ? ("0"+x) : String.valueOf(x);
    }

    /***
     *dialog de confirmación, ¿Terminar entrenamiento? SI NO, si pulsas que no pues sigue contando, es un dialog aparte con un comportamiento aparte;
     */
    public void dialogConfirmacion(){

        dialog = new DialogX(this,R.layout.dialog_message_title);
        dialog.dialog_confirmacion("Si", "No",
                                     "Cronómetro","¿Terminar entrenamieto?",
                                     v -> {
                                         crear_entrenamiento();
                                         reloj.cancel();
                                         },
                                     v -> {
                                         crono.setBase(SystemClock.elapsedRealtime()-timepause);
                                         start_stop =true;
                                         crono.start();
                                         dialog.dismiss();},
                                     v -> {
                                         dialog.dismiss();
                                     });}

    public void crear_entrenamiento(){
        Calendar c = Calendar.getInstance();
        String hora = dosdigitos(c.get(c.HOUR_OF_DAY))+":" +dosdigitos(c.get(c.MINUTE))+":"+ dosdigitos(c.get(c.SECOND));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        System.out.println("Duración entrenamiento = "+crono.getText().toString());
        System.out.println("Nombre Máquina/entrenamiento = "+tv_nombre.getText().toString());
        System.out.println("Id Máquina = "+id_maquina);
        System.out.println("Hora de creación del entrenamiento " + hora);
        System.out.println("Fecha de creación: "+formattedDate);

        JSONObject data = new JSONObject();

        try {
            data.put(Tags.NOMBRE_MAQUINA,name);
            data.put(Tags.ID_MAQUINA,id_maquina);
            data.put(Tags.FECHA,formattedDate);
            data.put(Tags.HORA,hora);
            data.put(Tags.TIEMPO_USO,crono.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject datos =  ApiUtils.getAuthenticationWhith(data);//Pasarémos estos datos

        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .registrar_entrenamiento(datos);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        Toast.makeText(TemporizadorActivity.this, "Todo ok",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(TemporizadorActivity.this, "Todo mal :V",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(TemporizadorActivity.this, LectorActivity.class);
        startActivity(back);
        reloj.cancel();
    }

    public void timerbackground(){
         reloj = new TimerTask() {
            @Override
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            crono.getBase();
                        }
                    });
            }
        };
        tiempo.schedule(reloj, 1000, 1000);
    }
}
