package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.UserSetting;
import com.suver.nate.patientscheduler.R;

import org.json.JSONObject;

/**
 * Created by nates on 11/22/2017.
 */

public class OfficeSettingsApi extends BaseApi {
    public OfficeSettingsApi(Context context) {
        super(context,context.getString(R.string.base_api_url), ApplicationData.tenant, context.getString(R.string.content_type_api), ApplicationData.authToken);
    }

    public OfficeSettings Get(Integer officeId) {
        String result = ExecuteRequest("common/list-items/get-branch-list-item/"+ officeId);
        Gson gson = new GsonBuilder().create();
        OfficeSettings retval = gson.fromJson(result, OfficeSettings.class);
        return retval;
    }
}
