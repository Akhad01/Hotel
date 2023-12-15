package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HotelsListApi {
    @GET("hotels/search")
    Observable<HotelResponse> getHotelsList(
        @Query("region_id") String regionId,
        @Query("locale") String locale,
        @Query("checkin_date") String checkIn,
        @Query("sort_order") String sortOrder,
        @Query("adults_number") int adultNum,
        @Query("domain") String domain,
        @Query("checkout_date") String checkOut,
        @Query("rapidapi-key") String apiKey
    );

}
