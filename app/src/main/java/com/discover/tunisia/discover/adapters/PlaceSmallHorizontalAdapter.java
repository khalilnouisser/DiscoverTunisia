package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.discover.entities.Place;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alaa on 14/09/2018.
 */

public class PlaceSmallHorizontalAdapter extends RecyclerView.Adapter<PlaceSmallHorizontalAdapter.ViewHolder> {


    private final Context context;
    private List<Place> places;


    public PlaceSmallHorizontalAdapter(Context context, List<Place> places) {
        this.places = places;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_horizontal_place, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            Place place = places.get(i);
            viewHolder.rbPlace.setNumStars(Integer.parseInt(place.getStars()));
            viewHolder.tvPlace.setText(place.getName());
            viewHolder.tvPlaceDesc.setText(place.getDescription());
        } catch (Exception e) {
        }
    }

    public void resplace(List<Place> places) {
        this.places = new ArrayList<>(places);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return places.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_place)
        TextView tvPlace;
        @BindView(R.id.rb_place)
        RatingBar rbPlace;
        @BindView(R.id.tv_place_desc)
        TextView tvPlaceDesc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
