package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class SummaryDetailsResponseSummary {
    @SerializedName("id")
    private String id;

    @SerializedName("location")
    private Location location;

    @SerializedName("name")
    private String name;

    @SerializedName("nearbyPOIs")
    private NearbyPOIs nearbyPOIs;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("telesalesPhoneNumber")
    private String telesalesPhoneNumber;

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public NearbyPOIs getNearbyPOIs() {
        return nearbyPOIs;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTelesalesPhoneNumber() {
        return telesalesPhoneNumber;
    }
}
