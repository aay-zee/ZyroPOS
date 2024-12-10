package org.example.zyropos;

public class Branch {
    private int branchID;
    private String branchName;
    private String city;
    private String address;
    private String phone;
    private int empCount;
    private boolean isActive;

    public Branch(int branchID, String branchName, String city, String address, String phone, int empCount, boolean isActive){
        this.branchID = branchID;
        this.branchName = branchName;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.empCount = empCount;
        this.isActive = isActive;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getEmpCount() {
        return empCount;
    }

    public void setEmpCount(int empCount) {
        this.empCount = empCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchID=" + branchID +
                ", branchName='" + branchName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", empCount=" + empCount +
                ", isActive=" + isActive +
                '}';
    }
}
