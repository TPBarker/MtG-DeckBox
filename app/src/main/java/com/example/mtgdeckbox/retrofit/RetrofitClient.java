package com.example.mtgdeckbox.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is the RetrofitClient, which is used to send calls to the
 * Scryfall API and collect Card information.
 * @author: Tom Barker
 */
public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.scryfall.com/";

    /**
     * This method builds and returns a new RetrofitInterface.
     * @return a RetrofitInterface, to be used to send calls to the API.
     */
    public static RetrofitInterface getRetrofitService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitInterface.class);
    }
}
