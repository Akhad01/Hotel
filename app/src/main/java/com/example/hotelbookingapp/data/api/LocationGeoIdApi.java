package com.example.hotelbookingapp.data.api;


import com.example.hotelbookingapp.data.dto.geo_id.LocationIdResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationGeoIdApi {
    @GET("regions")
    Observable<LocationIdResponse> getGeoId(
            @Query("query") String city,
            @Query("domain") String domain,
            @Query("locale") String locale,
            @Query("rapidapi-key") String apiKey
    );
}
