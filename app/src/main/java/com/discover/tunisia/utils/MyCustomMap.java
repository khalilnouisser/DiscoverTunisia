package com.discover.tunisia.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

public class MyCustomMap extends SupportMapFragment {

    public View mapView;
    public TouchableWrapper touchView;
    private boolean mMapIsTouched;
    private OnTouchListener onTouchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mapView = super.onCreateView(inflater, parent, savedInstanceState);
        // overlay a touch view on map view to intercept the event
        touchView = new TouchableWrapper(getActivity());
        touchView.addView(mapView);
        return touchView;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public interface OnTouchListener {
        void onTouch();
    }

    public class TouchableWrapper extends FrameLayout {
        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (onTouchListener != null) {
                        onTouchListener.onTouch();
                    }
                    // consume event to prevent map from receiving the event
//                    return true;
            }
            return super.dispatchTouchEvent(event);
        }
    }

}
