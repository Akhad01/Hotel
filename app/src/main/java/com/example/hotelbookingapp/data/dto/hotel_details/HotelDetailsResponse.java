package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;

public class HotelDetailsResponse {
    @SerializedName("propertyGallery")
    private PropertyGallery propertyGallery;

    @SerializedName("summary")
    private SummaryDetailsResponseSummary summary;

    public PropertyGallery getPropertyGallery() {
        return propertyGallery;
    }

    public SummaryDetailsResponseSummary getSummary() {
        return summary;
    }
}
