package com.discover.tunisia.incontournables;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.incontournables.adapters.DetailsIncontrounableAdapter;
import com.discover.tunisia.incontournables.entities.DetailsIncontournable;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
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
public class IncontournableFragment extends Fragment {


    View view;
    @BindView(R.id.iv_incontournable)
    RoundedImageView ivIncontournable;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.rv_incontournables)
    RecyclerView rvIncontournables;
    @BindView(R.id.tv_incontournables_title)
    TextView tvIncontournablesTitle;
    Unbinder unbinder;
    Incontournable incontournable;

    public IncontournableFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(Incontournable incontournable) {
        Bundle args = new Bundle();
        args.putSerializable(Constante.INCONTOURNABLE, (Serializable) incontournable);
        IncontournableFragment fragment = new IncontournableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_incontournable, container, false);
        unbinder = ButterKnife.bind(this, view);
        assert this.getArguments() != null;
        incontournable = (Incontournable) this.getArguments().getSerializable(Constante.INCONTOURNABLE);
        if (incontournable != null) {
            iniToolBar();
            initIncontournable(incontournable);
        }
        return view;
    }

    private void initIncontournable(Incontournable incontournable) {

        Utils.setRoundedImageUri(incontournable.getImage(), getContext(), ivIncontournable);

        tvIncontournablesTitle.setText(incontournable.getTitle());
      tvDescription.setText(incontournable.getDescription());
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getOneIncontournable(Integer.parseInt(incontournable.getId()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        JSONArray object = new JSONArray(response.body().string());

                        Gson gson = Utils.getGsonInstance();
                        List<DetailsIncontournable> detailsIncontournables = new ArrayList<>();
                        for (int i = 0; i < object.length(); i++) {
                            DetailsIncontournable detailsIncontournable = gson.fromJson(object.get(i).toString(), DetailsIncontournable.class);
                            detailsIncontournables.add(detailsIncontournable);
                        }
                        initIncontournableAdapter(detailsIncontournables);
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


        try {
            Thread thread = new Thread(() -> {
                try  {
                    URL url = new URL(incontournable.getImage());
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Drawable image = null;
                    try {
                        image = new BitmapDrawable(Objects.requireNonNull(getContext()).getResources(), bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        rvIncontournables.setBackground(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            try {
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }



    }

    private void initIncontournableAdapter(List<DetailsIncontournable> incontournables) {
        DetailsIncontrounableAdapter adapter = new DetailsIncontrounableAdapter(incontournables, getContext());
        rvIncontournables.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvIncontournables.setAdapter(adapter);
    }

    private void iniToolBar() {
        // toolbar
        ivIncontournable.setBackgroundResource(incontournable.getResource());
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
                } else {
                    Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
