package com.example.hotelbookingapp.data.repository;

import com.example.hotelbookingapp.data.api.LocationGeoIdApi;
import com.example.hotelbookingapp.data.dto.geo_id.LocationIdResponse;
import com.example.hotelbookingapp.domain.repository.RegionIdRepository;
import com.example.hotelbookingapp.helper.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RegionIdRepositoryImpl implements RegionIdRepository {

    private final LocationGeoIdApi api;

    @Inject
    public RegionIdRepositoryImpl(LocationGeoIdApi api) {
        this.api = api;
    }

    @Override
    public Observable<LocationIdResponse> getGeoId(String city) {
        return api.getGeoId(
                city,
                "AE",
                "en_GB",
                Constants.API_KEY
        );
    }
}
