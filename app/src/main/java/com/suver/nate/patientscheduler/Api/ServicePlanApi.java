package com.suver.nate.patientscheduler.Api;
import android.content.Context;
import android.util.Log;

import com.suver.nate.patientscheduler.Models.ServicePlanListItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by nates on 11/24/2017.
 */

public class ServicePlanApi extends RequestApi {
    private static final String LOG = "ServicePlanApi";
    public ServicePlanApi(Context context) {
        super(context);
    }

    public ServicePlanListItem[] GetList(Integer clientId) {
        String result = ExecuteRequest("my/clients/" + clientId + "/service-plan");
        ArrayList<ServicePlanListItem> splans= new ArrayList<ServicePlanListItem>();
        try {
            JSONArray arr = new JSONObject(result).getJSONArray("items");

            for (Integer i = 0; i< arr.length();i++) {
                JSONObject o = arr.getJSONObject(i);
                splans.add(new ServicePlanListItem(o.getInt("taskId"), o.getString("notes")));
            }

        }
        catch (Exception ex) {
            Log.d(LOG,result.toString());
        }
        return splans.toArray (new ServicePlanListItem[0]);//ServicePlanListItem[])splans.toArray();
    }
}
