package com.example.hotelbookingapp.presentation.booking;

import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.domain.repository.BookingRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingViewModel extends ViewModel {

    private final BookingRepository repository;

    @Inject
    public BookingViewModel(BookingRepository repository) {
        this.repository = repository;
    }

    public void bookHotel(BookingDetails bookingDetails) {
        repository.bookHotel(bookingDetails);
    }
}
