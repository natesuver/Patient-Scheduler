package com.suver.nate.patientscheduler.Api;

import android.content.Context;
import android.graphics.Bitmap;

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
