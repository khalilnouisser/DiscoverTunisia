package com.discover.tunisia.navigations;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.navigations.adapters.NavigationPagerAdapter;
import com.discover.tunisia.navigations.entities.Navigation;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
public class DetailsNavigationFragment extends Fragment {

    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_navigation)
    ViewPager rvNavigation;
    Unbinder unbinder;
    @BindView(R.id.tabDots)
    TabLayout tabDots;

    public DetailsNavigationFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new DetailsNavigationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_details_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);

        try {
            ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        } catch (Exception ignored) {

        }
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initData() {
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getNavigations();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray array = object.optJSONArray("data");
                        if (array != null) {
                            Gson gson = Utils.getGsonInstance();
                            List<Navigation> navigations = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Navigation navigation = gson.fromJson(array.get(i).toString(), Navigation.class);
                                navigations.add(navigation);
                            }
                            initAdapter(navigations);
                        }
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

    private void initAdapter(List<Navigation> navigations) {
        NavigationPagerAdapter adapter = new NavigationPagerAdapter(getContext(), navigations);
        rvNavigation.setAdapter(adapter);
        tabDots.setupWithViewPager(rvNavigation, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
