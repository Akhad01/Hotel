package com.example.hotelbookingapp.presentation.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.databinding.PopularLocationViewHolderBinding;
import com.example.hotelbookingapp.domain.model.City;

import java.util.List;

public class PopularCitiesRvAdapter extends RecyclerView.Adapter<PopularCitiesRvAdapter.CityViewHolder> {

    private final List<City> citiesList;
    private final OnCityClickListener listener;

    public PopularCitiesRvAdapter(List<City> citiesList, OnCityClickListener listener) {
        this.citiesList = citiesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(
                listener,
                PopularLocationViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(citiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public interface OnCityClickListener {
        void onCityClick(City city);
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        private final OnCityClickListener listener;
        private final PopularLocationViewHolderBinding binding;
        private City city;

        public CityViewHolder(OnCityClickListener listener, PopularLocationViewHolderBinding binding) {
            super(binding.getRoot());
            this.listener = listener;
            this.binding = binding;

            binding.imageViewPopCity.setOnClickListener(v -> {
                if (city != null) {
                    listener.onCityClick(city);
                }
            });
        }

        public void bind(City curCity) {
            binding.textViewPopCityName.setText(curCity.getName());
            binding.imageViewPopCity.setBackgroundResource(curCity.getImage());
            city = curCity;
        }
    }
}
