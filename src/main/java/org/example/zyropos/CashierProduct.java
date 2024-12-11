package org.example.zyropos;

public class CashierProduct {
    private int id;
    private String name;
    private String category;
    private double salePrice;
    private double unitPrice;
    private double cartonPrice;
    private int quantity;

    public CashierProduct(int id, String name, String category, double salePrice, double unitPrice, double cartonPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.salePrice = salePrice;
        this.unitPrice = unitPrice;
        this.cartonPrice = cartonPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(double cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
