package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class PropertyRating {
    @SerializedName("accessibility")
    private String accessibility;

    @SerializedName("rating")
    private int rating;

    @SerializedName("__typename")
    private String typename;

    public String getAccessibility() {
        return accessibility;
    }

    public int getRating() {
        return rating;
    }

    public String getTypename() {
        return typename;
    }
}
