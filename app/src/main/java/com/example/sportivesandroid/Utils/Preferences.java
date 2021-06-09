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
    /**
     * Set a string into the SharedPreferences
     * @param key variable name
     * @param value variable
     * */
    public static void setString(String key, String value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key,value);
        editor.commit();
    }
    /**
     * Get a string from the SharedPreferences
     * @param key variable name
     * */
    public static String getString(String key){
        return getSharedPreferences().getString(key, "");
    }
    /**
     * Set a int into the SharedPreferences
     * @param key variable name
     * @param value variable
     * */
    public static void setInt(String key, int value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key,value);
        editor.commit();
    }
    /**
     * Get a int from the SharedPreferences
     * @param key variable name
     * */
    public static int getInt(String key){
        return getSharedPreferences().getInt(key,0);
    }
    /**
     * Set a boolean into the SharedPreferences
     * @param key variable name
     * @param value variable
     * */
    public static void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    /**
     * Get a boolean from the SharedPreferences
     * @param key variable name
     * */
    public static Boolean getBoolean(String key){
        return getSharedPreferences().getBoolean(key, false);
    }
    /**
     * to get OneSignal user for notifications
     * */
    public static String getOneSignalUser(){
        return getString(Tags.OSUSER);
    }
    /**
     * to get OneSignal register for notifications
     * */
    public static String getOneSignalRegistration(){
        return getString(Tags.OSREGISTRATION);
    }
    /**
     * to set user token
     * */
    public static void setToken(String token){
        setString(Tags.TOKEN, token);
    }
    /**
     * to get user token
     * */
    public static String getToken(){
        return getSharedPreferences().getString(Tags.TOKEN, null);
    }
    /**
     * to get user pk
     * */
    public static String getID(){
        String token = getToken();
        if(token != null && !token.equals("")) {
            return token.substring(0, token.indexOf("_"));
        }else{
            return -1 + "";
        }
    }
    /**
     * to set type session
     * */
    public static void setTipoSesion(String tipo_sesion){
        setString(Tags.TIPO_SESION, tipo_sesion);
    }
    /**
     * to get type session
     * */
    public static String getTipoSesion(){
        return getSharedPreferences().getString(Tags.TIPO_SESION, null);
    }
    /**
     * to set user type
     * */
    public static void setTipoUser(Boolean admin){
        setBoolean(Tags.TIPO_USUARIO, admin);
    }
    /**
     * to get user type
     * */
    public static Boolean getTipoUser(){
        return getBoolean(Tags.TIPO_USUARIO);
    }
    /**
     * to set user email
     * */
    public static void setEmailUser(String email){
        setString(Tags.EMAIL, email);
    }
    /**
     * to set Name user
     * */
    public static void setNameUser(String nameUser){
        setString(Tags.USERNAME, nameUser);
    }
    /**
     * to get Name user
     * */
    public static String getNameUser(){return getString(Tags.USERNAME);}
    /**
     * to get user email
     * */
    public static String getEmailUser(){
        return getString(Tags.EMAIL);
    }

    public static void setProfileImageVersion(int version){
        setInt(Tags.IMAGE, version);
    }

    public static int getProfileImageVersion(){
        return getInt(Tags.IMAGE);
    }
    /**
     * Log out need clear all user preferences
     *
     * */
    public static void clearUserPreferences(){
        setToken(null);
        setTipoSesion("");
        setTipoUser(false);
        setEmailUser(null);
        setString(Tags.TARJETA,null);
    }
}