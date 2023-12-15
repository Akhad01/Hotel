package com.example.hotelbookingapp.presentation.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.FragmentHomeBinding;
import com.example.hotelbookingapp.helper.Constants;
import com.example.hotelbookingapp.helper.PopularLocations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private PopularCitiesRvAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setRecyclerViewAdapter();

        binding.buttonFindLocation.setOnClickListener(v -> {
            if (binding.editTextLocation.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Enter your destination", Toast.LENGTH_LONG).show();
            } else {
                String cityName = binding.editTextLocation.getText().toString();
                navToHotels(cityName);
            }
        });

        binding.imageButtonDateFrom.setOnClickListener(v -> showDatePickerDialog(binding.textViewCheckInDate));

        binding.imageButtonDateTo.setOnClickListener(v -> showDatePickerDialog(binding.textViewCheckOutDate));
    }

    private void showDatePickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (DatePicker view, int year, int month, int day) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, day);
                    String formattedDate = formatDate(selectedDate.getTime());
                    textView.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(date);
    }

    private void setRecyclerViewAdapter() {
        recyclerViewAdapter = new PopularCitiesRvAdapter(
                PopularLocations.generatePopularLocations(),
                cityName -> navToHotels(cityName.getName())
        );

        binding.recyclerPopularLocations.setHasFixedSize(true);
        binding.recyclerPopularLocations.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.recyclerPopularLocations.setAdapter(recyclerViewAdapter);
    }

    private void navToHotels(String cityName) {
        if (binding.textViewCheckInDate.getText().toString().isEmpty() ||
                binding.textViewCheckOutDate.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Enter check-in and check-out dates please", Toast.LENGTH_LONG).show();
        } else {
            String checkInDate = binding.textViewCheckInDate.getText().toString();
            String checkoutDate = binding.textViewCheckOutDate.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.LOCATION_KEY, cityName);
            bundle.putString(Constants.CHECK_IN_DATE_KEY, checkInDate);
            bundle.putString(Constants.CHECK_OUT_DATE_KEY, checkoutDate);
            NavHostFragment.findNavController(this).navigate(
                    R.id.nav_home_hotels,
                    bundle
            );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        recyclerViewAdapter = null;
    }
}
