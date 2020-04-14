package com.freshbrigade.market;

class FullDetailForVendorData {

    private String name,patani,veginame,Quantity,rateSummary,priceAmount,editQty,pCode,status;
    Double totalOfRate;

    public String getEditQty() {
        return editQty;
    }

    public void setEditQty(String editQty) {
        this.editQty = editQty;
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

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Double getTotalOfRate() {
        return totalOfRate;
    }

    public void setTotalOfRate(Double totalOfRate) {
        this.totalOfRate = totalOfRate;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FullDetailForVendorData(String name, String patani, String veginame, String quantity, String rateSummary, String priceAmount, Double totalOfRate, String editQty, String pCode , String status) {
        this.name = name;
        this.patani = patani;
        this.veginame = veginame;
        Quantity = quantity;
        this.rateSummary = rateSummary;
        this.priceAmount = priceAmount;
        this.totalOfRate = totalOfRate;
        this.editQty =editQty;
        this.pCode = pCode;
        this.status = status;

}
}
