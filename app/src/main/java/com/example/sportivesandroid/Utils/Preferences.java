package com.example.sportivesandroid.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sportivesandroid.MainActivity;
import com.example.sportivesandroid.Sportives;
/**
 * SharedPreferences
 *
 * */
public class Preferences {
    private static Context context = Sportives.getContext();

    public static SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(String key){
        return getSharedPreferences().getString(key, "");
    }

    public static void setInt(String key, int value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(String key){
        return getSharedPreferences().getInt(key,0);
    }

    public static void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static Boolean getBoolean(String key){
        return getSharedPreferences().getBoolean(key, false);
    }
    public static String getOneSignalUser(){
        return getString(Tags.OSUSER);
    }

    public static String getOneSignalRegistration(){
        return getString(Tags.OSREGISTRATION);
    }

    public static void setToken(String token){
        setString(Tags.TOKEN, token);
    }

    public static String getToken(){
        return getSharedPreferences().getString(Tags.TOKEN, null);
    }

    public static String getID(){
        String token = getToken();
        if(token != null && !token.equals("")) {
            return token.substring(0, token.indexOf("_"));
        }else{
            return -1 + "";
        }
    }

    public static void setTipoSesion(String tipo_sesion){
        setString(Tags.TIPO_SESION, tipo_sesion);
    }

    public static String getTipoSesion(){
        return getSharedPreferences().getString(Tags.TIPO_SESION, null);
    }

    public static void setTipoUser(Boolean admin){
        setBoolean(Tags.TIPO_USUARIO, admin);
    }

    public static Boolean getTipoUser(){
        return getBoolean(Tags.TIPO_USUARIO);
    }

    public static void setEmailUser(String email){
        setString(Tags.EMAIL, email);
    }

    public static void setNameUser(String nameUser){
        setString(Tags.USERNAME, nameUser);
    }

    public static String getNameUser(){return getString(Tags.USERNAME);}

    public static String getEmailUser(){
        return getString(Tags.EMAIL);
    }

    public static void setProfileImageVersion(int version){
        setInt(Tags.IMAGE, version);
    }

    public static int getProfileImageVersion(){
        return getInt(Tags.IMAGE);
    }

    public static void clearUserPreferences(){
        setToken(null);
        setTipoSesion("");
        setTipoUser(false);
        setEmailUser(null);
        setString(Tags.TARJETA,null);
    }
}