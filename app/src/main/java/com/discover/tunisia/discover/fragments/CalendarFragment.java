package com.discover.tunisia.discover.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.ListResponse;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.discover.tunisia.utils.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {


    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    @BindView(R.id.tv_event)
    TextView tvEvent;
    @BindView(R.id.layout_call_police)
    RelativeLayout layoutCallPolice;
    @BindView(R.id.layout_call_ambulance)
    RelativeLayout layoutCallAmbulance;
    @BindView(R.id.layout_call_taxi)
    RelativeLayout layoutCallTaxi;

    private HashMap<CalendarDay, Event> dayEventHashMap = new HashMap<>();

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);

        initView();
        getEvents();

        return view;
    }


    private void initView() {

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            try {
                if (dayEventHashMap.get(date) != null) {
                    tvEvent.setText(dayEventHashMap.get(date).getDescription());
                    tvEvent.setVisibility(View.VISIBLE);
                } else tvEvent.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        layoutCallAmbulance.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: 190 "));
            startActivity(intent);
        });

        layoutCallPolice.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: 197 "));
            startActivity(intent);
        });
        layoutCallTaxi.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: 71 837 083"));
            startActivity(intent);
        });
    }

    private void getEvents() {

        Call<ListResponse<Event>> call = RetrofitServiceFacotry.getServiceApiClient().getCalendar();
        call.enqueue(new Callback<ListResponse<Event>>() {
            @Override
            public void onResponse(Call<ListResponse<Event>> call, Response<ListResponse<Event>> response) {
                try {
                    final ArrayList<CalendarDay> dates = new ArrayList<>();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
                    for (Event event : response.body().getData()) {
                        final CalendarDay day = CalendarDay.from(simpleDateFormat.parse(event.getDate()));
                        dates.add(day);
                        dayEventHashMap.put(day, event);
                    }
                    calendarView.addDecorator(new EventDecorator(getResources().getColor(R.color.colorPrimary), dates));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Event>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
