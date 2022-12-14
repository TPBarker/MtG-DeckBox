package com.example.mtgdeckbox.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class represents the SearchResponse which Retrofit will receive from
 * the Scryfall API, used to obtain the JSON object of a particular Card.
 * @author: Tom Barker
 */
public class SearchResponse {

    @SerializedName("image_uris")
    public ImageURI imageURI;

    /**
     * This is the Accessor method for the imageURI field, which will contain
     * a JSON object with all of the image URIs.
     * @return
     */
    public ImageURI getImageURI() {
        return imageURI;
    }

    /**
     * This is the Mutator method for the imageURI field, which will contain
     * a JSON object with all of the image URIs.
     * @param imageURI
     */
    public void setImageURI (ImageURI imageURI) {
        this.imageURI = imageURI;
    }
}
