package com.suver.nate.patientscheduler.Helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by nates on 11/24/2017.
 */

public class TaskDescriptionLookup {
    private static final String LOG = "TaskDescriptionLookup";
    private HashMap<Integer, String> map;
    //The structure that comes back from the API that contains task descriptions is fairly complicated, certainly for this use case.  This is an attempt to simplify it to something the client can easily consume
    public TaskDescriptionLookup(String json) {
        try {
            map = new HashMap<Integer, String>();
            JSONArray array = new JSONArray(json);
            for (Integer i = 0; i< array.length();i++) {
                JSONArray innerArray = array.getJSONObject(i).getJSONArray("hhaTasks");
                for (Integer j = 0; j< innerArray.length();j++) {
                    JSONObject li = innerArray.getJSONObject(j);
                    if (!map.containsKey(li.getInt("id"))) {
                        map.put(li.getInt("id"), li.getString("name"));
                    }
                }
            }
        }
        catch (Exception ex) {
            map = null;
            Log.e(LOG,ex.getMessage());
        }
    }
    public String GetTaskDescription(Integer id) {
        if (map.containsKey(id)) {
            return map.get(id);
        }
        else return "";
    }
}
