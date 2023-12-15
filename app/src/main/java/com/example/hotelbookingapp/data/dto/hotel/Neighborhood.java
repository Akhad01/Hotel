package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class Neighborhood {
    @SerializedName("name")
    private String name;

    @SerializedName("__typename")
    private String typename;

    public String getName() {
        return name;
    }

    public String getTypename() {
        return typename;
    }
}
