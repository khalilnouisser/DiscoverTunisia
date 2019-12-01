package com.discover.tunisia.config;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.bumptech.glide.Glide;
import com.discover.tunisia.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

public class Utils {
    public static void changeStatusBarColors(Activity activity, int color) {
        try {
            Window window = activity.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

            // finally change the color

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(activity, color));
            }

            if (color == R.color.white && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window w = Objects.requireNonNull(activity).getWindow();
                w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setRoundedImageUri(final String imageURL, final Context context, final RoundedImageView roundedImageView) {


        if (imageURL != null && context != null) {

            Glide.with(context)

                    .load(imageURL)
                    .into(roundedImageView);
        }
    }
}
