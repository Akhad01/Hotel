package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Editorial {
    @SerializedName("content")
    private List<String> content;

    public List<String> getContent() {
        return content;
    }
}
