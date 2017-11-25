package com.suver.nate.patientscheduler.Api;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.InvoiceListItem;
import com.suver.nate.patientscheduler.Models.ScheduleListItem;

import java.io.File;

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

    public File GetInvoice(Integer invoiceId) {
        String outputFile = "invoice_dl.pdf";

        File file = new File(mContext.getCacheDir()+"/docs/", outputFile);
        file.mkdirs();
        ExecuteFileRequest("iris/invoices/" + invoiceId + "/statementPDF", file);
        Log.d(LOG,"File Downloaded Successfully!");
        return file;
    }

}
