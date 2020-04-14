package com.freshbrigade.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class C_VSlectionUi extends AppCompatActivity {
    ImageView clientUser;
    ImageView vendorUser;
    String phoneNumber;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("mobilenumber");
        Log.e("mobileNumber",phoneNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__vslection_ui);
        clientUser=findViewById(R.id.buy);
        vendorUser=findViewById(R.id.sale);
        clientUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_VSlectionUi.this, ClientRegistration.class);
                intent.putExtra("mobilenumber",phoneNumber);
                Log.e("sendingtoNextAct",phoneNumber);
                startActivity(intent);

            }
        });
        vendorUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_VSlectionUi.this, Vendar_Registration.class);
                intent.putExtra("mobilenumber",phoneNumber);
                Log.e("sendingtoNextAct",phoneNumber);
                startActivity(intent);
            }
        });
    }
}
