package com.example.hotelbookingapp.presentation.hotels;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;
import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.domain.repository.FavoriteHotelsRepository;
import com.example.hotelbookingapp.domain.repository.HotelsListRepository;
import com.example.hotelbookingapp.domain.repository.RegionIdRepository;

import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@HiltViewModel
public class HotelsListViewModel extends ViewModel {
    private final HotelsListRepository hotelsListRepository;
    private final RegionIdRepository regionIdRepository;
    private final FavoriteHotelsRepository favoriteHotelsRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<Observable<HotelResponse>> hotelsList = new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();

    @Inject
    public HotelsListViewModel(
            HotelsListRepository hotelsListRepository,
            RegionIdRepository regionIdRepository,
            FavoriteHotelsRepository favoriteHotelsRepository
    ) {
        this.hotelsListRepository = hotelsListRepository;
        this.regionIdRepository = regionIdRepository;
        this.favoriteHotelsRepository = favoriteHotelsRepository;
    }

    public MutableLiveData<Observable<HotelResponse>> getHotelsList() {
        return hotelsList;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    public void getHotelsList(String cityName, String checkIn, String checkOut) {
        Log.d("hotelsList", "Started");
        Log.d("params", cityName + " " + checkIn + " " + checkOut);
        Disposable subscription = regionIdRepository.getGeoId(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response != null && !response.getData().isEmpty()) {
                                String regionId = response.getData().get(0).getGaiaId();
                                fetchHotelsList(regionId, checkIn, checkOut);
                            }
                        },
                        error -> {
                            if (error instanceof HttpException) {
                                HttpException httpException = (HttpException) error;
                                if (httpException.response() != null && Objects.requireNonNull(httpException.response()).errorBody() != null) {
                                    String errorResponse = Objects.requireNonNull(Objects.requireNonNull(httpException.response()).errorBody()).string();
                                    responseError.postValue(errorResponse);
                                } else {
                                    // Handle other HTTP-related errors (e.g., 4xx, 5xx).
                                    responseError.postValue("HTTP Error: " + httpException.code());
                                }
                            } else if (error instanceof IOException) {
                                // Handle network-related errors (e.g., no internet connection).
                                responseError.postValue("Network Error: No internet connection");
                            } else {
                                // Handle other types of errors.
                                responseError.postValue("Error: " + error.getMessage());
                            }
                        }
                );


        compositeDisposable.add(subscription);
    }

    private void fetchHotelsList(String regionId, String checkIn, String checkOut) {
        Log.d("params", regionId + " " + checkIn + " " + checkOut);
        Disposable subscription = hotelsListRepository.
                getHotelsList(regionId, checkIn, checkOut)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response != null && !response.getProperties().isEmpty()) {
                                hotelsList.postValue(Observable.just(response));
                            }
                        },
                        error -> {
                            HttpException httpException = (HttpException) error;
                            String errorResponse = Objects.requireNonNull(Objects.requireNonNull(httpException.response()).errorBody()).string();
                            Log.d("hotelsList", errorResponse);
                            responseError.postValue(errorResponse);
                        }
                );

        compositeDisposable.add(subscription);
    }

    public void addToFavorites(SingleHotelItem hotel) {
        favoriteHotelsRepository.saveHotelToFavorites(hotel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
