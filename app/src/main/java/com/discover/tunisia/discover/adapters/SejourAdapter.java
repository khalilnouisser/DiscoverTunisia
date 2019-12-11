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
import com.discover.tunisia.discover.entities.Sejour;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SejourAdapter extends RecyclerView.Adapter<SejourAdapter.ViewHolder> {

    private List<Sejour> sejours;
    private Context context;

    public SejourAdapter(Context context,List<Sejour> sejours) {
        this.sejours = sejours;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_sejour, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Sejour sejour = sejours.get(i);
        if (sejour != null) {
            viewHolder.tvTitle.setText(sejour.getTitle());
            Utils.setRoundedImageUri(sejour.getImage(),context,viewHolder.ivSejour);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TransitionActivity.class);
            intent.putExtra(Constante.ACTION,Constante.DETAILS_SEJOUR_FRAGMENT);
            intent.putExtra("sejour", sejour);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sejours.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_sejour)
        RoundedImageView ivSejour;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
