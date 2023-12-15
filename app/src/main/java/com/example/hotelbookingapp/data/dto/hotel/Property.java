package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class Property {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("neighborhood")
    private Neighborhood neighborhood;

    @SerializedName("price")
    private Price price;

    @SerializedName("propertyImage")
    private PropertyImage propertyImage;

    @SerializedName("reviews")
    private Reviews reviews;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public Price getPrice() {
        return price;
    }

    public PropertyImage getPropertyImage() {
        return propertyImage;
    }

    public Reviews getReviews() {
        return reviews;
    }
}
