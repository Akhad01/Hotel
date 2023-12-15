package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class PropertyImage {
    @SerializedName("image")
    private Image image;

    public Image getImage() {
        return image;
    }
}
