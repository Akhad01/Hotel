package com.example.hotelbookingapp.presentation.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.databinding.HistoryItemViewHolderBinding;

import java.time.LocalDate;
import java.util.List;

public class RvBookingHistoryAdapter extends RecyclerView.Adapter<RvBookingHistoryAdapter.BookedHotelViewHolder> {

    private static OnBookingClickListener listener;
    private final List<BookingDetails> bookingList;

    public RvBookingHistoryAdapter(List<BookingDetails> bookingList, OnBookingClickListener listener) {
        this.bookingList = bookingList;
        RvBookingHistoryAdapter.listener = listener;
    }

    @Override
    public BookedHotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookedHotelViewHolder(
                HistoryItemViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(BookedHotelViewHolder holder, int position) {
        holder.bind(bookingList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public interface OnBookingClickListener {
        void onBookingClick(BookingDetails bookingDetails);
    }

    public static class BookedHotelViewHolder extends RecyclerView.ViewHolder {

        private final HistoryItemViewHolderBinding binding;
        private BookingDetails bookedHotel;

        public BookedHotelViewHolder(HistoryItemViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.buttonCancelBooking.setOnClickListener(v -> {
                if (bookedHotel != null) {
                    listener.onBookingClick(bookedHotel);
                }
            });
        }

        public void bind(BookingDetails curBookedHotel) {
            binding.textViewBookedHotelName.setText(curBookedHotel.getHotelName());
            binding.textViewBookedCheckIn.setText(curBookedHotel.getCheckInDate());
            binding.textViewBookedCheckOut.setText(curBookedHotel.getCheckOutDate());

            if (datePassed(curBookedHotel.getCheckInDate())) {
                binding.buttonCancelBooking.setVisibility(View.GONE);
            }

            bookedHotel = curBookedHotel;
        }

        private boolean datePassed(String dateString) {
            LocalDate date = LocalDate.parse(dateString);
            return LocalDate.now().isAfter(date);
        }
    }
}
