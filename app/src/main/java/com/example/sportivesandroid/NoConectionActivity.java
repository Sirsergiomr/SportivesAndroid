package com.example.sportivesandroid;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.BatchUpdateException;
/**
 * Activity that you see when you lost the connection.
 * Only have a button to come back and try again.
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class NoConectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nononection);
        Button bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

}
