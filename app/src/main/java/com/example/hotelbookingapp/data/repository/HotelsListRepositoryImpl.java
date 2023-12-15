package com.example.hotelbookingapp.data.repository;


import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;
import com.example.hotelbookingapp.domain.repository.HotelsListRepository;
import com.example.hotelbookingapp.helper.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HotelsListRepositoryImpl implements HotelsListRepository {

    private final HotelsListApi api;

    @Inject
    public HotelsListRepositoryImpl(HotelsListApi api) {
        this.api = api;
    }

    @Override
    public Observable<HotelResponse> getHotelsList(String regionId, String checkIn, String checkOut) {
        return api.getHotelsList(
                regionId,
                "en_US",
                checkIn,
                "REVIEW",
                1,
                "US",
                checkOut,
                Constants.API_KEY
        );
    }
}
