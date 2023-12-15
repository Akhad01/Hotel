package com.example.hotelbookingapp.presentation.favorite;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.domain.repository.FavoriteHotelsRepository;

import java.io.IOException;
import java.util.List;
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
public class FavoriteHotelsViewModel extends ViewModel {

    private final FavoriteHotelsRepository repository;
    private final MutableLiveData<Observable<List<SingleHotelItem>>> hotelsList = new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public FavoriteHotelsViewModel(FavoriteHotelsRepository repository) {
        this.repository = repository;
        getFavoriteHotels();
    }

    public MutableLiveData<Observable<List<SingleHotelItem>>> getHotelsList() {
        return hotelsList;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    void refresh(){
        getFavoriteHotels();
    }

    private void getFavoriteHotels() {
        Disposable subscription = repository.getHotelFromFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response != null && !response.isEmpty()) {
                                hotelsList.postValue(Observable.just(response));
                            } else responseError.postValue("No data found");
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

    public void deleteHotelFromFav(String hotelName) {
        repository.removeHotelFromFavorites(hotelName);
        getFavoriteHotels();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
