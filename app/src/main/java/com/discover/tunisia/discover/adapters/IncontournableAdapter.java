package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.photos.entities.Photo;
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
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_incontournable, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Incontournable incontournable = photos.get(i);
        if(incontournable!=null && incontournable.getImage()!=null)
        {
            Utils.setRoundedImageUri(incontournable.getImage(),context,viewHolder.ivIncontournable);
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
