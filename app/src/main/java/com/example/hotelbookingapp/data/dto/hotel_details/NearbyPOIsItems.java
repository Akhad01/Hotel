package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class NearbyPOIsItems {
    @SerializedName("moreInfo")
    private String moreInfo;

    @SerializedName("text")
    private String text;

    public String getMoreInfo() {
        return moreInfo;
    }

    public String getText() {
        return text;
    }
}
