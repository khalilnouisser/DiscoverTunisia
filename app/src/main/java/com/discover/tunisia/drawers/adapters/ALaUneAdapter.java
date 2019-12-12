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
import com.discover.tunisia.discover.entities.Event;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_a_la_une_all, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Event event = events.get(i);
    if(event!=null)
    {
        try {
            viewHolder.tvTitle.setText(event.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            viewHolder.tvDescription.setText(event.getDate());
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
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TransitionActivity.class);
                    intent.putExtra(Constante.ACTION,Constante.DETAILS_EVENT_FRAGMENT);
                    intent.putExtra("event", event);
                    context.startActivity(intent);
                }
            });
        }catch (Exception ignored)
        {

        }
    }
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
