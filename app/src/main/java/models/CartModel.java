package models;

public class CartModel {
    String imageurl;
    String itemname;
    String price;
    String billname;
    String address;
    int count;
    int total;
    String size;
    String type;

    public CartModel(String imageurl, String itemname, String price, String billname, String address, int count, int total, String size, String type) {
        this.imageurl = imageurl;
        this.itemname = itemname;
        this.price = price;
        this.billname = billname;
        this.address = address;
        this.count = count;
        this.total = total;
        this.size = size;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
