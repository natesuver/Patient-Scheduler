package com.suver.nate.patientscheduler.Api;

import android.content.Context;

import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Models.Token;
import com.suver.nate.patientscheduler.R;

/**
 * Created by nates on 11/24/2017.
 */

public abstract class RequestApi extends BaseApi {
    public RequestApi(Context context) {
        super(context,context.getString(R.string.base_api_url), ApplicationData.tenant, context.getString(R.string.content_type_api), ApplicationData.token);
    }

    protected Token RefreshToken(Token existingToken) {
        Identity i = new Identity(mContext,"");
        Token refresh = i.RefreshToken(existingToken);
        refresh.incrementRetries();
        ApplicationData.token =refresh;
        return refresh;
    }
}
