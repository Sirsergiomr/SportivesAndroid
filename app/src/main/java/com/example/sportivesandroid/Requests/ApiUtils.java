package com.example.sportivesandroid.Requests;

import android.widget.Toast;

import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiUtils {

    public static void ErrorResponse(Throwable t){
        String message = "Error de conexión";
        Toast.makeText(Sportives.getContext(), message, Toast.LENGTH_LONG).show();
    }
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
