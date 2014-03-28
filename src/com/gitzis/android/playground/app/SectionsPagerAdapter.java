package com.gitzis.android.playground.app;

import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final MainActivity mainActivity;

    public SectionsPagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 1; // Show 3 total pages.
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
        case 0:
            return this.mainActivity.getString(R.string.title_section1).toUpperCase(l);
        case 1:
            return this.mainActivity.getString(R.string.title_section2).toUpperCase(l);
        case 2:
            return this.mainActivity.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}