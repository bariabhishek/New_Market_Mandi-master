package com.freshbrigade.market.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.freshbrigade.market.Adapter.DemoStateFragmentAdapter;
import com.freshbrigade.market.ClientFragment.His_tory;
import com.freshbrigade.market.ClientFragment.Recycle_Main_Activity;
import com.freshbrigade.market.ClientFragment.Veg_Home;
import com.freshbrigade.market.ClientFragment.Wall_et;
import com.freshbrigade.market.NumberActivity;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;
import com.freshbrigade.market.Summmry;
import com.freshbrigade.market.VendarActivation;
import com.freshbrigade.market.Vendar_Registration;

import io.fabric.sdk.android.Fabric;

public class Client_Side extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawereLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    TextView select,freshTool;
    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;

    DemoStateFragmentAdapter adapter;

    public static BottomNavigationView bottomNav;
    ViewPager mViewPager;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_client__side);



        mDrawereLayout = findViewById(R.id.Main1);
        toolbar = findViewById(R.id.tool2);
        mViewPager = findViewById(R.id.pager12);
        select=findViewById(R.id.select1);
        freshTool=findViewById(R.id.freshTool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView = findViewById(R.id.navigation1);
        navigationView.setNavigationItemSelectedListener(Client_Side.this);

        adapter = new DemoStateFragmentAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawereLayout, R.string.open, R.string.close);
        mDrawereLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //loadFragment(new Veg_Home());


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawereLayout.openDrawer(GravityCompat.START);
            }
        });


        bottomNav = findViewById(R.id.bottombar1);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.Home007 :

                        mViewPager.setCurrentItem(0);
                        select.setVisibility(View.VISIBLE);
                        freshTool.setVisibility(View.GONE);

                        break;

                    case R.id.History:
                        mViewPager.setCurrentItem(1);
                        select.setVisibility(View.GONE);
                        freshTool.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Recycle007:
                        mViewPager.setCurrentItem(2);
                        select.setVisibility(View.VISIBLE);
                        freshTool.setVisibility(View.GONE);


                        break;

                    case R.id.Wallet007:
                        mViewPager.setCurrentItem(3);
                        select.setVisibility(View.GONE);
                        freshTool.setVisibility(View.VISIBLE);

                        break;


                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i)
            {

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNav.getMenu().getItem(0).setChecked(false);
                }
                //Log.d("page", "onPageSelected: "+position);
                bottomNav.getMenu().getItem(i).setChecked(true);
                prevMenuItem = bottomNav.getMenu().getItem(i);

                if (i==0)
                {
                    select.setVisibility(View.VISIBLE);
                    freshTool.setVisibility(View.GONE);
                }
                else if (i==1)
                {
                    select.setVisibility(View.GONE);
                    freshTool.setVisibility(View.VISIBLE);
                }
                else if (i==2)
                {
                    select.setVisibility(View.VISIBLE);
                    freshTool.setVisibility(View.GONE);
                }
                else if (i==3)
                {
                    select.setVisibility(View.GONE);
                    freshTool.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageScrollStateChanged(int i)
            {


            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mDrawereLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawereLayout.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to close Fresh Brigade", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        Fragment fragment = null;
        if (id == R.id.editprofile) {
            Intent intent = new Intent(this , Vendar_Registration.class);
            startActivity(intent);

        }else
        if (id == R.id.wallet_text_activity) {

            fragment=new Wall_et();
            //loadFragment(fragment);

//
//            Intent intent = new Intent(this , Wallet.class);
//            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.Vendor) {
            Intent intent = new Intent(this , VendarActivation.class);
            startActivity(intent);

//            Intent intent = new Intent(this , VendarActivation.class);
//            startActivity(intent);

        } else if (id == R.id.history) {

            fragment=new His_tory();
           // loadFragment(fragment);

//            Intent intent = new Intent(this , History.class);
//            startActivity(intent);

        } else if (id == R.id.logout) {

            SessionManage sessionManage = new SessionManage(this);
            sessionManage.set_AddProduct("setAll",0);
            sessionManage.logOut();

            Intent i = new Intent(getApplicationContext(), NumberActivity.class);

            startActivity(i);
            finish();


        }

        else if (id == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download Fresh Brigade App : https://play.google.com/store/apps/details?id=com.freshbrigade.market&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


        }

        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mDrawereLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

    @Override
    protected void onStart() {
        boolean c = isNetworkAvailable();
        if (c == true) {
           // loadFragment(new Veg_Home());
            bottomNav.setVisibility(View.VISIBLE);
            ImageView noWifi=findViewById(R.id.noWifi);
            noWifi.setVisibility(View.GONE);
            TextView textView=findViewById(R.id.No_Wifi1);
            textView.setVisibility(View.GONE);

            //LoadJSon();
        } else {

            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            bottomNav.setVisibility(View.GONE);
            ImageView noWifi=findViewById(R.id.noWifi);
            noWifi.setVisibility(View.VISIBLE);
            TextView textView=findViewById(R.id.No_Wifi1);
            textView.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }


}
