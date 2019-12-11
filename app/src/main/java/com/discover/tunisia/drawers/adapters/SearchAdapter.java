package com.discover.tunisia.drawers.adapters;

import android.annotation.SuppressLint;
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
import com.discover.tunisia.drawers.entities.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    List<Search> searches = new ArrayList<>();
    private Context context;

    public SearchAdapter(List<Search> searches, Context context) {
        this.searches = searches;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_search, viewGroup, false);
        return new ViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Search search = searches.get(i);
        if(search!=null)
        {
            if(search.getEvent() != null)
            {
                viewHolder.tvTitle.setText(search.getEvent().getTitle());
                try {
                    if(search.getEvent().getDescription().length()>50)
                    {
                        viewHolder.tvDescription.setText(search.getEvent().getDescription().substring(0,50)+"...");
                    }
                    else {
                        viewHolder.tvDescription.setText(search.getEvent().getDescription());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else  if(search.getSejour() != null)
            {
                viewHolder.tvTitle.setText(search.getSejour().getTitle());
            }
            else  if(search.getIncontournable() != null)
            {
                viewHolder.tvTitle.setText(search.getIncontournable().getTitle());
                try {
                    if(search.getIncontournable().getDescription().length()>50)
                    {
                        viewHolder.tvDescription.setText(search.getIncontournable().getDescription().substring(0,50)+"...");
                    }
                    else {
                        viewHolder.tvDescription.setText(search.getIncontournable().getDescription());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else  if(search.getDetailsSejour() != null)
            {
                viewHolder.tvTitle.setText(search.getDetailsSejour().getName());
                try {
                    if(search.getDetailsSejour().getDescription().length()>50)
                    {
                        viewHolder.tvDescription.setText(search.getDetailsSejour().getDescription().substring(0,50)+"...");
                    }
                    else {
                        viewHolder.tvDescription.setText(search.getDetailsSejour().getDescription());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            viewHolder.itemView.setOnClickListener(v -> {
                switch (search.getType()) {
                    case "event": {
                        Intent intent = new Intent(context, TransitionActivity.class);
                        intent.putExtra(Constante.ACTION, Constante.DETAILS_EVENT_FRAGMENT);
                        intent.putExtra("event", search.getEvent());
                        context.startActivity(intent);
                        break;
                    }
                    case "monsejourelements": {
                        Intent intent = new Intent(context, TransitionActivity.class);
                        intent.putExtra(Constante.ACTION, Constante.ONE_SEJOUR_FRAGMENT);
                        intent.putExtra(Constante.DETAILS_SEJOUR, search.getDetailsSejour());
                        Objects.requireNonNull(context).startActivity(intent);
                        break;
                    }
                    case "incontournable": {
                        Intent intent = new Intent(context, TransitionActivity.class);
                        intent.putExtra(Constante.ACTION, Constante.DETAILS_INCONTOURNABLE_FRAGMENT);
                        intent.putExtra(Constante.INCONTOURNABLE, search.getIncontournable());
                        context.startActivity(intent);
                        break;
                    }
                    case "sejour": {
                        Intent intent = new Intent(context, TransitionActivity.class);
                        intent.putExtra(Constante.ACTION, Constante.DETAILS_SEJOUR_FRAGMENT);
                        intent.putExtra("sejour", search.getSejour());
                        context.startActivity(intent);
                        break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
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
