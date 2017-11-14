package com.suver.nate.patientscheduler.BusinessLayer.Validation;

/**
 * Created by nates on 11/13/2017.
 */

public class LoginValidator {
    public static Boolean UserValid(String username) {
        return !isValueEmpty(username);
    }
    public static Boolean PasswordValid(String pw) {
        return !isValueEmpty(pw);
    }

    public static Boolean TenantValid(String tenant) {
        return !isValueEmpty(tenant);
    }

    public static Boolean isValueEmpty(String value) {
        if (value==null) return true;
        if (value.length()==0) return true;
        return false;
    }
}
