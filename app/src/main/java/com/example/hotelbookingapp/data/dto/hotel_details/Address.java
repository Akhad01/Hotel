package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("addressLine")
    private String addressLine;

    public String getAddressLine() {
        return addressLine;
    }
}
