package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DisplayMessage {
    @SerializedName("lineItems")
    private List<LineItem> lineItems;

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
