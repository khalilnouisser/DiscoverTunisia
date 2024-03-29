package com.discover.tunisia.photos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.photos.adapter.PhotoAdapter;
import com.discover.tunisia.photos.entities.Photo;
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

public class PhotosFragmentFragment extends Fragment {

    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    Unbinder unbinder;
    private View view;

    public PhotosFragmentFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return  new PhotosFragmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {
        Call<ResponseBody> call = null;
        if(Preference.getCurrentCompte(getContext())!=null)
        {
            call = RetrofitServiceFacotry.getServiceApiClient().getPhotos(Integer.parseInt(Objects.requireNonNull(Preference.getCurrentCompte(getContext())).getId()));
        }
        else call = RetrofitServiceFacotry.getServiceApiClient().getPhotos();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.code() == 200)
                {
                    try {
                        assert response.body() != null;
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray array = object.optJSONArray("data");
                        if(array!=null)
                        {
                            Gson gson = Utils.getGsonInstance();
                            List<Photo> photos = new ArrayList<>();
                            for (int i = 0;i<array.length();i++)
                            {
                                Photo photo = gson.fromJson(array.get(i).toString(),Photo.class);
                                photos.add(photo);
                            }
                            initPhotoAdapter(photos);
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

    private void initPhotoAdapter(List<Photo> photos) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), photos);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvImages.setLayoutManager(mLayoutManager);
        rvImages.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
