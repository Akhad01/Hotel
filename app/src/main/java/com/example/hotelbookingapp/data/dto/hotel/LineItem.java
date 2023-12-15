package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class LineItem {
    @SerializedName("price")
    private PriceDetails price;

    public PriceDetails getPrice() {
        return price;
    }
}
