package models;

public class OrderModel2 {

    String buyername;
    String orderid;
    String ordername;
    String address;
    String img;
    int count;
    int total;
    String size;
    String username;
    String type;

    public OrderModel2(String buyername, String orderid, String ordername, String address, String img, int count, int total, String size, String username, String type) {
        this.buyername = buyername;
        this.orderid = orderid;
        this.ordername = ordername;
        this.address = address;
        this.img = img;
        this.count = count;
        this.total = total;
        this.size = size;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
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
}
