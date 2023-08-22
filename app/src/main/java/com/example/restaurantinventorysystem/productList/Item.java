package com.example.restaurantinventorysystem.productList;

import java.io.Serializable;

public class Item implements Serializable {

    private String itemName;
    private String itemPrice;
    private String itemType;
    private String supplierName;
    private String supplierContact;
    private int quantity;
    private String expiryDate;

    public Item(){

    }

    public Item(String itemName, String itemPrice, String supplierName,String supplierContact, String itemType){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.itemType=itemType;
        this.supplierName=supplierName;
        this.supplierContact=supplierContact;
        this.quantity=0;
        this.expiryDate="0/0/0000";
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }


}
