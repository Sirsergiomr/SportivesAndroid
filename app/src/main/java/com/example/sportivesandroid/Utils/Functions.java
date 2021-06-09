package com.example.sportivesandroid.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.WebViewActivity;
import com.example.sportivesandroid.ui.usuario.UserFragment;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * When a method repeats, pass here to make it a static function
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 * */
public class Functions {
    /**
     * When you need to change a card it is necessary to erase the one that was before
     * */
    public static void eraser_cards(FragmentManager fm){
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .eraser_cards(ApiUtils.getBasicAuthentication());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result;
                    result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        Log.v("PerfilActivity", "Borrado de tarjetas exitoso");
                        Preferences.setString(Tags.TARJETA,null);
                    }else{
                        Log.v("PerfilActivity", "Error en borrado de tarjetas");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }

    /**
     * If you change your card the fragment need restart
     * @param pfm pass the parent fragment manager who control the framents  and replace the old shard with a new one
     * */
    public static void refreshFragment(FragmentManager pfm){
        UserFragment frag = new UserFragment();
        FragmentManager fm = pfm;
        fm.beginTransaction().replace(R.id.container_usu, frag).addToBackStack(null).commit();
    }

    public static void setDecorView(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }else{
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    /**
     * Get Tag URL_GUARDAR_TARJETA and user id, finally make a intent with WebViewActivity to create a new card
     * @param activity indicates current activity
     * @param requestCode to catch in the onresult
     * @see com.example.sportivesandroid.MainActivity
     * */
    public static void crearTarjeta(Activity activity, int requestCode){
        String url = Tags.URL_GUARDAR_TARJETA + Preferences.getID();
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(Tags.URL, url);
        activity.startActivityForResult(intent, requestCode);
    }
}
