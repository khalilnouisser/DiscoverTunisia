package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.discover.entities.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            viewHolder.rbPlace.setRating(Integer.parseInt(place.getStars()));
            viewHolder.tvPlace.setText(place.getName());
            viewHolder.tvPlaceDesc.setText(place.getDescription());
            viewHolder.llMainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("okiiiii");
                    try {
                        openPlaceDialog(places.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
        }
    }



    private void openPlaceDialog(Place place) {

        final View dialogFiltre =  LayoutInflater.from(context).inflate(
                R.layout.dialog_place, null, false);

        final AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(context)).create();

        alertDialog.setView(dialogFiltre);


        ImageView ivClose = dialogFiltre.findViewById(R.id.iv_close);
        TextView tv_place = dialogFiltre.findViewById(R.id.tv_place);
        RatingBar rbPlace = dialogFiltre.findViewById(R.id.rb_place);
        TextView label_description = dialogFiltre.findViewById(R.id.label_description);
        TextView tv_description = dialogFiltre.findViewById(R.id.tv_description);
        TextView label_adresse = dialogFiltre.findViewById(R.id.label_adresse);
        TextView tv_adresse = dialogFiltre.findViewById(R.id.tv_adresse);
        TextView label_city = dialogFiltre.findViewById(R.id.label_city);
        TextView tv_city = dialogFiltre.findViewById(R.id.tv_city);
        TextView label_phone = dialogFiltre.findViewById(R.id.label_phone);
        TextView tv_phone = dialogFiltre.findViewById(R.id.tv_phone);

        ivClose.setOnClickListener(view -> alertDialog.dismiss());

        tv_place.setText(place.getName());
        rbPlace.setRating(Integer.parseInt(place.getStars()));

        if (place.getDescription() != null && !place.getDescription().equals("")) {
            tv_description.setText(place.getDescription());
        } else {
            label_description.setVisibility(View.GONE);
            tv_description.setVisibility(View.GONE);
        }


        if (place.getAdress() != null && !place.getAdress().equals("")) {
            tv_adresse.setText(place.getAdress());
        } else {
            label_adresse.setVisibility(View.GONE);
            tv_adresse.setVisibility(View.GONE);
        }

        if (place.getCity() != null && !place.getCity().equals("")) {
            tv_city.setText(place.getCity());
        } else {
            label_city.setVisibility(View.GONE);
            tv_city.setVisibility(View.GONE);
        }

        if (place.getPhone() != null && !place.getPhone().equals("")) {
            tv_phone.setText(place.getPhone());
        } else {
            label_phone.setVisibility(View.GONE);
            tv_phone.setVisibility(View.GONE);
        }


        alertDialog.show();

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
        @BindView(R.id.main_view)
        LinearLayout llMainView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
