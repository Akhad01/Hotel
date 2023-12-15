package com.example.hotelbookingapp.presentation.hotels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.hotel.Property;
import com.example.hotelbookingapp.databinding.HotelViewHolderBinding;

import java.util.List;

public class HotelsRvAdapter extends RecyclerView.Adapter<HotelsRvAdapter.HotelViewHolder> {

    private final List<Property> hotelList;
    private final OnHotelClickListener listener;
    private final OnHotelFavClickListener favListener;

    public HotelsRvAdapter(List<Property> hotelList, OnHotelClickListener listener, OnHotelFavClickListener favListener) {
        this.hotelList = hotelList;
        this.listener = listener;
        this.favListener = favListener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_view_holder, parent, false);
        return new HotelViewHolder(itemView, listener, favListener);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        holder.bind(hotelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public interface OnHotelClickListener {
        void onHotelClick(Property hotel);
    }

    public interface OnHotelFavClickListener {
        void onFavClick(Property hotel);
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final HotelViewHolderBinding binding;
        private Property hotel;

        public HotelViewHolder(View itemView, OnHotelClickListener listener, OnHotelFavClickListener favListener) {
            super(itemView);
            context = itemView.getContext();
            binding = HotelViewHolderBinding.bind(itemView);

            binding.imageViewHotel.setOnClickListener(view -> {
                if (hotel != null) {
                    listener.onHotelClick(hotel);
                }
            });

            binding.ivAddToFav.setOnClickListener(view -> {
                if (hotel != null) {
                    binding.ivAddToFav.setBackgroundResource(R.drawable.favorite_icon);
                    favListener.onFavClick(hotel);
                }
            });
        }

        public void bind(Property curHotel) {
            if (curHotel != null && curHotel.getPropertyImage() != null) {
                Glide.with(context).load(curHotel.getPropertyImage().getImage().getUrl())
                        .error(R.drawable.places)
                        .into(binding.imageViewHotel);
            }

            assert curHotel != null;
            binding.textViewHotelName.setText(curHotel.getName());

            float rating = (float) curHotel.getReviews().getScore();
            String textViewRatingText = "Rating: " + rating + "/10";
            binding.textViewRating.setText(textViewRatingText);
            String unknownPriceMsg = "-";
            String price = "";

            if (curHotel.getPrice() != null && curHotel.getPrice().getDisplayMessages() != null && !curHotel.getPrice().getDisplayMessages().isEmpty() && curHotel.getPrice().getDisplayMessages().get(0) != null && curHotel.getPrice().getDisplayMessages().get(0).getLineItems() != null && !curHotel.getPrice().getDisplayMessages().get(0).getLineItems().isEmpty() && curHotel.getPrice().getDisplayMessages().get(0).getLineItems().get(0) != null && curHotel.getPrice().getDisplayMessages().get(0).getLineItems().get(0).getPrice() != null) {
                price = curHotel.getPrice().getDisplayMessages().get(0).getLineItems().get(0).getPrice().getPriceTag();
                binding.textViewPrice.setText(price);
            } else binding.textViewPrice.setText(unknownPriceMsg);


            if (curHotel.getNeighborhood() != null && curHotel.getNeighborhood().getName() != null) {
                binding.textViewNeighborhood.setText(curHotel.getNeighborhood().getName());
            }

            hotel = curHotel;
        }
    }
}
