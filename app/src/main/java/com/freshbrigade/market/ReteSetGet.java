package com.freshbrigade.market;

class ReteSetGet {

    String rate,yesrate,pname;
    String image , date;

    public ReteSetGet(String rate, String yesrate,String pname,String image,String date) {
        this.rate = rate;
        this.yesrate = yesrate;
        this.pname = pname;
        this.image = image;
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getYesrate() {
        return yesrate;
    }

    public void setYesrate(String yesrate) {
        this.yesrate = yesrate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
