package com.example.miapackages;

public class Order {
    private String idS = "";
    private String amountS = "";
    private String itemsS = "";
    private String phoneS = "";
    private String addressS = "";
    //private int totAmountS = 0;
    public Order(String id,String phone, String address, String items, String amounts) {
        idS = id;
        phoneS = phone;
        addressS = address;
        itemsS = items;
        amountS = amounts;
    }
    public String getIdS(){return idS;}
    public String getPhone() {
        return phoneS;
    }
    public String getAddressS() {
        return addressS;
    }
    public String getItemsS() {
        return itemsS;
    }
    public String getAmounts() {
        return amountS;
    }

}
