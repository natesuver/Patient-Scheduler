package com.suver.nate.patientscheduler.Models;

import java.util.Date;

/**
 * Created by nates on 11/22/2017.
 */

public class Schedule {
    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCaregiver() {
        return Caregiver;
    }

    public void setCaregiver(String caregiver) {
        Caregiver = caregiver;
    }

    public String getAgency() {
        return Agency;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    private Date StartDate;
    private Date EndDate;
    private String Service;
    private String Status;
    private String Caregiver;
    private String Agency;


}
