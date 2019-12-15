package com.discover.tunisia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.DetailsSejour;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.discover.entities.Sejour;
import com.discover.tunisia.discover.fragments.DetailEventFragment;
import com.discover.tunisia.discover.fragments.MapFragment;
import com.discover.tunisia.drawers.ActualiteFragment;
import com.discover.tunisia.drawers.AlaUneFragment;
import com.discover.tunisia.drawers.AllIncontournableFragment;
import com.discover.tunisia.drawers.AllSejourFragment;
import com.discover.tunisia.drawers.SearchFragment;
import com.discover.tunisia.incontournables.IncontournableFragment;
import com.discover.tunisia.login.fragment.CreateAccountFragment;
import com.discover.tunisia.navigations.DetailsNavigationFragment;
import com.discover.tunisia.photos.OneImageFragment;
import com.discover.tunisia.photos.entities.Photo;
import com.discover.tunisia.sejours.DetailsSejourFragment;
import com.discover.tunisia.sejours.OneSejourFragment;

public class TransitionActivity extends AppCompatActivity {

    private Event event;
    private int action;
    private Fragment currentFragment;
    private Sejour sejour;
    private Photo photo;
    private DetailsSejour detailsSejour;
    private Incontournable incontournable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        try {
            Utils.changeStatusBarColors(this, R.color.white);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra(Constante.EVENT);
        sejour = (Sejour) intent.getSerializableExtra(Constante.SEJOUR);
        detailsSejour = (DetailsSejour) intent.getSerializableExtra(Constante.DETAILS_SEJOUR);
        incontournable = (Incontournable) intent.getSerializableExtra(Constante.INCONTOURNABLE);
        photo = (Photo) intent.getSerializableExtra(Constante.PHOTO);
        action = intent.getIntExtra(Constante.ACTION, 1);
        if (action == Constante.DETAILS_EVENT_FRAGMENT) {
            currentFragment = DetailEventFragment.newInstance(event);
        } else if (action == Constante.DETAILS_SEJOUR_FRAGMENT) {
            currentFragment = DetailsSejourFragment.newInstance(sejour);
        } else if (action == Constante.ONE_SEJOUR_FRAGMENT) {
            currentFragment = OneSejourFragment.newInstance(detailsSejour);
        } else if (action == Constante.DETAILS_INCONTOURNABLE_FRAGMENT) {
            currentFragment = IncontournableFragment.newInstance(incontournable);
        } else if (action == Constante.DETAILS_NAVIGATION_FRAGMENT) {
            currentFragment = DetailsNavigationFragment.newInstance();
        } else if (action == Constante.DETAILS_PHOTO_FRAGMENT) {
            currentFragment = OneImageFragment.newInstance(photo);
        } else if (action == Constante.CREATE_ACCOUNT_FRAGMENT) {
            currentFragment = CreateAccountFragment.newInstance();
        } else if (action == Constante.AllEventFragment) {
            currentFragment = AlaUneFragment.newInstance();
        } else if (action == Constante.AllSejourFragment) {
            currentFragment = AllSejourFragment.newInstance();
        } else if (action == Constante.AllIncontournableFragment) {
            currentFragment = AllIncontournableFragment.newInstance();
        } else if (action == Constante.NewsFragment) {
            currentFragment = ActualiteFragment.newInstance();
        } else if (action == Constante.SEARCH) {
            currentFragment = SearchFragment.newInstance();
        } else if (action == Constante.MAP) {
            currentFragment = MapFragment.newInstance();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, currentFragment);
        fragmentTransaction.commit();
    }
}
