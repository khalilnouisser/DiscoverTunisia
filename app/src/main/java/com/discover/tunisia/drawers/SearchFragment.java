package com.discover.tunisia.drawers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.IncontournableAdapter;
import com.discover.tunisia.drawers.adapters.SearchAdapter;
import com.discover.tunisia.drawers.entities.Search;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class SearchFragment extends Fragment {

    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rvData)
    RecyclerView rvData;
    Unbinder unbinder;
    Call<ResponseBody> callElement;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null && !s.toString().isEmpty())
                {
                    callData(s.toString());
                }
            }
        });
        return view;
    }

    private void callData(String toString) {
        if(callElement == null)
        {   List<Search> searches = new ArrayList<>();
            callElement = RetrofitServiceFacotry.getServiceApiClient().search(toString);
            callElement.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    callElement = null;
                    if(response.code() == 200)
                    {
                        try {
                            Gson gson = Utils.getGsonInstance();
                            assert response.body() != null;
                            JSONArray array = new JSONArray(response.body().string());
                            if(array.length() > 0)
                            {
                                for(int i = 0; i<array.length();i++)
                                {
                                    Search search = gson.fromJson(array.get(i).toString(),Search.class);
                                    searches.add(search);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    initAdapter(searches);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                }
            });
        }

    }

    private void initAdapter(List<Search> searches) {
        SearchAdapter adapter = new SearchAdapter(searches,getContext());
        rvData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvData.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
