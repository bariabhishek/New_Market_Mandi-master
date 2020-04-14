package com.freshbrigade.market;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.freshbrigade.market.Activity.Client_Side;
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;

import io.fabric.sdk.android.Fabric;
import java.util.HashMap;

public class SleshScreen extends AppCompatActivity {


    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    SessionManage sessionManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_slesh_screen);

        sessionManage = new SessionManage(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                preferencs();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }



    public void preferencs() {


        SharedPreferences sharedPreferences = getSharedPreferences("chack", MODE_PRIVATE);

        if (sharedPreferences.contains("NUMBERCHACK")) {

             if (sharedPreferences.getString("NUMBERCHACK", null).equals("true")) {

                sessionManage.get_user();
               String user =sessionManage.get_user();
                String auth = sessionManage.get_activeCode();


                if (user.equals("client")) {

                    Intent intent = new Intent(getApplicationContext(), Client_Side.class);
                    startActivity(intent);
                    finish();

                } else {

                    HashMap<String, String> userData;
                    userData =sessionManage.get_vendor_session();
                    String activation_status = userData.get(sessionManage.USER_STATUS);
                    Log.e("status",activation_status);

                    Intent intent = new Intent(getApplicationContext(), ListOfVegetablefor_Vender.class);
                        startActivity(intent);
                       finish();

//                    if(activation_status.equals("1")){
////                        Intent intent = new Intent(getApplicationContext(), ListOfVegetablefor_Vender.class);
////                        startActivity(intent);
////                        finish();
//
//                    }
//
//                   else {
//
//                         Intent intent = new Intent(getApplicationContext(), VendorDetailUpdate.class);
//                        startActivity(intent);
//                        finish();
//                    }
                }

            } else {

                Intent intent = new Intent(getApplicationContext(), NumberActivity.class);
                startActivity(intent);
                finish();
            }
        }else {

            Intent intent = new Intent(getApplicationContext(), NumberActivity.class);
            startActivity(intent);
            finish();
        }
    }}
