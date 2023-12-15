package com.example.hotelbookingapp.data.repository;

import com.example.hotelbookingapp.data.api.HotelDetailsApi;
import com.example.hotelbookingapp.data.dto.hotel_details.HotelDetailsResponse;
import com.example.hotelbookingapp.domain.repository.HotelDetailsRepository;
import com.example.hotelbookingapp.helper.Constants;

import io.reactivex.Observable;

public class HotelDetailsRepositoryImpl implements HotelDetailsRepository {

    private final HotelDetailsApi api;

    public HotelDetailsRepositoryImpl(HotelDetailsApi api) {
        this.api = api;
    }

    @Override
    public Observable<HotelDetailsResponse> getHotelDetailsById(String hotelId) {
        return api.getHotelDetailsById(
                "AE",
                hotelId,
                "en_GB",
                Constants.API_KEY
        );
    }
}
