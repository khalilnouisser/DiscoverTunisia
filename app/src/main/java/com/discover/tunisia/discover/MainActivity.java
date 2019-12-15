package com.discover.tunisia.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discover.tunisia.R;
import com.discover.tunisia.activities.TransitionActivity;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.Ui.CustomViewPager;
import com.discover.tunisia.discover.adapters.HomeViewPagerAdapter;
import com.discover.tunisia.drawers.adapters.LangueAdapter;
import com.discover.tunisia.drawers.entities.Langue;
import com.discover.tunisia.login.entities.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_user)
    RoundedImageView ivUser;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_adress)
    TextView tvUserAdress;
    @BindView(R.id.iv_bookmark)
    ImageView ivBookmark;
    @BindView(R.id.layout_user_info)
    RelativeLayout layoutUserInfo;
    @BindView(R.id.layout_header)
    LinearLayout layoutHeader;
    @BindView(R.id.tv_a_la_une)
    TextView tvALaUne;
    @BindView(R.id.tv_actualite)
    TextView tvActualite;
    @BindView(R.id.tv_sejour)
    TextView tvSejour;
    @BindView(R.id.tv_incontournables)
    TextView tvIncontournables;
    @BindView(R.id.tv_parametre)
    TextView tvParametre;
    @BindView(R.id.layout_select_language)
    LinearLayout layoutSelectLanguage;
    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;
    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;
    @BindView(R.id.iv_facebook)
    ImageView ivFacebook;
    @BindView(R.id.slider)
    LinearLayout slider;
    @BindView(R.id.layout_drawer)
    DrawerLayout layoutDrawer;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.tv_lougout)
    TextView tvLougout;
    @BindView(R.id.view_logout)
    View viewLogout;
    @BindView(R.id.tv_langue)
    TextView tvLangue;
    private DrawerLayout mDrawerLayout;
    private double latitude, longitude;
    public LocationManager mLocManager;
    List<Langue> langues = new ArrayList<>();
    private final static int MY_PERMISSIONS_REQUEST_LOCALISATION = 128;

    private final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        initDrawer();
        initNavigationMenu();
        initViewPager();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCALISATION);

            }
        }
        try {
            getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initLangue();
        } catch (Exception ignored) {

        }
    }

    private void initLangue() {
        Locale[] locales = new Locale[]{
                Locale.getDefault(),
                Locale.FRENCH,
                new Locale("ar", "DZ")
        };
        for (Locale locale : locales) {
            langues.add(new Langue(locale.getDisplayLanguage(locale), false,locale));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCALISATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // Permission Denied
                Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager);
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(4);
        viewpager.setPagingEnabled(false);
    }

    private void initDrawer() {
        try {
            Utils.changeStatusBarColors(this, R.color.white);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDrawerLayout = findViewById(R.id.layout_drawer);
        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(t);
        t.syncState();
        ivMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.START));
        ivClose.setOnClickListener(v -> mDrawerLayout.closeDrawer(Gravity.START));

        ivSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
            intent.putExtra(Constante.ACTION, Constante.SEARCH);
            startActivity(intent);
        });

        ivMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
            intent.putExtra(Constante.ACTION, Constante.MAP);
            startActivity(intent);
        });
        try {
            layoutSelectLanguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectLangue();
                }
            });
        } catch (Exception ignored) {

        }
        try {
            if (Preference.getCurrentCompte(getApplicationContext()) != null) {
                initUser(Objects.requireNonNull(Preference.getCurrentCompte(getApplicationContext())));
                viewLogout.setVisibility(View.VISIBLE);
                tvLougout.setVisibility(View.VISIBLE);
            } else {
                tvUserName.setText("Connect");
                tvUserAdress.setVisibility(View.GONE);
                viewLogout.setVisibility(View.GONE);
                tvLougout.setVisibility(View.GONE);
            }
            tvLougout.setOnClickListener(v -> {
                Preference.logout(getApplicationContext());
                layoutDrawer.closeDrawer(Gravity.START);
                tvUserName.setText("Connect");
                tvUserAdress.setVisibility(View.GONE);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tvALaUne.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.AllEventFragment);
                startActivity(intent);
                layoutDrawer.closeDrawer(Gravity.START);
            });
        } catch (Exception ignored) {

        }
        try {
            tvSejour.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.AllSejourFragment);
                startActivity(intent);
                layoutDrawer.closeDrawer(Gravity.START);
            });
        } catch (Exception ignored) {

        }
        try {
            tvIncontournables.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.AllIncontournableFragment);
                startActivity(intent);
                layoutDrawer.closeDrawer(Gravity.START);
            });
        } catch (Exception ignored) {

        }
        try {
            tvActualite.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra(Constante.ACTION, Constante.NewsFragment);
                startActivity(intent);
                layoutDrawer.closeDrawer(Gravity.START);
            });
        } catch (Exception ignored) {

        }
    }

    private void showSelectLangue() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") final View viewAlerte = inflater.inflate(R.layout.layout_langue, null);
        RecyclerView rvLangue = viewAlerte.findViewById(R.id.rv_langue);
        LangueAdapter adapter = new LangueAdapter(getApplicationContext(), langues);
        rvLangue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLangue.setAdapter(adapter);
        dialogBuilder.setView(viewAlerte);
        dialogBuilder.create();
        final AlertDialog alertDialog = dialogBuilder.show();
        LangueAdapter.setOnLangueChangedListener(langue -> {
            alertDialog.dismiss();
            tvLangue.setText(langue.getName());
                    Resources res = getApplicationContext().getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(langue.getLocale()); // API 17+ only.
                    res.updateConfiguration(conf, dm);
        }
        );

    }

    private void initUser(User currentCompte) {
        tvUserName.setText(currentCompte.getName());
        tvUserAdress.setText(currentCompte.getEmail());
        tvUserName.setVisibility(View.VISIBLE);
        tvUserAdress.setVisibility(View.VISIBLE);
        if (currentCompte.getAvatar() != null && !currentCompte.getAvatar().isEmpty()) {
            Utils.setRoundedImageUri(currentCompte.getAvatar(), getApplicationContext(), ivUser);
        }
    }

    private void initNavigationMenu() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Menu menu = bottomNavigation.getMenu();
                menu.getItem(0).setIcon(R.drawable.home_disabled);
                menu.getItem(1).setIcon(R.drawable.navigation_disabled);
                menu.getItem(2).setIcon(R.drawable.photos_disabled);
                menu.getItem(3).setIcon(R.drawable.info_disabled);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        item.setIcon(R.drawable.home);
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.action_navigation:
                        item.setIcon(R.drawable.navigation);
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.action_photo:
                        item.setIcon(R.drawable.photos);
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.action_info:
                        item.setIcon(R.drawable.info);
                        viewpager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }


    //Get location
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        assert locationManager != null;
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if (myLocation != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 3);
                Utils.query = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
