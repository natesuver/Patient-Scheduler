package com.suver.nate.patientscheduler.Api;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.InvoiceListItem;

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
        String outputFile = "invoice.pdf";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), outputFile);
        file.mkdirs();
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (Exception ex) {
            Log.d(LOG,"File could not be created: " + ex.getMessage()); //TODO: this will fail if end user did not provide permission, handle appropriately.
        }

        Log.d(LOG,"File Path: " + file.getAbsolutePath());
        ExecuteFileRequest("iris/invoices/" + invoiceId + "/statementPDF", file);
        Log.d(LOG,"File Downloaded Successfully!");
        Log.d(LOG,"File exists?: " + file.exists());
        Log.d(LOG,"File Length: " + file.length());
        return file;
    }

}
