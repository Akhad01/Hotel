package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class ImageDetails {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
}
