package com.example.sportivesandroid.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.sportivesandroid.R;
import com.example.sportivesandroid.WebViewActivity;
import com.example.sportivesandroid.ui.usuario.UserFragment;

public class Functions {
    public static void refreshFragment(FragmentManager pfm){
        UserFragment frag = new UserFragment();
        FragmentManager fm = pfm;
        fm.beginTransaction().replace(R.id.container_usu, frag).addToBackStack(null).commit();
    }
    public static void setDecorView(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }else{
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public static void crearTarjeta(Activity activity, int requestCode){
        String url = Tags.URL_GUARDAR_TARJETA + Preferences.getID();
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(Tags.URL, url);
        activity.startActivityForResult(intent, requestCode);
    }
}
