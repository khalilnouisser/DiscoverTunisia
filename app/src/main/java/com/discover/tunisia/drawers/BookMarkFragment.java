package com.discover.tunisia.drawers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.ALaUneAdapter;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.drawers.entities.BookMark;
import com.discover.tunisia.drawers.entities.EventBookMark;
import com.discover.tunisia.drawers.entities.GalleryBookMark;
import com.discover.tunisia.photos.adapter.PhotoAdapter;
import com.discover.tunisia.photos.entities.Photo;
import com.discover.tunisia.services.RetrofitServiceFacotry;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class BookMarkFragment extends Fragment {
    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_events)
    RecyclerView rvEvents;
    @BindView(R.id.layout_event)
    LinearLayout layoutEvent;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.layout_photo)
    LinearLayout layoutPhoto;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.layout_video)
    LinearLayout layoutVideo;
    @BindView(R.id.rv_360)
    RecyclerView rv360;
    @BindView(R.id.layout_360)
    LinearLayout layout360;
    @BindView(R.id.nested)
    NestedScrollView nested;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @BindView(R.id.tv_empty_data)
    TextView tvEmptyData;

    List<Event> eventList = new ArrayList<>();
    List<Photo> photos = new ArrayList<>();
    List<Photo> video = new ArrayList<>();
    List<Photo> viurtuels = new ArrayList<>();


    public BookMarkFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return  new BookMarkFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_mark, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initData() {
        Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getFavorite(Integer.parseInt(Objects.requireNonNull(Preference.getCurrentCompte(getContext()))
                .getId()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception ignored) {

                    }
                    try {
                        assert response.body() != null;
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray events = object.optJSONArray("events");
                        JSONArray videos = object.optJSONArray("videos");
                        JSONArray images360 = object.optJSONArray("images360");
                        JSONArray images = object.optJSONArray("images");
                            if (events!=null && events.length() > 0) {
                                layoutEvent.setVisibility(View.VISIBLE);
                                for(int i = 0;i<events.length();i++)
                                {
                                    EventBookMark eventBookMark = Utils.getGsonInstance().fromJson(events.get(i).toString(),EventBookMark.class);
                                    eventList.add(eventBookMark.getEvent());
                                }
                                initEventAdapter(eventList);
                            } else {
                                layoutPhoto.setVisibility(View.GONE);
                            }
                            if (images!=null && images.length() > 0) {
                                layoutPhoto.setVisibility(View.VISIBLE);
                                for(int i = 0;i<images.length();i++)
                                {
                                    GalleryBookMark galleryBookMark = Utils.getGsonInstance().fromJson(images.get(i).toString(),GalleryBookMark.class);
                                    photos.add(galleryBookMark.getGallery());
                                }
                                initPhotoAdapter(photos);
                            } else {
                                layoutPhoto.setVisibility(View.GONE);
                            }
                            if (videos!=null && videos.length() > 0) {
                                layoutVideo.setVisibility(View.VISIBLE);
                                for(int i = 0;i<videos.length();i++)
                                {
                                    GalleryBookMark galleryBookMark = Utils.getGsonInstance().fromJson(videos.get(i).toString(),GalleryBookMark.class);
                                    video.add(galleryBookMark.getGallery());
                                }
                                initVideoAdapter(video);
                            } else {
                                layoutVideo.setVisibility(View.GONE);
                            }
                            if (images360!=null && images360.length() > 0) {
                                layout360.setVisibility(View.VISIBLE);
                                for(int i = 0;i<images360.length();i++)
                                {
                                    GalleryBookMark galleryBookMark = Utils.getGsonInstance().fromJson(images360.get(i).toString(),GalleryBookMark.class);
                                    viurtuels.add(galleryBookMark.getGallery());
                                }
                                initVirtuelleAdapter(viurtuels);
                            } else {
                                layout360.setVisibility(View.GONE);
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    nested.setVisibility(View.GONE);
                    tvEmptyData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }

    private void initPhotoAdapter(List<Photo> photos) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), photos);
        rvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPhoto.setAdapter(adapter);
    }

    private void initVideoAdapter(List<Photo> photos) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), photos);
        rvVideo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvVideo.setAdapter(adapter);
    }

    private void initVirtuelleAdapter(List<Photo> photos) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), photos);
        rv360.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv360.setAdapter(adapter);
    }

    private void initEventAdapter(List<Event> eventList) {
        ALaUneAdapter adapter = new ALaUneAdapter(getContext(), eventList);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvEvents.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
