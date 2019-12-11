package com.discover.tunisia.drawers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.drawers.entities.Langue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LangueAdapter extends RecyclerView.Adapter<LangueAdapter.ViewHolder> {

    private List<Langue> langues;
    private Context context;
    public static OnLangueChangedListener mListener;

    public LangueAdapter(Context context, List<Langue> langues) {
        this.langues = langues;
        this.context = context;
    }

    public static void setOnLangueChangedListener(OnLangueChangedListener onLangueChangedListener) {
        mListener = onLangueChangedListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_langue, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Langue langue = langues.get(i);
        if (langue != null) {
            viewHolder.tvLangue.setText(langue.getName());
            if(langue.isSelected())
            {
                viewHolder.cvRadio.setChecked(true);
            }
            else {
                viewHolder.cvRadio.setChecked(false);
            }
        }

        viewHolder.itemView.setOnClickListener(v -> {

            assert langue != null;
            if(langue.isSelected())
            {
                langue.setSelected(false);
            }
            else {
                langue.setSelected(true);
            }
            for(int j = 0;j<langues.size();j++)
            {
                if(!langues.get(j).getName().equalsIgnoreCase(langue.getName()))
                {
                    langues.get(j).setSelected(false);
                }
            }
            if(mListener!=null)
            {
                mListener.onLangueChangedListener(langue);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return langues.size();
    }


    public interface OnLangueChangedListener {
        void onLangueChangedListener(Langue langue);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_langue)
        TextView tvLangue;
        @BindView(R.id.cv_radio)
        RadioButton cvRadio;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
