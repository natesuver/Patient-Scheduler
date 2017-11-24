package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/23/2017.
 */

public class Status {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullDescription() {
        switch (name) {
            case "C": return "Completed";
            case "S": return "Scheduled";
            default: return name;
        }
    }
}
