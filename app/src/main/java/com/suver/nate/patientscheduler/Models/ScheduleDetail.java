package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class ScheduleDetail {
    private String pPayrNm;
    private Address client;
    private PlanOfCare poc;
    private Integer carId;
    public String getPayerName() {
        return pPayrNm;
    }

    public void setPayerName(String pPayrNm) {
        this.pPayrNm = pPayrNm;
    }

    public Address getClient() {
        return client;
    }

    public void setClient(Address client) {
        this.client = client;
    }

    public PlanOfCare getPlanOfCare() {
        return poc;
    }

    public void setPlanOfCare(PlanOfCare poc) {
        this.poc = poc;
    }

    public Integer getCaregiverId() {
        return carId;
    }

    public void setCaregiverId(Integer carId) {
        this.carId = carId;
    }
}
