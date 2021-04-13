package com.example.sportivesandroid;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_password, et_new_password,et_email , et_name;
    private Button bt_registrarse;
    private ImageView bt_show_password, bt_show_newpassword;
    private TextView tv_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        et_new_password= findViewById(R.id.et_new_password);

        bt_registrarse = findViewById(R.id.bt_register);
        bt_show_password = findViewById(R.id.btVisiblePassword);
        bt_show_newpassword = findViewById(R.id.btVisibleNewPassword);

        tv_login = findViewById(R.id.tv_login);

        bt_show_password.setOnClickListener(v -> {
            showPassword(v);
        });
        bt_show_newpassword.setOnClickListener(v -> {
            showPassword(v);
        });

        bt_registrarse.setOnClickListener(v -> {
           if(validateFields()){
               register();
           }
        });

    }
    public void showPassword(View view){
        switch (view.getId()){
            case R.id.btVisibleNewPassword:
                if(et_new_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    bt_show_newpassword.setImageResource(R.drawable.ic_remove_red_eye);
                    et_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    bt_show_newpassword.setImageResource(R.drawable.ic_visibility_off);
                    et_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btVisiblePassword:

                if(et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    bt_show_password.setImageResource(R.drawable.ic_remove_red_eye);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    bt_show_password.setImageResource(R.drawable.ic_visibility_off);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }
    private boolean validateFields() {
        if (et_email.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_email_message), Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_name.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_name_message), Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_password.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_password_message), Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_new_password.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_password_message), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_password.getText().toString().compareTo(et_new_password.getText().toString()) != 0) {
            Toast.makeText(this, getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void register(){
        bt_registrarse.setEnabled(false);
        bt_registrarse.setText("");
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.EMAIL, et_email.getText().toString());
            data.put(Tags.NAME, et_name.getText().toString());
            data.put(Tags.PASSWORD, et_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .register(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        Preferences.setEmailUser(et_email.getText().toString());
                        Preferences.setNameUser(et_name.getText().toString());
//                        setResult(-1);
//                        finish();
                        Toast.makeText(Sportives.getContext(), json.getString(Tags.MESSAGE),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(Sportives.getContext(), json.getString(Tags.MESSAGE),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bt_registrarse.setEnabled(true);
                bt_registrarse.setText(getResources().getString(R.string.registrar));
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.ErrorResponse(t);
                bt_registrarse.setEnabled(true);
                bt_registrarse.setText(getResources().getString(R.string.registrar));
            }
        });
    }

}
