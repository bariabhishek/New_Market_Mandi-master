package com.freshbrigade.market.ModelsData;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class OrdeProductModel implements Serializable {

    String productCode;
    String quantity;
    String pname;
    String price;
    String v_code;
    String min_qty;

    public OrdeProductModel(String code, String quantity, String vegname, String vegprice, String vcode, String min_qty) {
        this.productCode = code;
        this.quantity = quantity;
        this.pname = vegname;
        this.price = vegprice;
        this.v_code = vcode;
        this.min_qty = min_qty;
    }



    public String getCode() {
        return productCode;
    }

    public void setCode(String code) {
        this.productCode = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVegname() {
        return pname;
    }

    public void setVegname(String vegname) {
        this.pname = vegname;
    }

    public String getVegprice() {
        return price;
    }

    public void setVegprice(String vegprice) {
        this.price = vegprice;
    }

    public String getVcode() {
        return v_code;
    }

    public void setVcode(String vcode) {
        this.v_code = vcode;
    }

    public String getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(String min_qty) {
        this.min_qty = min_qty;
    }
}
