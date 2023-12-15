package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.geo_id.LocationIdResponse;

import io.reactivex.Observable;

public interface RegionIdRepository {
    Observable<LocationIdResponse> getGeoId(String city);
}
