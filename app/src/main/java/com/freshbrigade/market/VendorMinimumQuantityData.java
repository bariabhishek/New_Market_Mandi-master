package com.freshbrigade.market;

class VendorMinimumQuantityData {
    String image;
    String vegname;
    String editText;
    String pcode;
    String minQty;


    public VendorMinimumQuantityData(String image, String vegname, String editText,String pcode,String minQty) {
        this.image = image;
        this.vegname = vegname;
        this.editText = editText;
        this.pcode = pcode;
        this.minQty = minQty;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVegname() {
        return vegname;
    }

    public void setVegname(String vegname) {
        this.vegname = vegname;
    }

    public String getEditText() {
        return editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }
}
