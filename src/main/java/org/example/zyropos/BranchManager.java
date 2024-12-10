package org.example.zyropos;

public class BranchManager {
    private int managerID;
    private String name;
    private int branchID;
    private String phone;
    private String address;
    private String email;


    public BranchManager(int managerID, String name, int branchID, String phone, String address, String email) {
        this.managerID = managerID;
        this.name = name;
        this.branchID = branchID;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
