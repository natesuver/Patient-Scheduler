package com.suver.nate.patientscheduler.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nates on 11/24/2017.
 * note: ran through http://www.parcelabler.com/ to implements Parcelable
 */

public class ServicePlanListItem implements Parcelable {
    private Integer taskId;
    private String notes;

    public ServicePlanListItem(Integer id, String notes) {
        this.taskId = id;
        this.notes = notes;
    }
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getNotes() {
        if (notes==null || notes.length()==0) return "N/A";
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    protected ServicePlanListItem(Parcel in) {
        taskId = in.readByte() == 0x00 ? null : in.readInt();
        notes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (taskId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(taskId);
        }
        dest.writeString(notes);
    }

    public static final Parcelable.Creator<ServicePlanListItem> CREATOR = new Parcelable.Creator<ServicePlanListItem>() {
        @Override
        public ServicePlanListItem createFromParcel(Parcel in) {
            return new ServicePlanListItem(in);
        }

        @Override
        public ServicePlanListItem[] newArray(int size) {
            return new ServicePlanListItem[size];
        }
    };
}