package com.freshbrigade.market;

public class priceDataClass {

    String productCode;
    String price;





    public priceDataClass(String productCode,String price) {
        this.productCode = productCode;
        this.price = price;

    }
//hvjhs
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
