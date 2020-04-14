package com.freshbrigade.market.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.freshbrigade.market.VenderFragment.Customer_List;
import com.freshbrigade.market.VenderFragment.Money_payments;
import com.freshbrigade.market.VenderFragment.Order_Recieved_Vender;
import com.freshbrigade.market.VenderFragment.VendorListItem;

public class DemoFragmentStateAdapter extends FragmentStatePagerAdapter
{
    public DemoFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                return VendorListItem.newInstance("Customer_List, Instance 1");

            case 1:
                return Order_Recieved_Vender.newInstance("Order_Recieved_Vender, Instance 1");
           /* case 2:
                return Customer_List.newInstance("VendorListItem, Instance 1");*/
            case 2:
                return Money_payments.newInstance("Money_payments, Instance 1");

            default:
                return Customer_List.newInstance("Customer_List, Instance 1");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
