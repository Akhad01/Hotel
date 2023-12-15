package com.example.hotelbookingapp.presentation.hotel_details;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.hotel_details.HotelDetailsResponse;
import com.example.hotelbookingapp.data.dto.hotel_details.Image;
import com.example.hotelbookingapp.data.dto.hotel_details.NearbyPOIsItems;
import com.example.hotelbookingapp.databinding.FragmentHotelDetailsBinding;
import com.example.hotelbookingapp.helper.Constants;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class HotelDetailsFragment extends Fragment {

    String hotelId;
    private FragmentHotelDetailsBinding binding;
    private HotelDetailsViewModel viewModel;
    private HotelImagesPagerAdapter viewPagerAdapter;
    private Disposable subscription;
    private double score = 0.0;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHotelDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HotelDetailsViewModel.class);
        assert getArguments() != null;
        hotelId = getArguments().getString(Constants.HOTEL_ID_KEY);
        score = getArguments().getDouble(Constants.SCORE_KEY);
        if (hotelId != null) {
            viewModelOutputs(hotelId);
        } else {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshFragment() {
        binding.loadingDetailsGif.setVisibility(View.VISIBLE);
        binding.layoutDetailsContainer.setVisibility(View.GONE);
        binding.textViewError.setVisibility(View.GONE);

        new Handler().postDelayed(() -> {
            // This code will run after a 3-second delay
            viewModelOutputs(hotelId);
            binding.loadingDetailsGif.setVisibility(View.GONE);
            binding.layoutDetailsContainer.setVisibility(View.VISIBLE);
        }, 3000); // 3000 milliseconds (3 seconds)
    }

    private void viewModelOutputs(String id) {
        viewModel.getHotelDetailsById(id);
        viewModel.getHotelsDetails().observe(getViewLifecycleOwner(), hotelResponseObservable -> {
            if (hotelResponseObservable != null) {
                subscription = hotelResponseObservable.subscribe(
                        response -> {
                            if (response != null) {
                                displayHotelDetails(response);
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
            binding.buttonRefresh.setVisibility(View.VISIBLE);
            binding.buttonRefresh.setOnClickListener(view -> refreshFragment());
            binding.layoutDetailsContainer.setVisibility(View.GONE);
            binding.textViewError.setVisibility(View.VISIBLE);
            binding.loadingDetailsGif.setVisibility(View.GONE);
            binding.textViewError.setText(error);
        }
    }


    private List<String> getImagesList(List<Image> images) {
        List<String> imagesList = new ArrayList<>();
        for (int i = 0; i < 10 && i < images.size(); i++) {
            imagesList.add(images.get(i).getImage().getUrl());
        }
        return imagesList;
    }

    private List<String> getNearByPlaces(List<NearbyPOIsItems> items) {
        List<String> placesList = new ArrayList<>();

        for (NearbyPOIsItems place : items) {
            String nearByPlace = place.getText() + " (" + place.getMoreInfo() + ")";
            placesList.add(nearByPlace);
        }
        return placesList;
    }

    private void displayHotelDetails(HotelDetailsResponse data) {
        Log.d("testing", data.getSummary().getName());
        binding.loadingDetailsGif.setVisibility(View.GONE);
        binding.layoutDetailsContainer.setVisibility(View.VISIBLE);
        List<String> images = getImagesList(data.getPropertyGallery().getImages());
        viewPagerAdapter = new HotelImagesPagerAdapter(requireContext(), images);
        binding.viewPagerHotelImages.setAdapter(viewPagerAdapter);
        binding.textHotelName.setText(data.getSummary().getName());
        binding.textAddress.setText(data.getSummary().getLocation().getAddress().getAddressLine());
        binding.textHotelDetails.setText(data.getSummary().getLocation().getWhatsAround().getEditorial().getContent().toString());
        binding.textPhoneNumber.setText(data.getSummary().getTelesalesPhoneNumber());
        binding.textTagline.setText(data.getSummary().getTagline());
        binding.ratingBar.setRating((float) (score / 2));
        Log.d("score", String.valueOf((float) score));
        List<String> nearbyPOIs = getNearByPlaces(data.getSummary().getNearbyPOIs().getItems());
        binding.textWhatsAround.setText(nearbyPOIs.toString());

        binding.buttonBookNow.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.HOTEL_NAME_KEY, data.getSummary().getName());
            NavHostFragment.findNavController(this).navigate(
                    R.id.nav_details_to_book, bundle
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewPagerAdapter = null;
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
