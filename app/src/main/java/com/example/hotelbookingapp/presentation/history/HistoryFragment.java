package com.example.hotelbookingapp.presentation.history;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.databinding.FragmentHistoryBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private RvBookingHistoryAdapter rvAdapter;
    private Disposable subscription;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.swipeRefreshLayout.setOnRefreshListener(this::refreshFragment);
        viewModelOutputs();
    }

    private void viewModelOutputs() {
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        viewModel.getBookingListResponse().observe(getViewLifecycleOwner(), hotelResponseObservable -> {
            if (hotelResponseObservable != null) {
                subscription = hotelResponseObservable.subscribe(
                        response -> {
                            if (response != null && !response.isEmpty()) {
                                displayHistory(response);
                            } else {
                                viewModel.getResponseError().observe(getViewLifecycleOwner(), this::handleError);
                            }
                        },
                        error -> handleError(error.getMessage())
                );
            }
        });
        viewModel.getResponseError().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void refreshFragment() {
        binding.swipeRefreshLayout.setRefreshing(false);
        viewModel.refresh();
        viewModelOutputs();
    }

    private void handleError(String message) {
        if (message != null) {
            binding.loadingGif.setVisibility(View.GONE);
            binding.rvBookingHistory.setVisibility(View.GONE);
            binding.textViewError.setVisibility(View.VISIBLE);
            binding.textViewError.setText(message);
        }
    }

    private void displayHistory(List<BookingDetails> data) {
        binding.loadingGif.setVisibility(View.GONE);
        binding.rvBookingHistory.setVisibility(View.VISIBLE);
        binding.textViewError.setVisibility(View.VISIBLE);
        rvAdapter = new RvBookingHistoryAdapter(data, this::showConfirmationDialog);
        binding.rvBookingHistory.setHasFixedSize(true);
        binding.rvBookingHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBookingHistory.setAdapter(rvAdapter);
    }

    private void showConfirmationDialog(BookingDetails hotel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Are you sure you want to cancel?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            viewModel.removeBookedHotel(hotel.getHotelName(), hotel.getBookId());
            refreshFragment();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        rvAdapter = null;
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
