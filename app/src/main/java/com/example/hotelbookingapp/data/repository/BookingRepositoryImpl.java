package com.example.hotelbookingapp.data.repository;

import android.util.Log;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.domain.repository.BookingRepository;
import com.example.hotelbookingapp.helper.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;

public class BookingRepositoryImpl implements BookingRepository {

    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public BookingRepositoryImpl(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void bookHotel(BookingDetails bookingDetails) {
        firebaseFirestore.collection(Constants.BOOKING).document().set(bookingDetails);
    }

    @Override
    public Observable<List<BookingDetails>> getBookingHistory() {
        return Observable.create(emitter -> {
            String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            firebaseFirestore.collection(Constants.BOOKING)
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                List<BookingDetails> bookingDetailsList = querySnapshot.toObjects(BookingDetails.class);
                                emitter.onNext(bookingDetailsList);
                                emitter.onComplete();
                            } else {
                                emitter.onError(new IllegalStateException("Error getting booking history"));
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public void removeBookedHotel(String hotelName, String bookId) {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        // Create a query to filter documents by userId and hotelName
        CollectionReference collection = firebaseFirestore.collection(Constants.BOOKING);
        Query query = collection
                .whereEqualTo("userId", userId)
                .whereEqualTo("bookId", bookId)
                .whereEqualTo("hotelName", hotelName);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Get the reference to the document and delete it
                        DocumentReference documentReference = document.getReference();
                        documentReference.delete();
                    }
                }
            } else {
                // Handle the error if the task is not successful
                Exception exception = task.getException();
                if (exception != null) {
                    // Log the error or display an error message
                    Log.e("Firebase Error", "Error deleting documents: " + exception.getMessage());
                    // You can also show an error message to the user if appropriate
                }
            }
        });
    }


}
