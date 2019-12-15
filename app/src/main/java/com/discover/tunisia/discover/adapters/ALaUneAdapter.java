package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.Event;
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

public class ALaUneAdapter extends RecyclerView.Adapter<ALaUneAdapter.ViewHolder> {

    private Context context;
    private List<Event> events;

    public ALaUneAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_a_la_une, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Event event = events.get(i);
    if(event!=null)
    {
        if (Preference.getCurrentCompte(context) == null) {
            viewHolder.srcBookmark.setVisibility(View.GONE);
        } else {
            if (event.isIs_clicked_favoris()) {
                viewHolder.srcBookmark.setImageResource(R.drawable.ic_bookmark_enabled);
            } else {
                viewHolder.srcBookmark.setImageResource(R.drawable.ic_bookmark_disabled);
            }
        }

        try {
            viewHolder.tvTitle.setText(event.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            viewHolder.tvDescription.setText(event.getDate().substring(0,36));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Utils.setRoundedImageUri(event.getImage(),context,viewHolder.ivEvent);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try {
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.DETAILS_EVENT_FRAGMENT);
                intent.putExtra("event", event);
                context.startActivity(intent);
            });
        }catch (Exception ignored)
        {

        }

        viewHolder.srcBookmark.setOnClickListener(v -> {
            if(event.isIs_clicked_favoris())
            {
                event.setIs_clicked_favoris(false);
            }
            else {
                event.setIs_clicked_favoris(true);
            }
            notifyDataSetChanged();
            try {
                AddBookMark(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    }


    private void AddBookMark(Event event)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",Integer.parseInt(Objects.requireNonNull(Preference.getCurrentCompte(context)).getId()));
        jsonObject.addProperty("elementId",Integer.parseInt(Objects.requireNonNull(event.getId())));
        jsonObject.addProperty("elementType","event");
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().createFavorite(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return events.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_event)
        RoundedImageView ivEvent;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.src_bookmark)
        ImageView srcBookmark;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
