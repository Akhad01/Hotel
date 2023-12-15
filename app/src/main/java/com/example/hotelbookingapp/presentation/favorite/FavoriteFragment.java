package com.example.hotelbookingapp.presentation.favorite;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.databinding.FragmentFavoriteBinding;
import com.example.hotelbookingapp.helper.Constants;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteHotelsRvAdapter rvAdapter;
    private FavoriteHotelsViewModel viewModel;
    private Disposable subscription;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FavoriteHotelsViewModel.class);
        viewModelOutputs();
        binding.swipeRefreshLayout.setOnRefreshListener(this::refreshFragment);
    }

    private void viewModelOutputs() {
        viewModel.getHotelsList().observe(getViewLifecycleOwner(), hotelResponseObservable -> {
            if (hotelResponseObservable != null) {
                subscription = hotelResponseObservable.subscribe(
                        response -> {
                            if (response != null && !response.isEmpty()) {
                                displayHotels(response);
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

    private void handleError(String error) {
        if (error != null) {
            binding.loadingGif.setVisibility(View.GONE);
            binding.rvFavoriteHotels.setVisibility(View.GONE);
            binding.textViewError.setVisibility(View.VISIBLE);
            binding.textViewError.setText(error);
        }
    }


    private void displayHotels(List<SingleHotelItem> hotelData) {
        binding.loadingGif.setVisibility(View.GONE);
        binding.rvFavoriteHotels.setVisibility(View.VISIBLE);
        binding.textViewError.setVisibility(View.GONE);
        rvAdapter = new FavoriteHotelsRvAdapter(hotelData, this::navToDetails, this::showConfirmationDialog);
        binding.rvFavoriteHotels.setHasFixedSize(false);
        binding.rvFavoriteHotels.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavoriteHotels.setAdapter(rvAdapter);
    }


    private void showConfirmationDialog(String hotelName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove from Favorites?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            viewModel.deleteHotelFromFav(hotelName);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void navToDetails(SingleHotelItem data) {
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.SCORE_KEY, data.getReview());
        bundle.putString(Constants.HOTEL_ID_KEY, data.getId());
        NavHostFragment.findNavController(this).navigate(
                R.id.nav_fav_to_details,
                bundle
        );
    }

    private void refreshFragment() {
       binding.swipeRefreshLayout.setRefreshing(false);
       viewModel.refresh();
       viewModelOutputs();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        if (rvAdapter != null) {
            rvAdapter = null;
        }
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}