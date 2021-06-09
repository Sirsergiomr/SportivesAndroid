package com.example.sportivesandroid.Models;

import com.example.sportivesandroid.Utils.Tags;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Card model
 * @see com.example.sportivesandroid.ui.usuario.UserFragment
 * */
public class Tarjeta {

    private String pk, caducidad, end_digits, titular, tipo;

    public Tarjeta(JSONObject json) {
        try {
            pk = json.getString(Tags.PK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            caducidad = json.getString("caducidad");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            end_digits = json.getString("end_digits");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            titular = json.getString("titular");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            tipo = json.getString("tipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public String getEnd_digits() {
        return end_digits;
    }

    public void setEnd_digits(String end_digits) {
        this.end_digits = end_digits;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
