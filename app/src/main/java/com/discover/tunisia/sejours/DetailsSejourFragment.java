package com.discover.tunisia.sejours;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.discover.tunisia.activities.TransitionActivity;
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
public class DetailsSejourFragment extends Fragment {

    View view;
    @BindView(R.id.iv_plage)
    RoundedImageView ivPlage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.iv_tunis_tour)
    RoundedImageView ivTunisTour;
    @BindView(R.id.iv_animation_no_stop)
    RoundedImageView ivAnimationNoStop;
    @BindView(R.id.iv_ile)
    RoundedImageView ivIle;
    Unbinder unbinder;
    @BindView(R.id.tv_plage_title)
    TextView tvPlageTitle;
    @BindView(R.id.tv_soire_title)
    TextView tvSoireTitle;
    @BindView(R.id.tv_ile_title)
    TextView tvIleTitle;
    private Sejour sejour;

    private Sejour sejour_one = new Sejour();
    private Sejour sejour_two = new Sejour();
    private Sejour sejour_three = new Sejour();

    public DetailsSejourFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Sejour sejour) {
        Bundle args = new Bundle();
        args.putSerializable(Constante.SEJOUR, (Serializable) sejour);
        DetailsSejourFragment fragment = new DetailsSejourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_sejour, container, false);
        unbinder = ButterKnife.bind(this, view);

        assert this.getArguments() != null;
        sejour = (Sejour) this.getArguments().getSerializable(Constante.SEJOUR);
        if (sejour != null) {
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
        Utils.setRoundedImageUri(sejour.getImage(), getContext(), ivPlage);
        sejour_one.setTitle(tvPlageTitle.getText().toString());
        sejour_two.setTitle(tvSoireTitle.getText().toString());
        sejour_three.setTitle(tvIleTitle.getText().toString());
        sejour_one.setResource(R.drawable.plage);
        sejour_two.setResource(R.drawable.soiree);
        sejour_three.setResource(R.drawable.ile);

        ivTunisTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.ONE_SEJOUR_FRAGMENT);
                intent.putExtra("sejour", sejour_one);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        ivAnimationNoStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.ONE_SEJOUR_FRAGMENT);
                intent.putExtra("sejour", sejour_two);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });

        ivIle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransitionActivity.class);
                intent.putExtra(Constante.ACTION,Constante.ONE_SEJOUR_FRAGMENT);
                intent.putExtra("sejour", sejour_three);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
