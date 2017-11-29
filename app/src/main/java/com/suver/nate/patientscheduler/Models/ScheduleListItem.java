package com.suver.nate.patientscheduler.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nates on 11/22/2017.
 * note: ran through http://www.parcelabler.com/ to implements Parcelable
 */

public class ScheduleListItem implements Parcelable {

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private Status status;
    private String caregiverName;
    private Service service;
    private Integer id;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getPrintedDate() {
        return startDate + " " + startTime + " - " + endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected ScheduleListItem(Parcel in) {
        startDate = in.readString();
        startTime = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        status = (Status) in.readValue(Status.class.getClassLoader());
        caregiverName = in.readString();
        service = (Service) in.readValue(Service.class.getClassLoader());
        id = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeValue(status);
        dest.writeString(caregiverName);
        dest.writeValue(service);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
    }

    public static final Parcelable.Creator<ScheduleListItem> CREATOR = new Parcelable.Creator<ScheduleListItem>() {
        @Override
        public ScheduleListItem createFromParcel(Parcel in) {
            return new ScheduleListItem(in);
        }

        @Override
        public ScheduleListItem[] newArray(int size) {
            return new ScheduleListItem[size];
        }
    };
}
