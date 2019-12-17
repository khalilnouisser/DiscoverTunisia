package com.discover.tunisia.photos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.photos.entities.Photo;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(Context context, List<Photo> photos) {
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
        if (photo != null && photo.getImage() != null) {
            Utils.setRoundedImageUri(photo.getImage(), context, viewHolder.ivPhoto);
            if (Preference.getCurrentCompte(context) == null) {
                viewHolder.srcBookmark.setVisibility(View.GONE);
            } else {
                if (photo.isIs_clicked_favoris()) {
                    viewHolder.srcBookmark.setImageResource(R.drawable.ic_bookmark_enabled);
                } else {
                    viewHolder.srcBookmark.setImageResource(R.drawable.ic_bookmark_disabled);
                }
            }
        }
        viewHolder.srcBookmark.setOnClickListener(v -> {
            assert photo != null;
            if(photo.isIs_clicked_favoris())
            {
                photo.setIs_clicked_favoris(false);
            }
            else {
                photo.setIs_clicked_favoris(true);
            }
            notifyDataSetChanged();
            try {
                AddBookMark(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        viewHolder.itemView.setOnClickListener(v -> {
            assert photo != null;
            if (photo.getType().equalsIgnoreCase("image")) {
                Intent intent = new Intent(context, TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.DETAILS_PHOTO_FRAGMENT);
                intent.putExtra(Constante.PHOTO, photo);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(photo.getVideo_link()));
                context.startActivity(intent);
            }
        });
    }

    private void AddBookMark(Photo photo)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",Integer.parseInt(Objects.requireNonNull(Preference.getCurrentCompte(context)).getId()));
        jsonObject.addProperty("elementId",Integer.parseInt(Objects.requireNonNull(photo.getId())));
        jsonObject.addProperty("elementType",photo.getType());
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().createFavorite(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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

        @BindView(R.id.src_bookmark)
        ImageView srcBookmark;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
