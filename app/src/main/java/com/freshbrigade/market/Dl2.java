package com.freshbrigade.market;

class Dl2 {

    String name,price,rate,sn,select_vendor,v_code,product_code,pos,minQty,vType,rating,today_price,yesterday_price;

    public Dl2(String v_code,String name, String price, String rate,String sn,String select_vendor,String product_code,String pos,String vType ,String minQty,String rating,String today_price,String yesterday_price) {
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.select_vendor = select_vendor;
        this.sn=sn;
        this.v_code =v_code;
        this.product_code=product_code;
        this.pos=pos;
        this.minQty=minQty;
        this.vType = vType;
        this.rating=rating;
        this.today_price=today_price;
        this.yesterday_price=yesterday_price;
    }

    public String getToday_price() {
        return today_price;
    }

    public void setToday_price(String today_price) {
        this.today_price = today_price;
    }

    public String getYesterday_price() {
        return yesterday_price;
    }

    public void setYesterday_price(String yesterday_price) {
        this.yesterday_price = yesterday_price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getV_code() {
        return v_code;
    }

    public void setV_code(String v_code) {
        this.v_code = v_code;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSelect_vendor() {
        return select_vendor;
    }

    public void setSelect_vendor(String select_vendor) {
        this.select_vendor = select_vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
