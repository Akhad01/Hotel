package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class WhatsAround {
    @SerializedName("editorial")
    private Editorial editorial;

    @SerializedName("__typename")
    private String typename;

    public Editorial getEditorial() {
        return editorial;
    }

    public String getTypename() {
        return typename;
    }
}
