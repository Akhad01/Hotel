package com.example.hotelbookingapp.data.dto.hotel_booking;

public class CardDetails {
    private int cardNum;
    private String expireDate;
    private int cvv;

    public CardDetails() {
        // Default constructor required for Gson serialization
    }

    public CardDetails(int cardNum, String expireDate, int cvv) {
        this.cardNum = cardNum;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
