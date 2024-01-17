

package com.jasmineenriquez.casestudy;

import java.text.DecimalFormat;

public class BookItem {
    private int id;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private String bookType;
    private double total;
 

    public BookItem(int id, String itemName, String bookType, int quantity, double unitPrice, double total) {
        this.id = id;
        this.itemName = itemName;
        this.bookType = bookType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public String getUnitPrice1() {
    DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // Format to two decimal places
    return decimalFormat.format(unitPrice);
}

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public double getTotal() {
        return total;
    }
    public String getTotal1() {
    DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // Format to two decimal places
    return decimalFormat.format(total);
}

    public void setTotal(double total) {
        this.total = total;
    }
    public String formatTotal(double total) {
   DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // Format to two decimal places
   return decimalFormat.format(total);
    
}
}