package com.discover.tunisia.discover.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.discover.adapters.PlaceSmallHorizontalAdapter;
import com.discover.tunisia.discover.entities.ListResponse;
import com.discover.tunisia.discover.entities.Place;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.discover.tunisia.utils.MyCustomMap;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    GoogleMap mMap;
    @BindView(R.id.rv_places_cart)
    DiscreteScrollView rvPlaces;
    @BindView(R.id.tv_filtre)
    TextView tvFiltre;
    @BindView(R.id.layout_filtre)
    RelativeLayout layoutFiltre;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    List<Place> displayedPlaces = new ArrayList<>();
    List<Place> places = new ArrayList<>();
    private List<MarkerOptions> markers = new ArrayList<>();
    private int selectedPostion;
    private boolean success;
    private PlaceSmallHorizontalAdapter placeSmallHorizontalAdapter;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
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

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        initView();
        MyCustomMap mapFragment = (MyCustomMap) getChildFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        getPlaces();

        return view;
    }


    private void initView() {

        try {
            ivBack.setOnClickListener(view -> Objects.requireNonNull(getActivity()).finish());
            rvPlaces.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
                @Override
                public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                    //do nothing
                }

                @Override
                public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                    try {
                        Marker marker = createMarker(displayedPlaces.get(adapterPosition));
                        createMarkerSelected(marker);
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
//do nothing
                }
            });
        } catch (Exception e) {
        }

        layoutFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFiltreLayout();
            }
        });
    }

    private void openFiltreLayout() {


        final LinearLayout dialogFiltre = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.layout_filtre, null, false);

        final AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setView(dialogFiltre);

        TextView tvRestaurant = dialogFiltre.findViewById(R.id.tv_restaurant);
        TextView tvAgenceVoyage = dialogFiltre.findViewById(R.id.tv_agence);
        TextView tvBureau = dialogFiltre.findViewById(R.id.tv_bureau);
        TextView tvHotel = dialogFiltre.findViewById(R.id.tv_hotel);
        TextView tvAnnuler = dialogFiltre.findViewById(R.id.tv_annuler);

        tvRestaurant.setOnClickListener(view -> {
            tvFiltre.setText(tvRestaurant.getText());
            applyFiltre("Restaurant");
            alertDialog.dismiss();
        });

        tvAgenceVoyage.setOnClickListener(view -> {
            tvFiltre.setText(tvAgenceVoyage.getText());
            applyFiltre("Agence");
            alertDialog.dismiss();
        });

        tvBureau.setOnClickListener(view -> {
            tvFiltre.setText(tvBureau.getText());
            applyFiltre("Bureau");
            alertDialog.dismiss();
        });

        tvHotel.setOnClickListener(view -> {
            tvFiltre.setText(tvHotel.getText());
            applyFiltre("Hotel");
            alertDialog.dismiss();
        });

        tvAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void applyFiltre(String keyword) {
        displayedPlaces = new ArrayList<>();
        for (Place place : places) {
            if (place.getType().contains(keyword)) {
                displayedPlaces.add(place);
            }
        }
        onMapReady(mMap);
        placeSmallHorizontalAdapter.resplace(displayedPlaces);
    }

    @SuppressLint("RestrictedApi")
    private Marker createMarker(final Place business) {

        return new Marker(new zzt() {


            @Override
            public void remove() {
//do nothing
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public LatLng getPosition() {
                return null;
            }

            @Override
            public void setPosition(LatLng latLng) {
//do nothing
            }

            @Override
            public String getTitle() {
                return business.getName();
            }

            @Override
            public void setTitle(String s) {
//do nothing
            }

            @Override
            public String getSnippet() {
                return String.valueOf(business.getId());
            }

            @Override
            public void setSnippet(String s) {
//do nothing
            }

            @Override
            public boolean isDraggable() {
                return false;
            }

            @Override
            public void setDraggable(boolean b) {
//do nothing
            }

            @Override
            public void showInfoWindow() {
//do nothing
            }

            @Override
            public void hideInfoWindow() {
//do nothing
            }


            @Override
            public boolean isInfoWindowShown() {
                return false;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public void setVisible(boolean b) {
//do nothing
            }

            @Override
            public boolean zzj(zzt zzt) {
                return false;
            }

            @Override
            public int zzi() {
                return 0;
            }

            @Override
            public void zzg(IObjectWrapper iObjectWrapper) {
//do nothing
            }

            @Override
            public void setAnchor(float v, float v1) {
//do nothing
            }

            @Override
            public boolean isFlat() {
                return false;
            }

            @Override
            public void setFlat(boolean b) {
//do nothing
            }

            @Override
            public float getRotation() {
                return 0;
            }

            @Override
            public void setRotation(float v) {
//do nothing
            }

            @Override
            public void setInfoWindowAnchor(float v, float v1) {
//do nothing
            }

            @Override
            public float getAlpha() {
                return 0;
            }

            @Override
            public void setAlpha(float v) {
//do nothing
            }

            @Override
            public float getZIndex() {
                return 0;
            }

            @Override
            public void setZIndex(float v) {
//do nothing
            }

            @Override
            public void zze(IObjectWrapper iObjectWrapper) {
//do nothing
            }

            @Override
            public IObjectWrapper zzj() {
                return null;
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        });
    }

    private void createMarkerSelected(Marker marker) {

        try {
            if (mMap != null)
                mMap.clear();
            if (displayedPlaces != null && !displayedPlaces.isEmpty()) {
                for (int i = 0; i < displayedPlaces.size(); i++) {
                    if (String.valueOf(displayedPlaces.get(i).getId()).equals(marker.getSnippet())) {

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.valueOf(displayedPlaces.get(i).getLat()), Double.valueOf(displayedPlaces.get(i).getAlt())))
                                .anchor(0.5f, 0.5f)
                                .title(displayedPlaces.get(i).getName())
                                .snippet(String.valueOf(displayedPlaces.get(i).getId()))
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.ic_map_pin_enabled)));


                        selectedPostion = i;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    rvPlaces.smoothScrollToPosition(selectedPostion);
                                } catch (Exception e) {

                                }
                            }
                        }, 250);

                        float zoom = mMap.getCameraPosition().zoom;
                        if (zoom < 9)
                            zoom = 13;
                        LatLng markerLoc = new LatLng(Double.valueOf(displayedPlaces.get(i).getLat()), Double.valueOf(displayedPlaces.get(i).getAlt()));
                        final CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(markerLoc)      // Sets the center of the map to Mountain View
                                .zoom(zoom)                   // Sets the zoom
                                //                            .bearing(90)                // Sets the orientation of the camera to east
                                //                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   //
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } else {

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.valueOf(displayedPlaces.get(i).getLat()), Double.valueOf(displayedPlaces.get(i).getAlt())))
                                .anchor(0.5f, 0.5f)
                                .title(displayedPlaces.get(i).getName())
                                .snippet(String.valueOf(displayedPlaces.get(i).getId()))
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.ic_map_pin_disabled)));
                    }

                }

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            mMap = googleMap;
            if (mMap != null)
                mMap.clear();


            mMap.setOnMarkerClickListener(marker -> {
                try {
                    createMarkerSelected(marker);
                } catch (Exception e) {

                }
                return true;
            });


        } catch (Exception e) {

        }


        try {
            LatLng latLng = new LatLng(36.821100, 10.171000);
            createMarker();
            if (markers != null && markers.size() > 0)
                latLng = markers.get(0).getPosition();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));


        } catch (Exception e) {
        }


    }

    private void createMarker() {

        try {
            if (markers != null)
                markers.clear();

            if (displayedPlaces != null && !displayedPlaces.isEmpty()) {
                for (int i = 0; i < displayedPlaces.size(); i++) {
                    if (Double.parseDouble(displayedPlaces.get(i).getLat()) != 0f)
                        markers.add(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(displayedPlaces.get(i).getLat()), Double.parseDouble(displayedPlaces.get(i).getAlt())))
                                .anchor(0.5f, 0.5f)
                                .title(displayedPlaces.get(i).getName())
                                .snippet(String.valueOf(displayedPlaces.get(i).getId())).icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.ic_map_pin_disabled)));

                }
            }

            for (int i = 0; i < markers.size(); i++) {
                if (markers.get(i).getPosition().latitude != 0f)
                    mMap.addMarker(markers.get(i));
            }
        } catch (Exception e) {

        }

    }


    private void getPlaces() {

        Call<ListResponse<Place>> call = RetrofitServiceFacotry.getServiceApiClient().getMap();
        call.enqueue(new Callback<ListResponse<Place>>() {
            @Override
            public void onResponse(Call<ListResponse<Place>> call, Response<ListResponse<Place>> response) {
                try {
                    displayedPlaces = response.body().getData();
                    places = response.body().getData();
                    placeSmallHorizontalAdapter = new PlaceSmallHorizontalAdapter(getContext(), response.body().getData());
                    rvPlaces.setAdapter(placeSmallHorizontalAdapter);
                    rvPlaces.setItemTransformer(new ScaleTransformer.Builder()
                            .setMaxScale(1.05f)
                            .setMinScale(0.8f)
                            .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                            .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                            .build());
                    rvPlaces.setSlideOnFling(true);
                    onMapReady(mMap);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ListResponse<Place>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
