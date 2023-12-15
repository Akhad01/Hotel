package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Price {
    @SerializedName("displayMessages")
    private List<DisplayMessage> displayMessages;

    public List<DisplayMessage> getDisplayMessages() {
        return displayMessages;
    }
}
