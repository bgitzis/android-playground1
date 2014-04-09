package com.gitzis.android.playground.app.view;

import java.util.Locale;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gitzis.android.playground.app.AccelerometerCollectorFragment;
import com.gitzis.android.playground.app.R;

/**
 * Barak: not sure what this class should look like yet.. if at all
 * 
 * @author from sample
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Activity mainActivity;

    public SectionsPagerAdapter(Activity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return AccelerometerCollectorFragment.newInstance(position + 1);
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