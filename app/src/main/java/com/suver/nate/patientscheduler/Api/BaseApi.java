package com.suver.nate.patientscheduler.Api;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Models.Token;
import com.suver.nate.patientscheduler.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nates on 11/18/2017.
 */

public abstract class BaseApi {
    private static final String LOG = "BaseApi";
    private static final String ContentTypeMoniker = "Content-Type";
    private static final String AuthorizationMoniker = "Authorization";
    protected String mBaseUrl;
    protected Context mContext;
    protected String mTenant;
    protected String mContentType;
    protected Token mToken;

    public BaseApi(Context context, String baseUrl, String tenant, String contentType, Token token) {
        mContext = context;
        mBaseUrl = baseUrl;
        mTenant = tenant;
        mContentType = contentType;
        mToken = token;
    }
    protected String ExecuteRequest(String partialUrl, String parameters, Boolean setAccessToken) {
        try {
            URL obj = new URL(mBaseUrl+partialUrl);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty(ContentTypeMoniker, mContentType);
            if (setAccessToken && mToken!=null && mToken.getAccessToken().length()>0) {
                connection.setRequestProperty(AuthorizationMoniker, mToken.getTokenType() + " " + mToken.getAccessToken());
            }
            connection.setDoOutput(true);
            if (parameters!=null && parameters.length()>0) { //only write parms to the outgoing request if they are specified
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
            }

            int responseCode = connection.getResponseCode();
            InputStream stream;
            if (responseCode!=200) { //200 is success, everything else, for this purpose, is not a success.
                stream = connection.getErrorStream();
            }
            else {
                stream = connection.getInputStream();
            }
            return GetResponseStringBuffer(stream).toString();

        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return (ex.getMessage());
        }
    }

    protected String ExecuteRequest(String partialUrl) {
        try {
            URL obj = new URL(mBaseUrl + mTenant + "/" + partialUrl);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(AuthorizationMoniker, mToken.getTokenType() + " " + mToken.getAccessToken());
            int responseCode = connection.getResponseCode();
            InputStream stream;
            switch (responseCode) {
                case 200: //200 is success,
                    stream = connection.getInputStream();
                    break;
                case 401: //Unauthorized.  Assume the access token is bad, and needs to be refreshed
                    mToken = RefreshToken(mToken); //this is untested.
                    if (mToken.getRetries() <4)
                        return ExecuteRequest(partialUrl);
                    else
                        stream = connection.getErrorStream();
                    break;
                default: // everything else, for this purpose, is not a success.
                    stream = connection.getErrorStream();
            }
            return GetResponseStringBuffer(stream).toString();
        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return (ex.getMessage());
        }
    }

    protected abstract Token RefreshToken(Token existingToken);
/*

    protected JSONObject getJsonError(String errorMessage) {
        String err = "{\'error_description\':\'" + errorMessage + "\'}";
        JSONObject obj=null;
        try {
            obj = new JSONObject(err);
        }
        catch (Exception ex1) {
            Log.e(LOG,ex1.getMessage());
        }
        return obj;
    }

    protected JSONObject GetJsonObjectFromStream(InputStream stream) {
        try {
            StringBuffer response = GetResponseStringBuffer(stream);
            return new JSONObject(response.toString());
        } catch (Exception e) {
            Log.e(LOG,e.getMessage());
            return null;
        }
    }
*/
    @NonNull
    protected StringBuffer GetResponseStringBuffer(InputStream stream) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();
        return response;
    }

    protected String BuildParm(Integer field, Integer value, Boolean useDelimiter) {
        try {
            String charset = "UTF-8";
            String result = useDelimiter?"&":"";
            result+= mContext.getString(field) + "=" + URLEncoder.encode(mContext.getString(value), charset);
            return result;
        }
        catch(UnsupportedEncodingException ex) {
            Log.e(LOG,ex.getMessage());
            throw new AssertionError(R.string.no_utf8_found);
        }
    }

    protected String BuildParm(Integer field, String value, Boolean useDelimiter) {
        try {
            String charset = "UTF-8";
            String result = useDelimiter?"&":"";
            result+= mContext.getString(field) + "=" + URLEncoder.encode(value, charset);
            return result;
        }
        catch(UnsupportedEncodingException ex) {
            Log.e(LOG,ex.getMessage());
            throw new AssertionError(R.string.no_utf8_found);
        }
    }

    protected Bitmap ExecuteBitmapRequest(String partialUrl) {
        try {
            URL u = new URL(mBaseUrl + mTenant + "/" + partialUrl);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(AuthorizationMoniker, mToken.getTokenType() + " " + mToken.getAccessToken());
            Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
            return bmp;
        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return null;
        }

    }
}