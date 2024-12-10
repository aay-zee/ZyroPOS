package org.example.zyropos;

public class Cashier {
    private int id;
    private String name;
    private int branchID;
    private String contact;
    private String address;
    private String email;
    private String salary;


    public Cashier(int id, String name, int branchID, String contact, String address, String email, String salary) {
        this.id = id;
        this.name = name;
        this.branchID = branchID;
        this.contact = contact;
        this.address = address;
        this.email = email;
        this.salary = salary;
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

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
