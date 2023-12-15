package com.example.hotelbookingapp.data.repository;

import android.util.Log;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.domain.repository.FavoriteHotelsRepository;
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

public class FavoriteHotelsRepositoryImpl implements FavoriteHotelsRepository {

    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FavoriteHotelsRepositoryImpl(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void saveHotelToFavorites(SingleHotelItem hotel) {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String favHotelId = userId + hotel.getId();
        firebaseFirestore.collection(Constants.FAVORITE_HOTELS).document(favHotelId).set(hotel);
    }

    @Override
    public Observable<List<SingleHotelItem>> getHotelFromFavorites() {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        // Get the FireStore collection reference
        CollectionReference collection = firebaseFirestore.collection(Constants.FAVORITE_HOTELS);

        // Create a query to filter documents by userId
        Query query = collection.whereEqualTo("userId", userId);

        // Get the documents and handle the result
        return Observable.create(emitter -> query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<SingleHotelItem> hotelItems = querySnapshot.toObjects(SingleHotelItem.class);
                            emitter.onNext(hotelItems);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new IllegalStateException("Error getting hotels from favorites"));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public void removeHotelFromFavorites(String hotelName) {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        // Create a query to filter documents by userId and hotelName
        CollectionReference collection = firebaseFirestore.collection(Constants.FAVORITE_HOTELS);
        Query query = collection
                .whereEqualTo("userId", userId)
                .whereEqualTo("name", hotelName);

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
