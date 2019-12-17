package com.discover.tunisia.navigations;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    View view;
    @BindView(R.id.tv_discover)
    TextView tvDiscover;
    Unbinder unbinder;

    public NavigationFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return  new NavigationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvDiscover.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TransitionActivity.class);
            intent.putExtra(Constante.ACTION,Constante.DETAILS_NAVIGATION_FRAGMENT);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
