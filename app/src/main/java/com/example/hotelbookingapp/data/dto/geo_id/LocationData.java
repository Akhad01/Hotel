package com.example.hotelbookingapp.data.dto.geo_id;

import com.google.gson.annotations.SerializedName;

public class LocationData {
    @SerializedName("gaiaId")
    private String gaiaId;

    public String getGaiaId() {
        return gaiaId;
    }
}
