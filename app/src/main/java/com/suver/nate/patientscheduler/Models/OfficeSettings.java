package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/22/2017.
 */

public class OfficeSettings {
    private String name;
    private String companyUri;
    private String add1;
    private String add2;
    private String city;
    private String st;
    private String zip;
    private String primPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyUri() {
        return companyUri;
    }

    public void setCompanyUri(String companyUri) {
        this.companyUri = companyUri;
    }

    public String getAddress1() {
        return add1;
    }

    public void setAddress1(String address) {
        this.add1 = address;
    }

    public String getAddress2() {
        return add2;
    }

    public void setAddress2(String address2) {
        this.add2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return st;
    }

    public void setState(String state) {
        this.st = state;
    }

    public String getPostalCode() {
        return zip;
    }

    public void setPostalCode(String postalCode) {
        this.zip = postalCode;
    }

    public String getPrimPhone() {
        return primPhone;
    }

    public void setPrimPhone(String primPhone) {
        this.primPhone = primPhone;
    }

    public String GetFullAddress() {
        return this.name + "\n" + this.add1 + " " + this.add2 + "\n" + this.city + ", " + this.st + "  " + this.zip + "\nPhone: " + this.primPhone;
    }
}
