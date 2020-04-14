package com.freshbrigade.market;

class QuantityData {

   // int plus;
//    int minus;
    String et;
    String name;
    String amount;
    String pcode;
    String vcode;
    String qty,min_qty;

    public String getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(String min_qty) {
        this.min_qty = min_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public QuantityData(String et, String name, String amount, String pcode, String vcode,String qty,String min_qty) {
      //  this.plus = plus;
//        this.minus = minus;
        this.et = et;
        this.name = name;
        this.amount = amount;
        this.pcode = pcode;
        this.vcode=vcode;
        this.qty=qty;
        this.min_qty = min_qty;
    }
    public String getPcode() {
        return pcode;
    }

    public void getPcode(String pcode) {
        this.pcode = pcode;
    }
}
