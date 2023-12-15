package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;

import io.reactivex.Observable;

public interface HotelsListRepository {
    Observable<HotelResponse> getHotelsList(String regionId, String checkIn, String checkOut);
}
