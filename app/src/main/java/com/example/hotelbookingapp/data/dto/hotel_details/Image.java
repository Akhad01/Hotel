package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("image")
    private ImageDetails image;

    public ImageDetails getImage() {
        return image;
    }
}
