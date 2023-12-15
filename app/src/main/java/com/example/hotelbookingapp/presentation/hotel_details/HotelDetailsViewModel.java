package com.example.hotelbookingapp.presentation.hotel_details;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel_details.HotelDetailsResponse;
import com.example.hotelbookingapp.domain.repository.HotelDetailsRepository;

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
public class HotelDetailsViewModel extends ViewModel {

    private final HotelDetailsRepository repository;
    private final MutableLiveData<Observable<HotelDetailsResponse>> hotelsDetails = new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public HotelDetailsViewModel(HotelDetailsRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Observable<HotelDetailsResponse>> getHotelsDetails() {
        return hotelsDetails;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    public void getHotelDetailsById(String hotelId) {
        Disposable subscription = repository.getHotelDetailsById(hotelId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        subscriptionResponse -> {
                            if (subscriptionResponse != null) {
                                hotelsDetails.postValue(Observable.just(subscriptionResponse));
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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
