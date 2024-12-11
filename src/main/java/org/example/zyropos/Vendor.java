package org.example.zyropos;

public class Vendor {
    private int id;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String companyName;
    private String regDate;


    public Vendor(int id, String name, String contactPerson, String phone, String email, String companyName, String regDate) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.companyName = companyName;
        this.regDate = regDate;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
