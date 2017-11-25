package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Models.ScheduleDetail;
import com.suver.nate.patientscheduler.Models.ScheduleListItem;
import com.suver.nate.patientscheduler.Models.Task;
import com.suver.nate.patientscheduler.R;

/**
 * Created by nates on 11/23/2017.
 */

public class ScheduleApi extends RequestApi {
    public ScheduleApi(Context context) {
        super(context);
    }
    public ScheduleListItem[] GetList(Integer clientId) {
        String result = ExecuteRequest("iris/clients/" + clientId + "/schedules");
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, ScheduleListItem[].class);
    }
    public ScheduleDetail Get(Integer scheduleId) {
        String result = ExecuteRequest("my/schedules/" + scheduleId);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, ScheduleDetail.class);
    }

    public Task[] GetTaskList(Integer scheduleId) {
        String result = ExecuteRequest("my/schedules/" + scheduleId + "/poc/tasks");
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, Task[].class);
    }

}
