package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;

import java.util.List;

import io.reactivex.Observable;

public interface FavoriteHotelsRepository {

    void saveHotelToFavorites(SingleHotelItem hotel);

    void removeHotelFromFavorites(String hotelName);

    Observable<List<SingleHotelItem>> getHotelFromFavorites();
}
