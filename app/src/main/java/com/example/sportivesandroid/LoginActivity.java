package com.example.sportivesandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportivesandroid.Requests.UserRequests;
import com.example.sportivesandroid.Utils.Preferences;
/**
 * Activity for log users.
 *
 * This activity is used to log the user into the app.
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button bt_log;
    ImageView bt_show_password;
    TextView  tv_register;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);
        progressBar = findViewById(R.id.progressBar);
        bt_log = findViewById(R.id.bt_log);
        bt_show_password =findViewById(R.id.btVisiblePassword_log);

        tv_register = findViewById(R.id.tv_register);
        //If register is successful put the email on edit text..
        if(Preferences.getEmailUser() != null && !Preferences.getEmailUser().equals("")){
                et_email.setText(Preferences.getEmailUser());
        }
        //To see the password
        bt_show_password.setOnClickListener(v -> {
            if(et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                bt_show_password.setImageResource(R.drawable.ic_remove_red_eye);
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                bt_show_password.setImageResource(R.drawable.ic_visibility_off);
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        //To go to register activity
        tv_register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        //Try to enter
        bt_log.setOnClickListener(v -> {
            if(validateFields()){
                login();
            }
        });
    }
    /**
     * Try to validate the fields when you try to enter into the app.
     * */
    private boolean validateFields() {
        if (et_email.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_email_message), Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_password.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_password_message), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Change to see the progressbar and call login form UserRequest
     *
     * @see UserRequests
     * */
    private void login() {
        bt_log.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        bt_log.setEnabled(false);
        bt_log.setText("");
        Preferences.clearUserPreferences();
        UserRequests.login(LoginActivity.this, et_email.getText().toString(), et_password.getText().toString());
    }
    /**
     * Set enable button login
     * */
    public void enableLogin() {
        bt_log.setVisibility(View.VISIBLE);
        bt_log.setEnabled(true);
        bt_log.setText(getResources().getString(R.string.log));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

    }
}
