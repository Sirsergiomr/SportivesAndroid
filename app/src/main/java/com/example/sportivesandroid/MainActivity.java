package com.example.sportivesandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Utils.Functions;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.sequences.USequencesKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private boolean  loginActivo = false;
    private boolean  comingFromLogin = false;

    private FloatingActionButton bt_scan;
    private static final int LOGIN_REQUEST_CODE = 0027;
    private static final int QR_REQUEST_CODE = 8888;
    final int RequestCameraPermissionID = 1001;
    private final static int WEB_VIEW_REQUEST = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Preferences.getToken() == null && !loginActivo) {
            loginActivo = true;
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST_CODE);
        }else{
            comprobar_conexion();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        bt_scan = findViewById(R.id.bt_qr);

        bt_scan.setOnClickListener(v -> {
            cameraPermisions();
        });

        OneSignal. setLogLevel(OneSignal. LOG_LEVEL. VERBOSE, OneSignal. LOG_LEVEL. NONE);
        OneSignal. initWithContext(this);  OneSignal. setAppId(Tags.ONESIGNAL_APP_ID);

        Sportives.setCurrentActivity(this);
    }

    /**
     *
     * */
    private void cameraPermisions(){
        int estadoDePermiso = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED){
            startActivityForResult(new Intent(this, LectorActivity.class),QR_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
            System.out.println("PERMISOS");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE){
            loginActivo = false;
            comingFromLogin = true;
        }else if (requestCode == WEB_VIEW_REQUEST) {
            System.out.println("Comprobando activityresult 123");
            Functions.refreshFragment(getSupportFragmentManager());
            if (resultCode == 0) {
                System.out.println("Result code cero cargando datos....");
            }else if (resultCode == 3333){
                System.out.println("Comprobando activityresult 3333");
                Preferences.clearUserPreferences();
            }
        }
    }

    public void comprobar_conexion(){
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .get_anuncios(ApiUtils.getBasicAuthentication());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {

                    }else{
                        startActivity(new Intent(MainActivity.this, NoConectionActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                startActivity(new Intent(MainActivity.this, NoConectionActivity.class));
                finish();
            }
        });
    }

    public void ComprobarLogin(){
        System.out.println("CompobarLogin MainActivity ");
        if (Preferences.getToken() == null && !loginActivo && !comingFromLogin) {
            loginActivo= true;
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST_CODE);
            comingFromLogin = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ComprobarLogin();
        comprobar_conexion();
    }
}