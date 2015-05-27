package com.example.kamil.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kamil on 25.05.15.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0: return BasicInformationFragment.newInstance("", "");
            case 1: return AdditionalInformationFragment.newInstance("", "");
            case 2: return UpcomingDaysFragment.newInstance("", "");
            default: return UpcomingDaysFragment.newInstance("", "");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
