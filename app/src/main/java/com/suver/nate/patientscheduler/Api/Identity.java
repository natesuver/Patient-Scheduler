package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Models.ScheduleDetail;
import com.suver.nate.patientscheduler.Models.Token;
import com.suver.nate.patientscheduler.R;
import org.json.JSONObject;

/**
 * Created by nates on 11/13/2017.
 */

public class Identity extends BaseApi {

    private String mUrl;
    private static final String LOG = "Identity";
   public Identity(Context context, String tenant) {
       super(context,context.getString(R.string.api_token_url), tenant, context.getString(R.string.content_type_ids),null);
   }
    public Token GetAccessToken(String username, String pw) {
        String parms = BuildAccessTokenUrlParameters(username,pw);
        String result= ExecuteRequest("",parms, false);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, Token.class);
    }

    protected Token GetRefreshToken(Token existingToken) {
        String parms = BuildRefreshTokenUrlParameters(existingToken.getRefreshToken()); //returns invalid_grant, investigate.
        String result= ExecuteRequest("",parms, false);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, Token.class);
    }


    protected void RefreshToken(Token existingToken) {
        throw new UnsupportedOperationException("Not Supported");
    }
    public String BuildAccessTokenUrlParameters(String username, String pw) {
        String result = BuildParm(R.string.grant_type_ident,R.string.grant_type,false);
        result += BuildParm(R.string.client_id_ident,R.string.client_id,true);
        result += BuildParm(R.string.client_secret_ident,R.string.client_secret,true);
        result += BuildParm(R.string.scope_ident,R.string.scope,true);
        String acrVals = mContext.getString(R.string.acr_values).replace("{{tenant}}", mTenant);
        result += BuildParm(R.string.acr_values_ident,acrVals,true);
        result += BuildParm(R.string.username_ident,username,true);
        result += BuildParm(R.string.password_ident,pw,true);
        return result;
    }
    public String BuildRefreshTokenUrlParameters(String refreshToken) {
        String result = BuildParm(R.string.grant_type_ident,R.string.grant_type_refresh,false);
        result += BuildParm(R.string.client_id_ident,R.string.client_id,true);
        result += BuildParm(R.string.client_secret_ident,R.string.client_secret,true);
        result += BuildParm(R.string.scope_ident,R.string.scope,true);
        result += BuildParm(R.string.refresh_token_ident,refreshToken,true);
        return result;
    }
}
