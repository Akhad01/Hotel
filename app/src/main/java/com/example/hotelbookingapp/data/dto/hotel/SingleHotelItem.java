package com.example.hotelbookingapp.data.dto.hotel;

public class SingleHotelItem {
    private String userId;
    private String id;
    private String name;
    private String neighborhood;
    private String price;
    private String imageUrl;
    private Double review;

    public SingleHotelItem() {
        // Default constructor required for Gson serialization
    }

    public SingleHotelItem(String userId, String id, String name, String neighborhood, String price, String imageUrl, Double review) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.price = price;
        this.imageUrl = imageUrl;
        this.review = review;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }
}
