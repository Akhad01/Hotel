package com.example.hotelbookingapp.data.dto.hotel;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("mark")
    private Object mark;

    @SerializedName("message")
    private String message;

    @SerializedName("theme")
    private String theme;

    @SerializedName("type")
    private String type;

    @SerializedName("__typename")
    private String typename;

    public Object getMark() {
        return mark;
    }

    public String getMessage() {
        return message;
    }

    public String getTheme() {
        return theme;
    }

    public String getType() {
        return type;
    }

    public String getTypename() {
        return typename;
    }
}
