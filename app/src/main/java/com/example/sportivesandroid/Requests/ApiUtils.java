package com.example.sportivesandroid.Requests;

import android.widget.Toast;

import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Get JSONObject with different data for example basic data user or create a new JSONObject with new data.
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 */
public class ApiUtils {

    public static void ErrorResponse(Throwable t){
        String message = "Error de conexión";
        Toast.makeText(Sportives.getContext(), message, Toast.LENGTH_LONG).show();
    }
    /**
     * return a JSONObject with user_id, toke and add a JSONObject
     * @param json extra data to send
     * */
    public static JSONObject getAuthenticationWhith(JSONObject json){
        JSONObject data = getBasicAuthentication();
        try {
            for(int i=0;i<json.names().length();i++){
                data.put(json.names().get(i).toString(),json.get(json.names().get(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * return getBasicAuthentication (user_id and token)
     * */
    public static JSONObject getBasicAuthentication(){
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.TOKEN, Preferences.getToken());
            data.put(Tags.USER_ID, Preferences.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * return a JSONObject generated with two parameters, send a key1 with value1 and key2 with value2
     * @param key1 data name
     * @param key2 data name
     * @param value1 data for key 1
     * @param value2 data for key 2
     * */
    public static JSONObject getOSAuthenticationWith(String key1, String value1, String key2, String value2){
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.OSUSER, Preferences.getOneSignalUser());
            data.put(Tags.OSREGISTRATION, Preferences.getOneSignalRegistration());
            data.put(key1, value1);
            data.put(key2, value2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
