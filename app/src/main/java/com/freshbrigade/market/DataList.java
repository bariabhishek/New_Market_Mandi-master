package com.freshbrigade.market;

public class DataList { String imageView;
    String name,minQty,vType,code,add_kart,possion;
    String venderType;

    public String getPossion() {
        return possion;
    }

    public void setPossion(String possion) {
        this.possion = possion;
    }

    public DataList(String imageView, String name, String btn, String rbtn, String code, String add_kart, String possion,String vendorType) {
        this.imageView = imageView;
        this.name = name;
        this.minQty = btn;
        this.vType = rbtn;
        this.code = code;
        this.add_kart=add_kart;
        this.possion =possion;
        this.venderType = vendorType;
    }

    public String getVenderType() {
        return venderType;
    }

    public void setVenderType(String venderType) {
        this.venderType = venderType;
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


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getAdd_kart() {
        return add_kart;
    }

    public void setAdd_kart(String add_kart) {
        this.add_kart = add_kart;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }
}