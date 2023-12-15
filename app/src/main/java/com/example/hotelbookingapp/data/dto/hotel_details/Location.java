package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("address")
    private Address address;

    @SerializedName("whatsAround")
    private WhatsAround whatsAround;

    public Address getAddress() {
        return address;
    }

    public WhatsAround getWhatsAround() {
        return whatsAround;
    }
}
