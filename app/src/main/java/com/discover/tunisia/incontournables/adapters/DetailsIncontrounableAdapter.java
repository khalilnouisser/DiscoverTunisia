package com.discover.tunisia.incontournables.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.incontournables.entities.DetailsIncontournable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsIncontrounableAdapter extends RecyclerView.Adapter<DetailsIncontrounableAdapter.ViewHolder> {

    private List<DetailsIncontournable> incontournables;
    private Context context;

    public DetailsIncontrounableAdapter(List<DetailsIncontournable> incontournables, Context context) {
        this.incontournables = incontournables;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_details_incontroubale, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DetailsIncontournable incontrounable = incontournables.get(i);
        if(incontrounable!=null)
        {
            viewHolder.tvTitle.setText(incontrounable.getTitle());
            viewHolder.tvDescription.setText(incontrounable.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return incontournables.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder{
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
