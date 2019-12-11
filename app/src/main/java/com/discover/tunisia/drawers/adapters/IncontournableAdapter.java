package com.discover.tunisia.drawers.adapters;

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
import com.discover.tunisia.discover.entities.Incontournable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncontournableAdapter extends RecyclerView.Adapter<IncontournableAdapter.ViewHolder> {

    private List<Incontournable> photos;
    private Context context;

    public IncontournableAdapter(Context context, List<Incontournable> photos) {
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_incontournable_horizental, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Incontournable incontournable = photos.get(i);
        if(incontournable!=null && incontournable.getImage()!=null)
        {
            try {
                Utils.setRoundedImageUri(incontournable.getImage(),context,viewHolder.ivIncontournable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assert incontournable != null;
        viewHolder.tvTitle.setText(incontournable.getTitle());
        try {
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.DETAILS_INCONTOURNABLE_FRAGMENT);
                intent.putExtra(Constante.INCONTOURNABLE, incontournable);
                context.startActivity(intent);
            });
        }catch (Exception ignored)
        {

        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_incontournable)
        RoundedImageView ivIncontournable;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
