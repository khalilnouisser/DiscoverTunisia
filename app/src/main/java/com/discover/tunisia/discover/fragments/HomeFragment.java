package com.discover.tunisia.discover.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.adapters.ALaUneAdapter;
import com.discover.tunisia.discover.adapters.IncontournableAdapter;
import com.discover.tunisia.discover.adapters.SejourAdapter;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.discover.entities.Sejour;
import com.discover.tunisia.discover.entities.Weither;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class HomeFragment extends Fragment {

    View view;
    @BindView(R.id.tv_tunisia)
    RoundedImageView tvTunisia;
    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_annotation_date)
    TextView tvAnnotationDate;
    @BindView(R.id.tv_temp)
    RoundedImageView tvTemp;
    @BindView(R.id.iv_pin)
    ImageView ivPin;
    @BindView(R.id.tv_region_name)
    TextView tvRegionName;
    @BindView(R.id.tv_region_description)
    TextView tvRegionDescription;
    @BindView(R.id.tv_a_la_une)
    TextView tvALaUne;
    @BindView(R.id.rv_a_la_une)
    RecyclerView rvALaUne;
    @BindView(R.id.tv_mon_sejour)
    TextView tvMonSejour;
    @BindView(R.id.rv_sejour)
    RecyclerView rvSejour;
    Unbinder unbinder;
    @BindView(R.id.tv_incontournables)
    TextView tvIncontournables;
    @BindView(R.id.rv_incontournables)
    RecyclerView rvIncontournables;
    @BindView(R.id.tv_cloud)
    TextView tvCloud;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initWeither();
        initEvents();
        initSerjour();
        initIncontournable();
        return view;
    }

    private void initWeither() {
        Call<ResponseBody> call = RetrofitServiceFacotry.getWeitherApiClient().getWeither(Utils.key, Utils.query);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        JSONObject object = new JSONObject(response.body().string());
                        Gson gson = Utils.getGsonInstance();
                        Weither weither = gson.fromJson(object.toString(), Weither.class);
                        if (weither.getLocation().getName() != null) {
                            tvRegionName.setText(weither.getLocation().getName());
                        }
                        if (weither.getCurrent().getWeather_descriptions() != null) {
                            tvCloud.setText(weither.getCurrent().getWeather_descriptions().get(0));
                        }
                        int resource = R.drawable._001_rain_3;

                        if(weither.getCurrent().getWeather_code()==113)
                        {
                        resource = R.drawable._006_sun;
                        }
                        else if(weither.getCurrent().getWeather_code() == 116 ||
                                weither.getCurrent().getWeather_code() == 143 ||
                                weither.getCurrent().getWeather_code() == 248)
                        {
                        resource = R.drawable._009_cloudy_day_1;
                        }
                        else if(weither.getCurrent().getWeather_code() == 119 ||
                                weither.getCurrent().getWeather_code() == 122 ||
                                weither.getCurrent().getWeather_code() == 263 ||
                                weither.getCurrent().getWeather_code() == 266)
                        {

                            resource = R.drawable._029_cloudy_day;
                        }
                        else if(weither.getCurrent().getWeather_code() == 176)
                        {
                            resource = R.drawable._015_rain_1;
                        }
                        else if(weither.getCurrent().getWeather_code() == 179 ||
                                weither.getCurrent().getWeather_code() == 185 ||
                                weither.getCurrent().getWeather_code() == 227 ||
                                weither.getCurrent().getWeather_code() == 260 ||
                                weither.getCurrent().getWeather_code() == 293 )
                        {
                            resource = R.drawable._013_snowing_1;
                        }
                        else if(weither.getCurrent().getWeather_code() == 182 ||
                                weither.getCurrent().getWeather_code() == 281)
                        {
                            resource = R.drawable._012_snowing_2;
                        }
                        else if(weither.getCurrent().getWeather_code() == 200)
                        {
                            resource = R.drawable._002_storm_2;
                        }

                        else if(weither.getCurrent().getWeather_code() == 230 ||
                                weither.getCurrent().getWeather_code() == 284 ||
                                weither.getCurrent().getWeather_code() == 299 ||
                                weither.getCurrent().getWeather_code() == 302)
                        {
                            resource = R.drawable._003_storm_1;
                        }
                        else if(weither.getCurrent().getWeather_code() == 296)
                        {
                            resource = R.drawable._015_rain_1;
                        }
                        else if(weither.getCurrent().getWeather_code() == 305 ||
                                weither.getCurrent().getWeather_code() == 308)
                        {
                            resource = R.drawable._002_storm_2;
                        }
                        else if(weither.getCurrent().getWeather_code() == 311)
                        {
                            resource = R.drawable._011_snowing_3;
                        }
                        try {
                            tvTemp.setImageResource(resource);
                        }catch (Exception ignored)
                        {

                        }
                        if(weither.getRequest().getType()!=null)
                        {
                            tvRegionDescription.setText(weither.getRequest().getType());
                        }
                        if(weither.getLocation().getLocaltime()!=null)
                        {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
                            Date date;
                            try {
                                date =  dateformat.parse(weither.getLocation().getLocaltime());
                                @SuppressLint("SimpleDateFormat") DateFormat dayFormate=new SimpleDateFormat("EEEE");
                                String dayFromDate=dayFormate.format(date);
                                String day = (String) android.text.format.DateFormat.format("dd", date);
                                tvDate.setText(dayFromDate+" "+day);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }

    private void initEvents() {
        Call<ResponseBody> call = null;
        if(Preference.getCurrentCompte(getContext())!=null)
        {
            call = RetrofitServiceFacotry.getServiceApiClient().getEvent(Integer.parseInt(Objects.requireNonNull(Preference.getCurrentCompte(getContext())).getId()));
        }
        else call = RetrofitServiceFacotry.getServiceApiClient().getEvent();
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
                            List<Event> events = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Event event = gson.fromJson(array.get(i).toString(), Event.class);
                                events.add(event);
                            }
                            initEventAdapter(events);
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

    private void initEventAdapter(List<Event> events) {
        ALaUneAdapter adapter = new ALaUneAdapter(getContext(), events);
        rvALaUne.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvALaUne.setAdapter(adapter);
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
        SejourAdapter adapter = new SejourAdapter(getContext(), sejours);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvSejour.setLayoutManager(mLayoutManager);
        rvSejour.setAdapter(adapter);
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
        IncontournableAdapter adapter = new IncontournableAdapter(getContext(), incontournables);
        rvIncontournables.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvIncontournables.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
