package com.example.sportivesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Activity that you see when you start the app.
 *  For go to mainActivity.
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }
}