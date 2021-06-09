package com.example.sportivesandroid.Requests;

import android.app.Activity;
import android.content.Intent;

import com.example.sportivesandroid.LoginActivity;
import com.example.sportivesandroid.MainActivity;
import com.example.sportivesandroid.NoConectionActivity;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;
import com.example.sportivesandroid.WebViewActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Made the login call
 *
 * @see UserServices
 * @see RetrofitClient
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class UserRequests {

    /**
     * Log a user by email and pasword
     * @param activity to go to main from login
     * @param email to set the user email
     * @param password to set the user password
     * @see LoginActivity
     * @see Tags use to take strigns
     */
    public static void login(final Activity activity, String email, String password){
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .login(ApiUtils.getOSAuthenticationWith(Tags.USER, email, Tags.PASSWORD, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String p = json.getString(Tags.RESULT);
                    System.out.println(json.toString());
                    if (p.contains(Tags.OK)) {
                        Preferences.setEmailUser(email);
                        Preferences.setToken(json.getString(Tags.TOKEN));
                        Preferences.setTipoSesion(json.getString(Tags.TIPO_SESION));
                        Preferences.setNameUser(json.getString(Tags.NAME));
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.finish();
                    }else{
                        ((LoginActivity) activity).enableLogin();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.ErrorResponse(t);
                ((LoginActivity) activity).enableLogin();
                Intent intent = new Intent(activity, NoConectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });
    }
}
