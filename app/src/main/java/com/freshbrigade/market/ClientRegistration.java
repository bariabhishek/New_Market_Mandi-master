package com.freshbrigade.market;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.Activity.Client_Side;
import com.freshbrigade.market.Activity.CustomProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ClientRegistration extends AppCompatActivity {

    EditText username, poneNumber, shopAddress;
    EditText discription,refCode;
    Spinner business_type, user_city;
    RelativeLayout button;
  //  JSONObject jsonObject,newJsonObject ,jsonObjectLast;
    SessionManage sessionManage;
    private String REGISTER_USER_API=API.CLIENT_REGISTER;
    String name,phoneNumber,shopaddress,disc,citys;
    RelativeLayout img_1,img_2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageView img_show;
    String base64String = "";
    String ref;
    CustomProgress customProgress = new CustomProgress(this);

    TextView tv_dis,tv_add,tv_ref;
    ImageView dArr,aArr;

    String[] bType = { "Retailer", "Wholesaler", "Distributor", "Manufacturer", "Brand","Agent"};
    String[] uCity = { "jaipur", "chandigarh", "delhi", "ahmedabad"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        business_type = findViewById(R.id.business_type);
        user_city = findViewById(R.id.city_);
        initilisation();

        ArrayAdapter cityAdeptor = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,uCity);
        cityAdeptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_city.setAdapter(cityAdeptor);


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,bType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        business_type.setAdapter(aa);


        tv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discription.setVisibility(View.VISIBLE);
                shopAddress.setVisibility(View.GONE);
                dArr.setVisibility(View.GONE);
                aArr.setVisibility(View.VISIBLE);
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discription.setVisibility(View.GONE);
                shopAddress.setVisibility(View.VISIBLE);
                dArr.setVisibility(View.VISIBLE);
                aArr.setVisibility(View.GONE);

            }
        });

        tv_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refCode.setVisibility(View.VISIBLE);
            }
        });





                img_1.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        discription.setVisibility(View.GONE);
                        shopAddress.setVisibility(View.GONE);
                        dArr.setVisibility(View.GONE);
                        aArr.setVisibility(View.GONE);

                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                        }
                        else
                        {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }
                    }
                });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(username.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all details properly!", Toast.LENGTH_LONG).show();
                }
                else{
                    name=username.getText().toString();
                    phoneNumber=poneNumber.getText().toString();
                    shopaddress=shopAddress.getText().toString();
                    disc=discription.getText().toString();
                     ref = refCode.getText().toString();
                   // citys=city.getText().toString();

                    customProgress.show("");
                    apiRegistration(name,phoneNumber,shopaddress,disc,citys);

                 //   Log.e("asd","working" );
                }
            }
        });

    }

    private void initilisation() {
        refCode = findViewById(R.id.et_ref);
        username = findViewById(R.id.et_reg_name);
        sessionManage = new SessionManage(getApplicationContext());
        poneNumber = findViewById(R.id.et_reg_phone);

        dArr = findViewById(R.id.disAeroo);
        aArr = findViewById(R.id.addAeroo);

        tv_ref = findViewById(R.id.tvpromo);

        tv_add = findViewById(R.id.add_);
        tv_dis = findViewById(R.id.dis);


        poneNumber.setText(getIntent().getStringExtra("mobilenumber"));

        shopAddress = findViewById(R.id.edit_text_add);
        discription=findViewById(R.id.edit_text_dis);
        img_1=findViewById(R.id.uploadPhoto1);
        img_show = findViewById(R.id.img_show);
       // img_2=findViewById(R.id.uploadPhoto2);
        button = findViewById(R.id.btnRegister);

    }

    private void apiRegistration(final String name, final String shopn, final String shopadd, final String pincode, final String city) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_USER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            customProgress.hide();

                            if(status.equals("ok")) {


                                String c_code = jsonObject.getString("c_code");
                                String c_mobile = jsonObject.getString("c_mobile");
                                String userType = jsonObject.getString("userType");
                                String user_activation_status = jsonObject.getString("user_activation_status");

                                sessionManage.create_client_session(c_code,c_mobile,userType,user_activation_status);
                                sessionManage.set_user(userType);


                                Intent intent = new Intent(getApplicationContext(), Client_Side.class);

                                startActivity(intent);
                                finish();

                            }
                            else if(status.equals("elert")){
                                Toast.makeText(getApplicationContext(),"user already registor this number",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    //    Log.e("response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String>params=new HashMap<String,String>();

             //   Log.e("xyz",name+shopn+shopadd+city+pincode+img_1 );

                SessionManage sessionManage = new SessionManage(getApplicationContext());


                HashMap<String,String > user;
                user = sessionManage.get_client_session();
                String mobile = user.get(sessionManage.MOBILE);
                String cose_c =  user.get(sessionManage.CODE);


                params.put("c_name",name);
           //     params.put("c_shop_name",shopn);
                params.put("c_mobile",phoneNumber);
                params.put("c_address",shopadd);
                params.put("c_city",user_city.getSelectedItem().toString());
               // params.put("c_pincode",pincode);
                params.put("action","update");
                params.put("dec",disc);
                params.put("idImage",base64String);
                params.put("shopImage","");
                params.put("reffralCode",ref);
                params.put("business_type",business_type.getSelectedItem().toString());
                params.put("code",cose_c);

                      return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_show.setImageBitmap(photo);
            img_show.setVisibility(View.VISIBLE);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //    Log.e("sdsafdsdf","fsdfsdf");

         //   Log.e("sdsafdsdf",base64String);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
