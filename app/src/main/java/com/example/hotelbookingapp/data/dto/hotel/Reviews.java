package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class Reviews {
    @SerializedName("score")
    private double score;

    @SerializedName("total")
    private Integer total;

    @SerializedName("__typename")
    private String typename;

    public double getScore() {
        return score;
    }

    public Integer getTotal() {
        return total;
    }

    public String getTypename() {
        return typename;
    }
}
