package com.suver.nate.patientscheduler;

import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.UserSetting;

/**
 * Created by nates on 11/22/2017.
 */

public class ApplicationData {
    private static ApplicationData instance = null;
    protected ApplicationData() {
    }
    public static ApplicationData getInstance() {
        if(instance == null) {
            instance = new ApplicationData();
        }
        return instance;
    }

    public static String tenant;
    public static UserSetting userSetting;
    public static OfficeSettings officeSettings;
    public static String authToken;

}
