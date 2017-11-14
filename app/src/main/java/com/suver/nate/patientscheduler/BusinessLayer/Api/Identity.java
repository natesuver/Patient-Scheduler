package com.suver.nate.patientscheduler.BusinessLayer.Api;

import android.content.Context;
import com.suver.nate.patientscheduler.R;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nates on 11/13/2017.
 */

public class Identity {
    private Context mContext;
    private String mUrl;
    private static final String LOG = "Identity";
    public Identity(Context context) {
        mContext = context;
        mUrl = context.getString(R.string.api_token_url);
    }

    public JSONObject GetAccessToken(String username, String pw, String tenant) {
        String parms = BuildAccessTokenUrlParameters(username,pw, tenant);
        JSONObject result = ExecuteRequest(mUrl,parms);
        return result;
    }

    public JSONObject GetRefreshToken(String refreshToken) {
        String parms = BuildRefreshTokenUrlParameters(refreshToken);
        return ExecuteRequest(mUrl,parms);
    }

    public JSONObject ExecuteRequest(String url, String parameters) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", mContext.getString(R.string.content_type));
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            InputStream stream;
            if (responseCode!=200) {
                stream = connection.getErrorStream();
            }
            else {
                stream = connection.getInputStream();
            }
            return GetJsonObjectFromStream(stream);

        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return getJsonError(mContext.getString(R.string.connection_error));
        }
    }

    @Nullable
    private JSONObject getJsonError(String errorMessage) {
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

    public String BuildAccessTokenUrlParameters(String username, String pw, String tenant) {
        String result = BuildParm(R.string.grant_type_ident,R.string.grant_type,false);
        result += BuildParm(R.string.client_id_ident,R.string.client_id,true);
        result += BuildParm(R.string.client_secret_ident,R.string.client_secret,true);
        result += BuildParm(R.string.scope_ident,R.string.scope,true);
        String acrVals = mContext.getString(R.string.acr_values).replace("{{tenant}}", tenant);
        result += BuildParm(R.string.acr_values_ident,acrVals,true);
        result += BuildParm(R.string.username_ident,username,true);
        result += BuildParm(R.string.password_ident,pw,true);
        return result;
    }
    public String BuildRefreshTokenUrlParameters(String refreshToken) {
        String result = BuildParm(R.string.grant_type_ident,R.string.grant_type,false);
        result += BuildParm(R.string.client_id_ident,R.string.client_id,true);
        result += BuildParm(R.string.client_secret_ident,R.string.client_secret,true);
        result += BuildParm(R.string.scope_ident,R.string.scope,true);
        result += BuildParm(R.string.refresh_token_ident,refreshToken,true);
        return result;
    }

    public String BuildParm(Integer field, Integer value, Boolean useDelimiter) {
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
    public String BuildParm(Integer field, String value, Boolean useDelimiter) {
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
    public static JSONObject GetJsonObjectFromStream(InputStream stream) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(stream));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();
            return new JSONObject(response.toString());
        } catch (Exception e) {
            Log.e(LOG,e.getMessage());
            return null;
        }
    }
}
