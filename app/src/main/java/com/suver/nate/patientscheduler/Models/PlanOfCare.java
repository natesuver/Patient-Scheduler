package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class PlanOfCare {
    private String pocCStr;
    private String pocCEnd;

    public String getCertStart() {
        return pocCStr;
    }

    public void setCertStart(String pocCStr) {
        this.pocCStr = pocCStr;
    }

    public String getCertEnd() {
        return pocCEnd;
    }

    public void setCertEnd(String pocCEnd) {
        this.pocCEnd = pocCEnd;
    }

    public String getPrintedCert() {
        return pocCStr + " - " + pocCEnd;
    }
}
