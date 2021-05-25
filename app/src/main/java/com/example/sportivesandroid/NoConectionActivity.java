package com.example.sportivesandroid;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.BatchUpdateException;

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
