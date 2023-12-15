package com.example.hotelbookingapp.presentation.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.databinding.HotelViewHolderBinding;

import java.util.List;

public class FavoriteHotelsRvAdapter extends RecyclerView.Adapter<FavoriteHotelsRvAdapter.HotelViewHolder> {

    private static OnHotelClickListener listener;
    private static OnFavoriteClickListener favoriteClickListener;
    private final List<SingleHotelItem> hotelList;

    public FavoriteHotelsRvAdapter(List<SingleHotelItem> hotelList, OnHotelClickListener listener, OnFavoriteClickListener favoriteClickListener) {
        this.hotelList = hotelList;
        FavoriteHotelsRvAdapter.listener = listener;
        FavoriteHotelsRvAdapter.favoriteClickListener = favoriteClickListener;
    }


    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        HotelViewHolderBinding binding = HotelViewHolderBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HotelViewHolder(binding.getRoot(), context, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        holder.bind(hotelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(String hotelId);
    }

    public interface OnHotelClickListener {
        void onHotelClick(SingleHotelItem hotel);
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final HotelViewHolderBinding binding;
        private SingleHotelItem hotel;

        public HotelViewHolder(@NonNull View itemView, Context context, HotelViewHolderBinding binding) {
            super(itemView);
            this.context = context;
            this.binding = binding;

            binding.imageViewHotel.setOnClickListener(v -> {
                if (hotel != null) {
                    listener.onHotelClick(hotel);
                }
            });

            binding.ivAddToFav.setOnClickListener(v -> {
                if (hotel != null) {
                    binding.ivAddToFav.setBackgroundResource(R.drawable.favorite_border_icon);
                    favoriteClickListener.onFavoriteClick(hotel.getName());
                }
            });
        }

        public void bind(SingleHotelItem curHotel) {
            hotel = curHotel;
            Glide.with(context)
                    .load(curHotel.getImageUrl())
                    .error(R.drawable.places)
                    .into(binding.imageViewHotel);

            binding.textViewHotelName.setText(curHotel.getName());
            binding.ivAddToFav.setBackgroundResource(R.drawable.favorite_icon);
            Double rating = curHotel.getReview();
            String textViewRatingText = "Rating: " + (rating != null ? rating + "/10" : "");
            binding.textViewRating.setText(textViewRatingText);

            String price = curHotel.getPrice();
            String unknownPriceMsg = "-";
            binding.textViewPrice.setText(price != null ? price : unknownPriceMsg);

            String neighborhood = curHotel.getNeighborhood();
            binding.textViewNeighborhood.setText(neighborhood != null ? neighborhood : "");
        }
    }
}
