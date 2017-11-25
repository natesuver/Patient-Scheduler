package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class ServicePlanListItem {
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
}
