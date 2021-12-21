package com.example.miapackages;

public class Item {
    private int amountS = 0;
    private int priceS = 0;
    private String nameS = "abc";
    private String supplierS = "abc";
    private int totAmountS = 0;


    public Item(String name, String supp, int price, int amount) {
        nameS = name;
        supplierS = supp;
        priceS = price;
        amountS = amount;
    }
    public Item(String name, String supp, int price, int amount, int totAmount) {
        nameS = name;
        supplierS = supp;
        priceS = price;
        amountS = amount;
        totAmountS = totAmount;
    }

    public String getName() {
        return nameS;
    }
    public String getSupplier() {
        return supplierS;
    }
    public int getPrice() {
        return priceS;
    }
    public int getAmount() {
        return amountS;
    }
    public int getTotAmount() {
        return totAmountS;
    }
    public void setAmount(int i) {
        amountS = i;
    }

}