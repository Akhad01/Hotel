package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel_details.HotelDetailsResponse;

import io.reactivex.Observable;

public interface HotelDetailsRepository {
    Observable<HotelDetailsResponse> getHotelDetailsById(String hotelId);
}
