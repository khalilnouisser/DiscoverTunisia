package com.discover.tunisia.drawers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.IncontournableAdapter;
import com.discover.tunisia.discover.adapters.SejourAdapter;
import com.discover.tunisia.discover.entities.Incontournable;
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
public class AllIncontournableFragment extends Fragment {

    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_incontournables)
    RecyclerView rvIncontournables;
    Unbinder unbinder;

    public AllIncontournableFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new AllIncontournableFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_incontournable, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        try {
            initIncontournable();
        }catch (Exception ignored)
        {

        }
        return view;
    }


    private void initIncontournable() {
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getIncontournable();
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
                            List<Incontournable> incontournables = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Incontournable incontournable = gson.fromJson(array.get(i).toString(), Incontournable.class);
                                incontournables.add(incontournable);
                            }
                            initIncontournableAdapter(incontournables);
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

    private void initIncontournableAdapter(List<Incontournable> incontournables) {
        com.discover.tunisia.drawers.adapters.IncontournableAdapter adapter = new com.discover.tunisia.drawers.adapters.IncontournableAdapter(getContext(), incontournables);
        rvIncontournables.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvIncontournables.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
