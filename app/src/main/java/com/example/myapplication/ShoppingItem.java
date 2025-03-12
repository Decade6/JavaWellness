//shopping item class
package com.example.myapplication;

public class ShoppingItem {
    String name;
    String info;
    int quantity;

    public ShoppingItem(String name, String info, int quantity)
    {
        this.name = name;
        this.info = info;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
