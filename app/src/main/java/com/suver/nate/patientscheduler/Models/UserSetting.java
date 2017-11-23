package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/22/2017.
 */

public class UserSetting {
    private String userName;
    private Integer branchID;
    private Integer mapToEntityID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public Integer getMapToEntityID() {
        return mapToEntityID;
    }

    public void setMapToEntityID(Integer mapToEntityID) {
        this.mapToEntityID = mapToEntityID;
    }
}
