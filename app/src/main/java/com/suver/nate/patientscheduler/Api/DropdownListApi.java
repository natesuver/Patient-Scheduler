package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.suver.nate.patientscheduler.Helpers.TaskDescriptionLookup;

/**
 * Created by nates on 11/24/2017.
 */

public class DropdownListApi extends RequestApi {
    public DropdownListApi(Context context) {
        super(context);
    }

    public TaskDescriptionLookup GetTaskList(Integer officeId, Integer listType) {
        String result = ExecuteRequest("common/list-items/get-dropdown-list?listType="+ listType + "&branch=" + officeId);
        TaskDescriptionLookup t = new TaskDescriptionLookup(result);
        return t;
    }
}
