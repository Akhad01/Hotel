package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.hotel_details.HotelDetailsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HotelDetailsApi {
    @GET("hotels/details")
    Observable<HotelDetailsResponse> getHotelDetailsById(
        @Query("domain") String domain,
        @Query("hotel_id") String hotelId,
        @Query("locale") String locale,
        @Query("rapidapi-key") String apiKey
    );
}
