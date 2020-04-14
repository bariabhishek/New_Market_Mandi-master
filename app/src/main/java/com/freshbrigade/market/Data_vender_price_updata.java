package com.freshbrigade.market;

class Data_vender_price_updata {
    String imageView;
    String name,btn,rbtn,edittext;
    String pcode,todayprice;

    public Data_vender_price_updata(String imageView, String name, String btn, String rbtn, String edittext, String pcode , String todayprice) {
        this.imageView = imageView;
        this.name = name;
        this.btn = btn;
        this.rbtn = rbtn;
        this.edittext = edittext;
        this.pcode=pcode;
        this.todayprice=todayprice;
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

    public void setRbtn(String rbtn) {
        this.rbtn = rbtn;
    }

    public String getEdittext() {
        return edittext;
    }

    public void setEdittext(String edittext) {
        this.edittext = edittext;
    }
    public String getPcode() {
        return pcode;
    }

    public void getPcode(String pcode) {
        this.pcode = pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getTodayprice() {
        return todayprice;
    }

    public void setTodayprice(String todayprice) {
        this.todayprice = todayprice;
    }
}
