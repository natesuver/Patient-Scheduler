package com.suver.nate.patientscheduler.Api;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.ScheduleDetail;

/**
 * Created by nates on 11/24/2017.
 */

public class ImageApi extends RequestApi {
    public ImageApi(Context context) {
        super(context);
    }
    public Bitmap GetCaregiverImage(Integer id) {
        String partial = "my/caregivers/" + id + "/image";
        return ExecuteBitmapRequest(partial);
    }
}
