package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class PriceDetails {
    @SerializedName("formatted")
    private String priceTag;

    public String getPriceTag() {
        return priceTag;
    }
}
