package com.example.hotelbookingapp.presentation.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.data.repository.BookingRepositoryImpl;
import com.example.hotelbookingapp.domain.repository.BookingRepository;

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
public class HistoryViewModel extends ViewModel {

    private final BookingRepository bookingRepository;
    private final MutableLiveData<Observable<List<BookingDetails>>> bookingListResponse =
            new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public HistoryViewModel(BookingRepositoryImpl bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public MutableLiveData<Observable<List<BookingDetails>>> getBookingListResponse() {
        return bookingListResponse;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    private void getBookingHistory() {

        Disposable subscription = bookingRepository
                .getBookingHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response != null && !response.isEmpty()) {
                                bookingListResponse.postValue(Observable.just(response));
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

    public void refresh() {
        getBookingHistory();
    }

    public void removeBookedHotel(String hotelName, String bookId) {
        bookingRepository.removeBookedHotel(hotelName, bookId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
