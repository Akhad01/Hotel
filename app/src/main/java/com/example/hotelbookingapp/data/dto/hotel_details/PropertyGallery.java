package com.example.hotelbookingapp.data.dto.hotel_details;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PropertyGallery {
    @SerializedName("images")
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }
}
