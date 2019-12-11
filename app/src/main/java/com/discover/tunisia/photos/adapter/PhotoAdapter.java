package com.discover.tunisia.photos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.photos.entities.Photo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(Context context,List<Photo> photos) {
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_photo, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Photo photo = photos.get(i);
        if(photo!=null && photo.getImage()!=null)
        {
            Utils.setRoundedImageUri(photo.getImage(),context,viewHolder.ivPhoto);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            assert photo != null;
            if(photo.getType().equalsIgnoreCase("image"))
            {
                Intent intent = new Intent(context, TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.DETAILS_PHOTO_FRAGMENT);
                intent.putExtra(Constante.PHOTO,photo);
                context.startActivity(intent);
            }
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(photo.getVideo_link()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        RoundedImageView ivPhoto;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
