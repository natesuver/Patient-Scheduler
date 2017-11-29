package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.OfficeSettings;

/**
 * Created by nates on 11/22/2017.
 */

public class OfficeSettingsApi extends RequestApi {


    public OfficeSettingsApi(Context context) {
        super(context);
    }

    public OfficeSettings Get(Integer officeId) {
        String result = ExecuteRequest("common/list-items/get-branch-list-item/"+ officeId);
        Gson gson = new GsonBuilder().create();
        OfficeSettings retval = gson.fromJson(result, OfficeSettings.class);
        return retval;
    }
}
