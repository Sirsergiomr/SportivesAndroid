package com.example.sportivesandroid.Requests;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserServices {

    @POST("api/v1/usuarios/login/")
    @FormUrlEncoded
    Call<String> login(@Field("data") JSONObject data);

    @POST("api/v1/usuarios/get_inicio/")
    @FormUrlEncoded
    Call<String> loadUser(@Field("data") JSONObject data);

    @POST("api/v1/usuarios/registrar-usuario/")
    @FormUrlEncoded
    Call<String> register(@Field("data") JSONObject data);

    @POST("usuarios/java/registro_login_google/")
    @FormUrlEncoded
    Call<String> loginGoogle(@Field("data") JSONObject data);

    @POST("/api/v1/usuarios/cambiar_pass/")
    @FormUrlEncoded
    Call<String> cambiarPass(@Field("data") JSONObject data);
    @POST("/api/v1/usuarios/get_maquina/")
    @FormUrlEncoded
    Call<String> get_maquina(@Field("data") JSONObject data);

    @POST("/api/v1/usuarios/registrar_entrenamiento/")
    @FormUrlEncoded
    Call<String> registrar_entrenamiento(@Field("data") JSONObject data);

    @POST("/api/v1/usuarios/get_actividades/")
    @FormUrlEncoded
    Call<String> get_actividades(@Field("data") JSONObject data);

    @POST("/api/v1/usuarios/get_entrenamientos/")
    @FormUrlEncoded
    Call<String> get_entrenamientos(@Field("data") JSONObject data);
}
