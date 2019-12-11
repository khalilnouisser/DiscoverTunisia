package com.discover.tunisia.discover.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.discover.tunisia.discover.fragments.HomeFragment;
import com.discover.tunisia.navigations.NavigationFragment;
import com.discover.tunisia.photos.PhotosFragmentFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {


    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return NavigationFragment.newInstance();
            case 2:
                return PhotosFragmentFragment.newInstance();
            case 3:
                return HomeFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }


}

