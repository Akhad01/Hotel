package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class PriceMessage {
    @SerializedName("__typename")
    private String typename;

    @SerializedName("value")
    private String value;

    public String getTypename() {
        return typename;
    }

    public String getValue() {
        return value;
    }
}
