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
    @POST("api/v1/usuarios/get_perfil/")
    @FormUrlEncoded
    Call<String> loadUser(@Field("data") JSONObject data);
    @POST("api/v1/usuarios/get_inicio/")
    @FormUrlEncoded
    Call<String> getInicio(@Field("data") JSONObject data);
    @POST("api/v1/usuarios/registrar-usuario/")
    @FormUrlEncoded
    Call<String> register(@Field("data") JSONObject data);
    @POST("api/v1/usuarios/registro_login_google/")
    @FormUrlEncoded
    Call<String> loginGoogle(@Field("data") JSONObject data);
    @POST("usuarios/java/logout/")
    @FormUrlEncoded
    Call<String> logout(@Field("data") JSONObject data);
}
