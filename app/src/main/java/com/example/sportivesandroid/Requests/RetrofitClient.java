package com.example.sportivesandroid.Requests;

import com.example.sportivesandroid.Utils.Tags;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
/**
 *Take the Client with the server url
 *
 * @see Tags
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 * */
public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Tags.SERVIDOR)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
