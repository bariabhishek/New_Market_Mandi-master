package com.freshbrigade.market;

class FullDetailData {
    private String name,patani,veginame,Quantity,rateSummary,priceAmmount;

    public FullDetailData(String name, String patani, String veginame, String quantity, String rateSummary, String priceAmmount) {
        this.name = name;
        this.patani = patani;
        this.veginame = veginame;
        this.Quantity = quantity;
        this.rateSummary = rateSummary;
        this.priceAmmount = priceAmmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatani() {
        return patani;
    }

    public void setPatani(String patani) {
        this.patani = patani;
    }

    public String getVeginame() {
        return veginame;
    }

    public void setVeginame(String veginame) {
        this.veginame = veginame;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRateSummary() {
        return rateSummary;
    }

    public void setRateSummary(String rateSummary) {
        this.rateSummary = rateSummary;
    }

    public String getPriceAmmount() {
        return priceAmmount;
    }

    public void setPriceAmmount(String priceAmmount) {
        this.priceAmmount = priceAmmount;
    }
}
