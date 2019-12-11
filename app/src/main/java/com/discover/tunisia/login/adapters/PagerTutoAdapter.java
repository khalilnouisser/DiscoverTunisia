package com.discover.tunisia.login.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;

public class PagerTutoAdapter extends PagerAdapter {
    Context context;
    public static OnNextistener mListener;
    private int[] GalImages = new int[]{
            R.drawable.tuto_one,
            R.drawable.tuto_two,
            R.drawable.tuto_three
    };

    LayoutInflater mLayoutInflater;

    public PagerTutoAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static void setOnNextListener(OnNextistener onNextListener) {
        mListener = onNextListener;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_tuto);
        ImageView imageViewTuto = (ImageView) itemView.findViewById(R.id.iv_tuto_description);
        TextView textView = (TextView) itemView.findViewById(R.id.tv_tuto);
        if(position == 0)
        {
            imageViewTuto.setBackground(null);
            imageViewTuto.setBackground(context.getResources().getDrawable(R.drawable.tuto_2));
        }
        if(position == 1)
        {
            imageViewTuto.setBackground(null);
            imageViewTuto.setBackground(context.getResources().getDrawable(R.drawable.tuto_2));
        }
        if(position == 2)
        {
            imageViewTuto.setBackground(null);
            imageViewTuto.setBackground(context.getResources().getDrawable(R.drawable.tuto_3));
        }
        if(position == 2)
        {
            textView.setText("Get Started");
        }
        else {
            textView.setText("Next");
        }
        imageView.setBackground(context.getResources().getDrawable(GalImages[position]));
        container.addView(itemView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                    mListener.onNextListener(position);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public interface OnNextistener {
        void onNextListener(int position);
    }
}
