package com.freshbrigade.market.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.freshbrigade.market.Adapter.DemoFragmentStateAdapter;
import com.freshbrigade.market.ClientFragment.His_tory;
import com.freshbrigade.market.ClientFragment.Veg_Home;
import com.freshbrigade.market.NumberActivity;
import com.freshbrigade.market.SessionManage;
import com.freshbrigade.market.VenderFragment.Customer_List;
import com.freshbrigade.market.VenderFragment.Money_payments;
import com.freshbrigade.market.VenderFragment.Order_Recieved_Vender;
import com.freshbrigade.market.VenderFragment.VendorListItem;
import com.freshbrigade.market.R;
import com.freshbrigade.market.Vender_Update_price;
import com.freshbrigade.market.VendorDetailUpdate;

import io.fabric.sdk.android.Fabric;

public class ListOfVegetablefor_Vender extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    private DrawerLayout mDrawereLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog progressBar;
    DemoFragmentStateAdapter adapter;

    public static BottomNavigationView bottomNav;
    MenuItem prevMenuItem;
    private LinearLayout titleAppbar;
    private boolean isHideToolbarView = false;

    ViewPager mViewPager;
    TextView select,freshTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_list_of_vegetablefor__vender);

        mViewPager = findViewById(R.id.pager);

        mDrawereLayout = findViewById(R.id.Main);
        toolbar = findViewById(R.id.tool12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(ListOfVegetablefor_Vender.this);
        select=findViewById(R.id.select);
        freshTool=findViewById(R.id.freshTool1);

        adapter = new DemoFragmentStateAdapter(getSupportFragmentManager());


      // loadFragment(new VendorListItem());
        mViewPager.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawereLayout, R.string.open, R.string.close);
        mDrawereLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawereLayout.openDrawer(GravityCompat.START);
            }
        });



        bottomNav = findViewById(R.id.bottombar007);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.product :/* fragment=new VendorListItem();*/

                        mViewPager.setCurrentItem(0);
                        select.setVisibility(View.VISIBLE);
                        freshTool.setVisibility(View.GONE);
                                         break;

                    case R.id.Order:
                        mViewPager.setCurrentItem(1);
                        select.setVisibility(View.GONE);
                        freshTool.setVisibility(View.VISIBLE);
                        /*fragment=new Order_Recieved_Vender();*/

                        break;
               /*     case R.id.Customer: fragment=new Customer_List();


                        break;*/

                    case R.id.money_payment :
                        mViewPager.setCurrentItem(2);
                        select.setVisibility(View.GONE);
                        freshTool.setVisibility(View.VISIBLE);
                        /*fragment = new Money_payments();*/

                    break;
                }
               /* return loadFragment(fragment);*/
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
                else
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        if (id == R.id.editprofilevendor) {

            Intent intent = new Intent(getApplicationContext(), VendorDetailUpdate.class);
            startActivity(intent);

        } else if (id == R.id.historyvendor) {

            fragment=new Order_Recieved_Vender();
           // loadFragment(fragment);


        }


        else if (id == R.id.vendoreshare) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download Fresh Brigade App : https://play.google.com/store/apps/details?id=com.freshbrigade.market&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


        }
        else if (id == R.id.logoutvendor) {


            SessionManage sessionManage = new SessionManage(this);
          //  sessionManage.set_AddProduct("setAll",0);
            sessionManage.logOut();
            Intent i = new Intent(getApplicationContext(), NumberActivity.class);
            startActivity(i);
            finish();



        }
//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }


        mDrawereLayout.closeDrawer(GravityCompat.START);
        return true;
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


            bottomNav.setVisibility(View.VISIBLE);
            ImageView noWifi=findViewById(R.id.no_Wifi);
            noWifi.setVisibility(View.GONE);
            TextView textView=findViewById(R.id.No_Wifi);
            textView.setVisibility(View.GONE);
            createProgressBar();
            progressBar.dismiss();


            //LoadJSon();
        } else {
            createProgressBar();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.create();
            }
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            bottomNav.setVisibility(View.GONE);
            ImageView noWifi=findViewById(R.id.no_Wifi);
            noWifi.setVisibility(View.VISIBLE);
            TextView textView=findViewById(R.id.No_Wifi);
            textView.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }

    public void createProgressBar(){
        progressBar= new ProgressDialog(this);
        // progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading assets");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
         progressBar.show();//displays the progress bar
    }

   /* private void sendNotification(String message, String title, int id) {
        Intent intent = new Intent(this, Vender_Update_price.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id *//* ID of notification *//*,
                notificationBuilder.build());
    }*/
}
