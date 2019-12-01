package com.discover.tunisia.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.Ui.CustomViewPager;
import com.discover.tunisia.discover.adapters.HomeViewPagerAdapter;
import com.discover.tunisia.discover.fragments.HomeFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
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
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDrawer();
        initNavigationMenu();
        initViewPager();
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
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });
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
                        viewpager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
    }
}
