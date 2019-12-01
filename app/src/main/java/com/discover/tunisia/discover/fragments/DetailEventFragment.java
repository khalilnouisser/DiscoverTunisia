package com.discover.tunisia.discover.fragments;


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
import com.discover.tunisia.discover.entities.Event;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends Fragment {

    View view;
    @BindView(R.id.iv_event)
    RoundedImageView ivEvent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    Unbinder unbinder;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private Event event;

    public DetailEventFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putSerializable(Constante.EVENT, (Serializable) event);
        DetailEventFragment fragment = new DetailEventFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        unbinder = ButterKnife.bind(this, view);
        assert this.getArguments() != null;
        event = (Event) this.getArguments().getSerializable(Constante.EVENT);
        iniToolBar();
        if (event != null) {
            initEvent();
        }
        return view;
    }

    private void iniToolBar() {
        // toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
       appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                } else {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

    }


    private void initEvent() {
        Utils.setRoundedImageUri(event.getImage(), getContext(), ivEvent);
        tvTitle.setText(event.getTitle());
        tvDate.setText(event.getDate());
        tvDescription.setText(event.getDescription());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
