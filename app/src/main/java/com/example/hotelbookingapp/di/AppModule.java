package com.example.hotelbookingapp.di;

import android.content.Context;

import com.example.hotelbookingapp.data.api.HotelDetailsApi;
import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.api.LocationGeoIdApi;
import com.example.hotelbookingapp.data.repository.BookingRepositoryImpl;
import com.example.hotelbookingapp.data.repository.FavoriteHotelsRepositoryImpl;
import com.example.hotelbookingapp.data.repository.HotelDetailsRepositoryImpl;
import com.example.hotelbookingapp.data.repository.HotelsListRepositoryImpl;
import com.example.hotelbookingapp.data.repository.RegionIdRepositoryImpl;
import com.example.hotelbookingapp.domain.repository.BookingRepository;
import com.example.hotelbookingapp.domain.repository.FavoriteHotelsRepository;
import com.example.hotelbookingapp.domain.repository.HotelDetailsRepository;
import com.example.hotelbookingapp.domain.repository.HotelsListRepository;
import com.example.hotelbookingapp.domain.repository.RegionIdRepository;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.helper.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    @Singleton
    public static LocationGeoIdApi provideGeoIdApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(LocationGeoIdApi.class);
    }

    @Provides
    @Singleton
    public static HotelsListApi provideHotelsListApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelsListApi.class);
    }

    @Provides
    @Singleton
    public static HotelDetailsApi provideHotelDetailsApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelDetailsApi.class);
    }

    @Provides
    @Singleton
    public static HotelsListRepository provideHotelsListRepository(HotelsListApi api) {
        return new HotelsListRepositoryImpl(api);
    }

    @Singleton
    @Provides
    public static HotelDetailsRepository provideHotelDetailsRepository(HotelDetailsApi api) {
        return new HotelDetailsRepositoryImpl(api);
    }

    @Singleton
    @Provides
    public static BookingRepository provideBookingRepository(FirebaseFirestore firestore, FirebaseAuth firebaseAuth) {
        return new BookingRepositoryImpl(firestore, firebaseAuth);
    }

    @Provides
    @Singleton
    public static RegionIdRepository provideGeoIdRepository(LocationGeoIdApi api) {
        return new RegionIdRepositoryImpl(api);
    }

    @Provides
    @Singleton
    public static AuthManager provideAuthManager(@ApplicationContext Context context) {
        return new AuthManager(context);
    }

    @Provides
    @Singleton
    public static FavoriteHotelsRepository provideFavoriteHotelsRepository(FirebaseFirestore firestore, FirebaseAuth firebaseAuth) {
        return new FavoriteHotelsRepositoryImpl(firestore, firebaseAuth);
    }
}
