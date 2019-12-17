package com.discover.tunisia.navigations.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.navigations.entities.Navigation;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;

public class NavigationPagerAdapter extends PagerAdapter {
    Context context;
    private List<Navigation> navigations;

    public NavigationPagerAdapter(Context context, List<Navigation> navigations) {
        this.context = context;
        this.navigations = navigations;
    }

    public List<Navigation> getNavigations() {
        return navigations;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return navigations.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View viewLayout = inflater.inflate(R.layout.one_item_navigation, container,false);


        TextView tvTitle = viewLayout.findViewById(R.id.tv_title);
        RoundedImageView ivOne = viewLayout.findViewById(R.id.iv_one);
        RoundedImageView ivBackground = viewLayout.findViewById(R.id.iv_background);
        RoundedImageView ivTwo = viewLayout.findViewById(R.id.iv_two);
        RoundedImageView ivThree = viewLayout.findViewById(R.id.iv_three);
        RoundedImageView ivFor = viewLayout.findViewById(R.id.iv_for);
        RoundedImageView ivMap = viewLayout.findViewById(R.id.iv_map);
        TextView tvDescription = viewLayout.findViewById(R.id.tv_description);
        TextView tvDiscover = viewLayout.findViewById(R.id.tv_discover);

        Navigation navigation = navigations.get(position);
        if(navigation!=null)
        {
            try {
                Utils.setRoundedImageUri(navigation.getResource_one(),context,ivBackground);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_one(),context,ivOne);
            } catch (Exception e) {
                ivOne.setVisibility(View.GONE);
                e.printStackTrace();
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_two(),context, ivTwo);
            } catch (Exception e) {
                 ivTwo.setVisibility(View.GONE);
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_three(),context, ivThree);
            } catch (Exception e) {
                 ivThree.setVisibility(View.GONE);
            }
            try {
                Utils.setRoundedImageUri(navigation.getResource_for(),context, ivFor);
            } catch (Exception e) {
                 ivFor.setVisibility(View.GONE);
            }
            try {
                if (navigation.getMap().equals("")) {
                     ivMap.setImageDrawable(context.getResources().getDrawable(R.drawable.tunisia_map_one));
                } else {
                    Utils.setRoundedImageUri(navigation.getMap(),context, ivMap);
                }
            } catch (Exception e) {
                 ivMap.setImageDrawable(context.getResources().getDrawable(R.drawable.tunisia_map_one));
            }

            try {
                 tvDiscover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(navigation.getLink()));
                        context.startActivity(intent);

                    }
                });
            }catch (Exception ignored)
            {

            }

             tvTitle.setText(navigation.getTitle());
             tvDescription.setText(navigation.getDescription());
        }
        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
