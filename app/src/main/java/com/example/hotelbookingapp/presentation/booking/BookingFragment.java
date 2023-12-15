package com.example.hotelbookingapp.presentation.booking;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.data.dto.hotel_booking.CardDetails;
import com.example.hotelbookingapp.databinding.FragmentBookingBinding;
import com.example.hotelbookingapp.helper.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingFragment extends Fragment {
    @Inject
    FirebaseAuth firebaseAuth;
    private FragmentBookingBinding binding;
    private BookingViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        assert getArguments() != null;
        String hotelName = getArguments().getString(Constants.HOTEL_NAME_KEY);
        if (hotelName != null) {
            binding.textViewHotelName.setText(hotelName);
        }

        binding.buttonFinish.setOnClickListener(v -> {
            if (areAllFieldsFilled()) {
                bookHotel();
                Toast.makeText(requireContext(), "Booked", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
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

    private void bookHotel() {
        String hotelName = binding.textViewHotelName.getText().toString();
        String firstName = binding.editTextFirstName.getText().toString();
        String lastName = binding.editTextLastName.getText().toString();
        String email = binding.editTextEmail.getText().toString();
        String phoneNumber = binding.editTextPhoneNumber.getText().toString();
        String checkInDate = binding.textViewCheckInDate.getText().toString();
        String checkOutDate = binding.textViewCheckOutDate.getText().toString();
        int adultsNumber = parseNullableInt(binding.editTextAdultsNumber.getText().toString());
        int childrenNumber = parseNullableInt(binding.editTextChildrenNumber.getText().toString());
        String paymentType = getPaymentType();
        int cardNumber = parseNullableInt(binding.editTextCardNumber.getText().toString());
        String expiryDate = binding.editTextExpiryDate.getText().toString();
        int cvv = parseNullableInt(binding.editTextCVV.getText().toString());
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String bookId = generateRandomString();
        BookingDetails bookingDetails = new BookingDetails(
                bookId, hotelName, userId, firstName, lastName, email, phoneNumber, checkInDate, checkOutDate,
                adultsNumber, childrenNumber, paymentType, new CardDetails(cardNumber, expiryDate, cvv)
        );

        viewModel.bookHotel(bookingDetails);
    }

    private String generateRandomString() {
        return java.util.UUID.randomUUID().toString(); // Generate a random UUID string for the bookId field.
    }

    private boolean areAllFieldsFilled() {
        // Check if any of the required fields are empty
        return binding.editTextFirstName.getText().length() > 0 &&
                binding.editTextLastName.getText().length() > 0 &&
                binding.editTextAdultsNumber.getText().length() > 0 &&
                binding.editTextChildrenNumber.getText().length() > 0 &&
                binding.editTextPhoneNumber.getText().length() > 0 &&
                binding.editTextEmail.getText().length() > 0 &&
                binding.textViewCheckOutDate.getText().length() > 0 &&
                binding.textViewCheckOutDate.getText().length() > 0 &&
                (!binding.radioButtonCreditCard.isChecked() ||
                        (binding.editTextCardNumber.getText().length() > 0 &&
                                binding.editTextExpiryDate.getText().length() > 0 &&
                                binding.editTextCVV.getText().length() > 0));
    }

    private String getPaymentType() {
        // Get the selected payment type from RadioGroup
        return binding.radioButtonCreditCard.isChecked() ? "Credit Card" : "Cash";
    }

    private int parseNullableInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
