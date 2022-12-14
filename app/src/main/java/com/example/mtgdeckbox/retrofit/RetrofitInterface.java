package com.example.mtgdeckbox.retrofit;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

/**
 * This class allows the RetrofitClient to interface with the Scryfall API.
 * @author: Tom Barker
 */
public interface RetrofitInterface {

    /**
     * This method searches the API for a specific card and gets it as a JSON object.
     * @param CARD_URI a String containing the specific Card URI to be searched.
     * @return a Call of a Search Response containing the response we received
     * from the Scryfall API.
     */
    @GET("cards/{card_uri}")
    Call<SearchResponse> customSearch(@Path("card_uri") String CARD_URI);
}
