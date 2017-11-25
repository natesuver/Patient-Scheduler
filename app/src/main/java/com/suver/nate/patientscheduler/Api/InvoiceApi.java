package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.InvoiceListItem;
import com.suver.nate.patientscheduler.Models.ScheduleListItem;

/**
 * Created by nates on 11/24/2017.
 */

public class InvoiceApi extends RequestApi {

    private static final String LOG = "InvoiceApi";

    public InvoiceApi(Context context) {
        super(context);
    }

    public InvoiceListItem[] GetList(Integer clientId) {
        String result = ExecuteRequest("iris/clients/" + clientId + "/invoices");
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, InvoiceListItem[].class);
    }
}
