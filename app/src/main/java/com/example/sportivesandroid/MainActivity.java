package com.example.sportivesandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.sportivesandroid.Utils.Preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST_CODE = 0027;
    private boolean  loginActivo = false;
    private boolean  comingFromLogin = false;
    public FloatingActionButton bt_floating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Preferences.getToken() == null && !loginActivo) {
            loginActivo = true;
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE){
            loginActivo = false;
            comingFromLogin = true;
        }
    }
    public void ComprobarLogin(){
        System.out.println("CompobarLogin MainActivity ");
        if (Preferences.getToken() == null && !loginActivo && !comingFromLogin) {
            loginActivo= true;
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST_CODE);
            comingFromLogin = false;
        }else{
            //Que quieres cargar  al main si el login esta ok
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ComprobarLogin();
    }

}