package com.freshbrigade.market;

class Vender_Slection_Item_Data {
    int imageView;
    String name,btn,rbtn;

    public Vender_Slection_Item_Data(int imageView, String name, String btn, String rbtn) {
        this.imageView = imageView;
        this.name = name;
        this.btn = btn;
        this.rbtn = rbtn;

    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
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
}