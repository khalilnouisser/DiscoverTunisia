package com.discover.tunisia.sejours;


import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.DetailsSejourAdapter;
import com.discover.tunisia.discover.entities.DetailsSejour;
import com.discover.tunisia.discover.entities.Sejour;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Unbinder unbinder;
    @BindView(R.id.rv_details_sejour)
    RecyclerView rvDetailsSejour;
    @BindView(R.id.tv_sejour_title)
    TextView tvSejourTitle;
    private Sejour sejour;


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
        tvSejourTitle.setText(sejour.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
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
                if ((collapsingToolbar.getHeight() + verticalOffset) < (ViewCompat.getMinimumHeight(collapsingToolbar))) {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                } else {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

    }

    private void initSejour(Sejour sejour) {
        Utils.setRoundedImageUri(sejour.getImage(), getContext(), ivPlage);
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getOneSejour(Integer.parseInt(sejour.getId()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        JSONArray object = new JSONArray(response.body().string());

                        Gson gson = Utils.getGsonInstance();
                        List<DetailsSejour> detailsSejours = new ArrayList<>();
                        for (int i = 0; i < object.length(); i++) {
                            DetailsSejour detailsSejour = gson.fromJson(object.get(i).toString(), DetailsSejour.class);
                            detailsSejours.add(detailsSejour);
                        }
                        initAdapter(detailsSejours);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });


    }

    private void initAdapter(List<DetailsSejour> detailsSejours) {
        DetailsSejourAdapter adapter = new DetailsSejourAdapter(getContext(), detailsSejours);
        rvDetailsSejour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvDetailsSejour.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
