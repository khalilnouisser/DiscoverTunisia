package com.discover.tunisia.sejours;


import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.Sejour;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneSejourFragment extends Fragment {

    View view;
    @BindView(R.id.iv_plage)
    RoundedImageView ivPlage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    Unbinder unbinder;
    private Sejour sejour;

    public OneSejourFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(Sejour sejour) {
        Bundle args = new Bundle();
        args.putSerializable(Constante.SEJOUR, (Serializable) sejour);
        OneSejourFragment fragment = new OneSejourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one_sejour, container, false);
        unbinder = ButterKnife.bind(this, view);
        assert this.getArguments() != null;
        sejour = (Sejour) this.getArguments().getSerializable(Constante.SEJOUR);
        if(sejour!=null)
        {
            initSejour(sejour);
            iniToolBar();
        }
        return view;
    }

    private void iniToolBar() {
        // toolbar
        toolbar.setTitle(sejour.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    toolbar.setTitleTextColor(R.color.colorPrimary);
                } else {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    toolbar.setTitleTextColor(R.color.white);
                }
            }
        });

    }

    private void initSejour(Sejour sejour) {
        Utils.setRoundedImageUri(sejour.getImage(),getContext(),ivPlage);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
