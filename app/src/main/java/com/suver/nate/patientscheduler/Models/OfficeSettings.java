package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/22/2017.
 */

public class OfficeSettings extends Address {
    private String name;
    private String companyUri;

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
