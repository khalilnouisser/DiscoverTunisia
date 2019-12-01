package com.discover.tunisia.discover.fragments;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.discover.adapters.ALaUneAdapter;
import com.discover.tunisia.discover.adapters.IncontournableAdapter;
import com.discover.tunisia.discover.adapters.SejourAdapter;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.discover.entities.Sejour;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<Event> events = new ArrayList<>();
    List<Sejour> sejours = new ArrayList<>();
    List<Incontournable> incontournables = new ArrayList<>();
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
    ImageView tvTemp;
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
        initEvents();
        initSerjour();
        initIncontournable();
        return view;
    }

    private void initEvents() {
        Event event_1 = new Event("Festival du miel de Sidi Alouane",
                "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.\n" +
                        "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.\n" +
                        "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.\n" +
                        "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.\n" +
                        "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.",
                "https://onqor.co.uk/wp-content/uploads/2019/03/Events-1200x630.jpg", "Octobre 27, 13:00");
        Event event_2 = new Event("les dunes electroniques", "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.", "https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/3/2016/12/16131147/future-phone-mobile-live-events-technology-trends.png", "Octobre 27, 13:00");
        Event event_3 = new Event("Festival du miel de Sidi Alouane", "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.", "https://i.vimeocdn.com/video/685774105.webp?mw=1000&mh=563&q=70", "Octobre 27, 13:00");
        Event event_4 = new Event("les dunes electroniques", "Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is thought to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type specimen book.", "https://technext.github.io/Evento/images/demo/bg-slide-01.jpg", "Octobre 27, 13:00");
        events.add(event_1);
        events.add(event_2);
        events.add(event_3);
        events.add(event_4);
        initEventAdapter(events);
    }

    private void initEventAdapter(List<Event> events) {
        ALaUneAdapter adapter = new ALaUneAdapter(getContext(), events);
        rvALaUne.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvALaUne.setAdapter(adapter);
    }

    private void initSerjour() {
        Sejour sejour_1 = new Sejour("À la plage", "https://ibb.co/GMWCQ5j", R.drawable.sejour1);
        Sejour sejour_2 = new Sejour("BIEN-ËTRE", "", R.drawable.sejour2);
        Sejour sejour_3 = new Sejour("ARTISANAR", "", R.drawable.sejour3);
        Sejour sejour_4 = new Sejour("CULTURE", "", R.drawable.sejour4);
        Sejour sejour_5 = new Sejour("SAVEURS", "", R.drawable.sejour5);
        Sejour sejour_6 = new Sejour("SENIORS", "", R.drawable.sejour6);
        Sejour sejour_7 = new Sejour("SAHARA", "", R.drawable.sejour7);
        Sejour sejour_8 = new Sejour("ACTIVITÉs", "", R.drawable.sejour8);
        Sejour sejour_9 = new Sejour("INCENTIVES", "", R.drawable.sejour9);
        sejours.add(sejour_1);
        sejours.add(sejour_2);
        sejours.add(sejour_3);
        sejours.add(sejour_4);
        sejours.add(sejour_5);
        sejours.add(sejour_6);
        sejours.add(sejour_7);
        sejours.add(sejour_8);
        sejours.add(sejour_9);
        initSerjourAdapter(sejours);
    }

    private void initSerjourAdapter(List<Sejour> sejours) {
        SejourAdapter adapter = new SejourAdapter(getContext(), sejours);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvSejour.setLayoutManager(mLayoutManager);
        rvSejour.setAdapter(adapter);
    }


    private void initIncontournable() {
        Incontournable incontournable_1 = new Incontournable("","https://onqor.co.uk/wp-content/uploads/2019/03/Events-1200x630.jpg");
        Incontournable incontournable_2 = new Incontournable("","https://onqor.co.uk/wp-content/uploads/2019/03/Events-1200x630.jpg");
        Incontournable incontournable_3 = new Incontournable("","https://onqor.co.uk/wp-content/uploads/2019/03/Events-1200x630.jpg");
        Incontournable incontournable_4 = new Incontournable("","https://onqor.co.uk/wp-content/uploads/2019/03/Events-1200x630.jpg");
        incontournables.add(incontournable_1);
        incontournables.add(incontournable_2);
        incontournables.add(incontournable_3);
        incontournables.add(incontournable_4);
        initIncontournableAdapter(incontournables);
    }

    private void initIncontournableAdapter(List<Incontournable> incontournables) {
        IncontournableAdapter adapter = new IncontournableAdapter(getContext(),incontournables);
        rvIncontournables.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvIncontournables.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
