package com.discover.tunisia.navigations.adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.navigations.entities.Navigation;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private List<Navigation> navigations;
    private Context context;

    public NavigationAdapter(Context context, List<Navigation> navigations) {
        this.navigations = navigations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context).inflate(R.layout.one_item_navigation, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Navigation navigation = navigations.get(i);
        if(navigation!=null)
        {
            try {
                Utils.setRoundedImageUri(navigation.getResource_one(),context,viewHolder.ivBackground);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_one(),context,viewHolder.ivOne);
            } catch (Exception e) {
                viewHolder.ivOne.setVisibility(View.GONE);
                e.printStackTrace();
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_two(),context,viewHolder.ivTwo);
            } catch (Exception e) {
                viewHolder.ivTwo.setVisibility(View.GONE);
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_three(),context,viewHolder.ivThree);
            } catch (Exception e) {
                viewHolder.ivThree.setVisibility(View.GONE);
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_for(),context,viewHolder.ivFor);
            } catch (Exception e) {
                viewHolder.ivFor.setVisibility(View.GONE);
            }
            try {
                if (navigation.getMap().equals("")) {
                    viewHolder.ivMap.setImageDrawable(context.getResources().getDrawable(R.drawable.tunisia_map_one));
                } else {
                    Utils.setRoundedImageUri(navigation.getMap(),context,viewHolder.ivMap);
                }
            } catch (Exception e) {
                viewHolder.ivMap.setImageDrawable(context.getResources().getDrawable(R.drawable.tunisia_map_one));
            }

            try {
                viewHolder.tvDiscover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(navigation.getLink()));
                        context.startActivity(intent);

                    }
                });
            }catch (Exception ignored)
            {

            }

            viewHolder.tvTitle.setText(navigation.getTitle());
            viewHolder.tvDescription.setText(navigation.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return navigations.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_one)
        RoundedImageView ivOne;
        @BindView(R.id.iv_background)
        RoundedImageView ivBackground;
        @BindView(R.id.iv_two)
        RoundedImageView ivTwo;
        @BindView(R.id.iv_three)
        RoundedImageView ivThree;
        @BindView(R.id.iv_for)
        RoundedImageView ivFor;
        @BindView(R.id.iv_map)
        RoundedImageView ivMap;
        @BindView(R.id.layout_images)
        RelativeLayout layoutImages;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_discover)
        TextView tvDiscover;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
