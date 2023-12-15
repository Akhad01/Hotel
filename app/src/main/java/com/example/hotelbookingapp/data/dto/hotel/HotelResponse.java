package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HotelResponse {
    @SerializedName("properties")
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }
}
