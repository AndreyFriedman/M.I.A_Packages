package com.example.miapackages;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int amountS = 0;
    private int priceS = 0;
    private String nameS = "abc";
    private String descriptionS = "abc";
    private int totAmountS = 0;


    public Item(String name, String desc, int price, int amount) {
        nameS = name;
        descriptionS = desc;
        priceS = price;
        amountS = amount;
    }
    public Item(String name, String desc, int price, int amount, int totAmount) {
        nameS = name;
        descriptionS = desc;
        priceS = price;
        amountS = amount;
        totAmountS = totAmount;
    }

    protected Item(Parcel in) {
        amountS = in.readInt();
        priceS = in.readInt();
        nameS = in.readString();
        descriptionS = in.readString();
        totAmountS = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return nameS;
    }
    public String getDescription() {
        return descriptionS;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amountS);
        dest.writeInt(priceS);
        dest.writeString(nameS);
        dest.writeString(descriptionS);
        dest.writeInt(totAmountS);
    }
}