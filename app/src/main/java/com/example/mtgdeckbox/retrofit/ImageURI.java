package com.example.mtgdeckbox.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * This class allows the application to capture the 'image_uris' JSON object
 * from the Scryfall API and interpret it.
 */
public class ImageURI {

    @SerializedName("normal")
    public String normal;

    /**
     * This is the Accessor method for the normal field, which contains
     * the URL for the normal size image.
     * @return
     */
    public String getNormal() {
        return normal;
    }

    /**
     * This is the Mutator method for the normal field, which contains
     * the URL for the normal size image.
     * @param normal
     */
    public void setNormal(String normal) {
        this.normal = normal;
    }
}
