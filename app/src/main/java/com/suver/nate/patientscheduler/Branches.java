package com.suver.nate.patientscheduler;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nates on 11/11/2017.
 */

public class Branches extends AsyncTask<String, Void, String> {
    private static final String URL = "https://test-api.soneto.net/2.51.0.0/api/SonetoQA/";
    @Override
    protected String doInBackground(String... params){

        try{
            String result = "";
            String url = URL+"common/list-items/get-list-item-types";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + params[0]);
            int responseCode = con.getResponseCode();

            if (responseCode < 400) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();

            }
            else{
                result = con.getResponseMessage();
            }
            //print result
            //System.out.println(response.toString());

        }
        catch (Exception e){
            String ex = e.getMessage();
        }
        return "";




    }
}
