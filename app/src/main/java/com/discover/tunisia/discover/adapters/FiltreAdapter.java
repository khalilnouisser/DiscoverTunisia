package com.discover.tunisia.discover.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltreAdapter extends RecyclerView.Adapter<FiltreAdapter.ViewHolder> {

    private Context context;
    private List<String> filtres;
    private OnItemClickListener onItemClickListener;


    public FiltreAdapter(Context context, List<String> filtres) {
        this.context = context;
        this.filtres = filtres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_filtre, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String filtre = filtres.get(i);


        try {
            viewHolder.tvFiltre.setText(filtre);
        } catch (Exception ignored) {
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClicked(filtre);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filtres.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_filtre)
        TextView tvFiltre;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        public void onItemClicked(String filtre);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
