package com.example.hotelbookingapp.data.dto.geo_id;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LocationIdResponse {
    @SerializedName("data")
    private List<LocationData> data;
    
    @SerializedName("query")
    private String query;

    public List<LocationData> getData() {
        return data;
    }

    public String getQuery() {
        return query;
    }
}
