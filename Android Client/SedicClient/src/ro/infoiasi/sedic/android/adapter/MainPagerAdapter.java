package ro.infoiasi.sedic.android.adapter;

import ro.infoiasi.sedic.android.fragment.SelectAdjuvantsFragment;
import ro.infoiasi.sedic.android.fragment.SelectTherapiesFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return new SelectAdjuvantsFragment();
        case 1:
            return new SelectTherapiesFragment();
        case 2:
            return new SelectAdjuvantsFragment();
        default:
            return new SelectAdjuvantsFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return "Adjuvants";
        case 1:
            return "Therapeutics";
        case 2:
            return "Medical Factors";
        }
        return "";
    }

}
