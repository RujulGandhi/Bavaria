package com.bavaria.group.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bavaria.group.Fragment.ContactUs;
import com.bavaria.group.Fragment.WorkingHours;

/**
 * Created by archirayan on 2/27/2016.
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ContactUs tab1 = new ContactUs();
                return tab1;
            case 1:
                WorkingHours tab2 = new WorkingHours();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}