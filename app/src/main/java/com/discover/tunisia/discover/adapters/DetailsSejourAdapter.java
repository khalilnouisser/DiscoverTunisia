package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.DetailsSejour;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsSejourAdapter extends RecyclerView.Adapter<DetailsSejourAdapter.ViewHolder> {

    private List<DetailsSejour> sejours;
    private Context context;

    public DetailsSejourAdapter(Context context, List<DetailsSejour> sejours) {
        this.sejours = sejours;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_details_sejour, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DetailsSejour detailsSejour = sejours.get(i);
        if (detailsSejour != null) {
            viewHolder.tvPlageTitle.setText(detailsSejour.getName());
            Utils.setRoundedImageUri(detailsSejour.getThumbnail(), context, viewHolder.ivTunisTour);
        }
        viewHolder.ivTunisTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.ONE_SEJOUR_FRAGMENT);
                intent.putExtra(Constante.DETAILS_SEJOUR, detailsSejour);
                Objects.requireNonNull(context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sejours.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tunis_tour)
        RoundedImageView ivTunisTour;
        @BindView(R.id.tv_plage_title)
        TextView tvPlageTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
