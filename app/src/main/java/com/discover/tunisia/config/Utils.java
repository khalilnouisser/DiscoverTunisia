package com.discover.tunisia.config;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.discover.tunisia.R;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Utils {
    public static final long TIMEOUT =  20;
    private static Gson gson;
    public static List<String> keys = new ArrayList<String>(Arrays.asList("507cebe7553ceb90b5e9d5469ffb63ca",
            "371135818435a26f31bfc53434bdb757","81e6d244f56e60423ba45d84e2957481",
            "674d00df87c6da897ad3a5ae55fcb1cf","0b59c6086058e9829121990149f29810",
            "20d282ce95be8b78a92fdd71c600a474","a9d3cd840a1a0a0f247da3c599ab0409",
            "d5e6e09dfa32f5614041dfbd79bca6fa","371a19e8ddbced908a36b1495048c448",
            "6205e1ffd3bbb38a28578096b020c3ab","b6a2799b48d2efe550975c9b5e039e18",
            "be47926d0d58843bbfbfd07b47c73b80","c2d57139eb80ba0a42b2313062393add",
            "83b9636c23fb41868dbfc0ea05bcd927","f3f62f04874a4e1c86cc9772eaae73fd",
            "d570523530186035d3c9fb6adec5979b","e22f1240a9f7700cbcd99b17c71329ae",
            "93d88aa0cc0560cf2e7465e88c0169e3","a252cc5b4a79a658aeb7498b59377be1",
            "ab9546ada19d2891713dffa06a44b05d"));
    public static String query="Tunis";
    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static String getWatherKey(){
        Random rnd = new Random();
        int r = rnd.nextInt(Utils.keys.size());
        return  Utils.keys.get(r);
    }

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





    public static void hideKeyboard(AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) appCompatActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && appCompatActivity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(appCompatActivity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
