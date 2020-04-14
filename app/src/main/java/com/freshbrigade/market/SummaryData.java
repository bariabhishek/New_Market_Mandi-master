package com.freshbrigade.market;

class SummaryData {

    private String name;
    private String quatity;
    private String price,rate;

    public SummaryData(String name, String quatity, String rate, String price) {
        this.name = name;
        this.quatity = quatity;
        this.price = price;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
