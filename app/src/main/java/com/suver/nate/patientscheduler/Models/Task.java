package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class Task {
    private String planNote;
    private String schtNote;
    private Integer taskId;
    private Boolean sel;

    public String getPlanNotes() {
        if (planNote==null || planNote.length()==0) return "N/A";
        return planNote;
    }

    public void setPlanNotes(String planNote) {
        this.planNote = planNote;
    }

    public String getVisitNote() {
        if (schtNote==null || schtNote.length()==0) return "N/A";
        return schtNote;
    }

    public void setVisitNote(String schtNote) {
        this.schtNote = schtNote;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Boolean getPerformed() {
        return sel;
    }

    public void setPerformed(Boolean sel) {
        this.sel = sel;
    }
}
