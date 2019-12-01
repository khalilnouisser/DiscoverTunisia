package com.discover.tunisia.activities;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.discover.tunisia.R;
import com.discover.tunisia.config.Constante;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.Sejour;
import com.discover.tunisia.discover.fragments.DetailEventFragment;
import com.discover.tunisia.sejours.DetailsSejourFragment;
import com.discover.tunisia.sejours.OneSejourFragment;

import java.util.EventListener;

public class TransitionActivity extends AppCompatActivity {

    private Event event;
    private int action;
    private Fragment currentFragment;
    private Sejour sejour;

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
        action = intent.getIntExtra(Constante.ACTION, 1);
        if(action == Constante.DETAILS_EVENT_FRAGMENT)
        {
            currentFragment = DetailEventFragment.newInstance(event);
        }

        else if(action == Constante.DETAILS_SEJOUR_FRAGMENT)
        {
            currentFragment = DetailsSejourFragment.newInstance(sejour);
        }
        else if(action == Constante.ONE_SEJOUR_FRAGMENT)
        {
            currentFragment = OneSejourFragment.newInstance(sejour);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, currentFragment);
        fragmentTransaction.commit();
    }
}
