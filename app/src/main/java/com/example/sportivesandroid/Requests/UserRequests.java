package com.example.sportivesandroid.Requests;

import android.app.Activity;

import com.example.sportivesandroid.LoginActivity;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {
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
                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.ErrorResponse(t);
                activity.finish();
            }
        });
    }
}
