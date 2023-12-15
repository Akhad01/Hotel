package com.example.hotelbookingapp.data.dto.hotel_booking;

public class BookingDetails {
    private String bookId;
    private String hotelName;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String checkInDate;
    private String checkOutDate;
    private int adultNumber;
    private int childrenNum;
    private String paymentType;
    private CardDetails cardDetails;

    public BookingDetails() {
        // Default constructor required for Gson serialization
    }

    public BookingDetails(String bookId,String hotelName, String userId, String firstName, String lastName, String email,
                          String phoneNum, String checkInDate, String checkOutDate, int adultNumber, int childrenNum,
                          String paymentType, CardDetails cardDetails) {
        this.bookId = bookId;
        this.hotelName = hotelName;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adultNumber = adultNumber;
        this.childrenNum = childrenNum;
        this.paymentType = paymentType;
        this.cardDetails = cardDetails;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(int adultNumber) {
        this.adultNumber = adultNumber;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(int childrenNum) {
        this.childrenNum = childrenNum;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
