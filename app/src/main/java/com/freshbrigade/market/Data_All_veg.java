package com.freshbrigade.market;


 public class Data_All_veg  {
   String imageView;
    String name,btn,rbtn,code,add_kart,sn;
    String price;
    String qty ,getAmt , vcode , getminqty;


     public String getSn() {
         return sn;
     }

     public void setSn(String sn) {
         this.sn = sn;
     }

     public Data_All_veg(String imageView, String name, String btn, String rbtn, String code, String add_kart, String sn,String price
     ,String qty ,String getAmt , String vcode , String getminqty) {
        this.imageView = imageView;
        this.name = name;
        this.btn = btn;
        this.rbtn = rbtn;
        this.code = code;
        this.add_kart=add_kart;
        this.sn=sn;
        this.price = price;
        this.qty = qty ;
        this.getAmt =getAmt;
        this.vcode = vcode;
        this.getminqty = getminqty;
     }

     public String getPrice() {
         return price;
     }

     public void setPrice(String price) {
         this.price = price;
     }

     public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }

    public String getRbtn() {
        return rbtn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

     public String getQty() {
         return qty;
     }

     public void setQty(String qty) {
         this.qty = qty;
     }

     public String getGetAmt() {
         return getAmt;
     }

     public void setGetAmt(String getAmt) {
         this.getAmt = getAmt;
     }

     public String getVcode() {
         return vcode;
     }

     public void setVcode(String vcode) {
         this.vcode = vcode;
     }

     public String getGetminqty() {
         return getminqty;
     }

     public void setGetminqty(String getminqty) {
         this.getminqty = getminqty;
     }

     public void setRbtn(String rbtn) {
        this.rbtn = rbtn;
    }
    public String getAdd_kart() {
        return add_kart;
    }

    public void setAdd_kart(String add_kart) {
        this.add_kart = add_kart;
    }
}
