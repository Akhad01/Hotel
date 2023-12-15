package com.example.hotelbookingapp.presentation.hotel_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;

import java.util.List;

public class HotelImagesPagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<String> imageUrls;

    public HotelImagesPagerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_hotel_image, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageViewHotel);

        // Load image using Glide
        Glide.with(context)
                .load(imageUrls.get(position))
                .centerCrop()
                .placeholder(R.drawable.places) // placeholder image while loading
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
