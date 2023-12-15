package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NearbyPOIs {
    @SerializedName("items")
    private List<NearbyPOIsItems> items;

    public List<NearbyPOIsItems> getItems() {
        return items;
    }
}
