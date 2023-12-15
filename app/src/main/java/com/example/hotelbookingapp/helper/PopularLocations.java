package com.example.hotelbookingapp.helper;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.domain.model.City;

import java.util.ArrayList;
import java.util.List;

public class PopularLocations {
    public static List<City> generatePopularLocations() {
        List<City> locations = new ArrayList<>();
        locations.add(new City("Barcelona", R.drawable.barcelona));
        locations.add(new City("London", R.drawable.london));
        locations.add(new City("Paris", R.drawable.paris));
        locations.add(new City("New York", R.drawable.new_york));
        locations.add(new City("Istanbul", R.drawable.istanbul));
        return locations;
    }
}
