package com.freshbrigade.market;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManage {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;



    public   final  String PREF_NAME="PREF_NAME";
    private static final String LOGIN="LOGIN";
    public  final String CODE = "C_CODE";
    public   final String MOBILE = "MOBILE";
    public final String USERTYPE = "USERTYPE";
    public final String ACTIVATION_CODE = "ACTIVATION_CODE";

    public final String VENDOR_TYPE = "VENDOR_TYPE";
    public final String USER_STATUS = "USER_STATUS";
    public final String ADD_PRODUCT = "ADD_PRODUCT";
    public final String ORDER = "ORDER";
    public final String CHECK_PRODUCT = "CHECK_PRODUCT";

    public SessionManage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public  boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);

    }


    public void set_user(String usertype){
        editor.putString(USERTYPE,usertype);
        editor.commit();
    }
    public String get_user(){

        String usertype=sharedPreferences.getString(USERTYPE,null);
        return usertype;
    }

    public String get_activeCode(){

        String usertype=sharedPreferences.getString(ACTIVATION_CODE,null);
        return usertype;
    }


    public void set_Add_Product(Boolean b){
        Log.e("okk",String.valueOf(b));
        editor.putBoolean(ADD_PRODUCT,b);

        editor.commit();
    }



    public Boolean get_Add_Product(){

        Boolean b = sharedPreferences.getBoolean(ADD_PRODUCT,false);

        return  b;
    }



    public void set_Add_Order(Boolean b){
        Log.e("okk",String.valueOf(b));
        editor.putBoolean(ORDER,b);

        editor.commit();
    }



    public Boolean get_Add_Order(){

        Boolean b = sharedPreferences.getBoolean(ORDER,false);

        return  b;
    }

    public void create_vendor_session(String c_code,String c_mobile ,String userStatus,String vendore_type){

        editor.putString(CODE,c_code);
        editor.putString(MOBILE,c_mobile);
        editor.putString(USER_STATUS,userStatus);
        editor.putString(VENDOR_TYPE,vendore_type);
        editor.commit();
    }

    public void create_client_session(String c_code,String c_mobile ,String usertype,String activation_code){

        editor.putString(CODE,c_code);
        editor.putString(MOBILE,c_mobile);
        editor.putString(USERTYPE,usertype);
        editor.putString(ACTIVATION_CODE,activation_code);
        editor.commit();
    }


    public HashMap<String,String>  get_vendor_session(){


        HashMap<String,String> user = new HashMap<>();
        user.put(USERTYPE, sharedPreferences.getString(USERTYPE,null));
        user.put(MOBILE, sharedPreferences.getString(MOBILE,null));
        user.put(CODE, sharedPreferences.getString(CODE,null));
        user.put(USER_STATUS, sharedPreferences.getString(USER_STATUS,null));
        user.put(VENDOR_TYPE, sharedPreferences.getString(VENDOR_TYPE,null));
        return user;
    }

    public HashMap<String,String>  get_client_session(){


        HashMap<String,String> user = new HashMap<>();
        user.put(USERTYPE, sharedPreferences.getString(USERTYPE,null));
        user.put(MOBILE, sharedPreferences.getString(MOBILE,null));
        user.put(CODE, sharedPreferences.getString(CODE,null));
        user.put(ACTIVATION_CODE, sharedPreferences.getString(ACTIVATION_CODE,null));
        return user;
    }


    public void logOut(){
        editor.clear().apply();
        sharedPreferences = context.getSharedPreferences("chack",PRIVATE_MODE);
        SharedPreferences.Editor num_editor = sharedPreferences.edit();
        num_editor.clear().apply();




    }




    public void set_AddProduct(String action,int add){
        int b = sharedPreferences.getInt(CHECK_PRODUCT,0);
        int a =0;
        if(action.equals("add")){
            a=b+add;
            editor.putInt(CHECK_PRODUCT,a);

        }
       else if(action.equals("setAll")){
            Log.e("setAll",String.valueOf(add));
            editor.putInt(CHECK_PRODUCT,add);
        }
        else{
            a=b-add;
            editor.putInt(CHECK_PRODUCT,a);
        }

        editor.commit();
    }



    public int get_AddProduct(){

        int b = sharedPreferences.getInt(CHECK_PRODUCT,0);

        return  b;
    }
}
