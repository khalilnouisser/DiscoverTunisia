package com.discover.tunisia.drawers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.SejourAdapter;
import com.discover.tunisia.discover.entities.Sejour;
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
public class AllSejourFragment extends Fragment {

    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_sejour)
    RecyclerView rvSejour;
    Unbinder unbinder;

    public AllSejourFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return  new AllSejourFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_sejour, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());

        try {
            initSerjour();
        }catch (Exception ignored)
        {

        }
        return view;
    }

    private void initSerjour() {
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getSejour();
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
                            List<Sejour> sejours = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Sejour sejour = gson.fromJson(array.get(i).toString(), Sejour.class);
                                sejours.add(sejour);
                            }
                            initSerjourAdapter(sejours);
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

    private void initSerjourAdapter(List<Sejour> sejours) {
        com.discover.tunisia.drawers.adapters.SejourAdapter adapter = new com.discover.tunisia.drawers.adapters.SejourAdapter(getContext(), sejours);
        rvSejour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvSejour.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
