package com.freshbrigade.market.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.freshbrigade.market.ClientFragment.His_tory;
import com.freshbrigade.market.ClientFragment.Recycle_Main_Activity;
import com.freshbrigade.market.ClientFragment.Veg_Home;
import com.freshbrigade.market.ClientFragment.Wall_et;
public class DemoStateFragmentAdapter extends FragmentStatePagerAdapter
{

    public DemoStateFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                return Veg_Home.newInstance("Veg_Home, Instance 1");

            case 1:
                return His_tory.newInstance("His_tory, Instance 1");
            case 2:
                return Recycle_Main_Activity.newInstance("Recycle_Main_Activity, Instance 1");
            case 3:
                return Wall_et.newInstance("Wall_et, Instance 1");
            default:
                return Veg_Home.newInstance("Veg_Home, Instance 1");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
