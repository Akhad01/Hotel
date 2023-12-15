package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;

import java.util.List;

import io.reactivex.Observable;

public interface BookingRepository {

    void bookHotel(BookingDetails bookingDetails);

    Observable<List<BookingDetails>> getBookingHistory();

    void removeBookedHotel(String hotelName, String bookId);
}
