package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.Schedule;
import com.suver.nate.patientscheduler.R;

import java.util.List;

/**
 * Created by nates on 11/23/2017.
 */

public class ScheduleApi extends BaseApi {
    public ScheduleApi(Context context) {
        super(context,context.getString(R.string.base_api_url), ApplicationData.tenant, context.getString(R.string.content_type_api), ApplicationData.authToken);
    }
    public Schedule[] GetList(Integer clientId) {
        String result = ExecuteRequest("iris/clients/" + clientId + "/schedules");
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, Schedule[].class);
    }
}
