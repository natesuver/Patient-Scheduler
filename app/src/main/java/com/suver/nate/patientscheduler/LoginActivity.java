package com.suver.nate.patientscheduler;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.suver.nate.patientscheduler.BusinessLayer.Api.Identity;
import com.suver.nate.patientscheduler.BusinessLayer.Validation.LoginValidator;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String LOG = "LoginScreen";
    EditText mUsername;
    EditText mPassword;
    EditText mTenant;
    Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText)findViewById(R.id.usernameText);
        mPassword = (EditText)findViewById(R.id.pwText);
        mTenant = (EditText)findViewById(R.id.tenantText);

        mLogin = (Button) findViewById(R.id.loginButton);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername.setError(null);
                mPassword.setError(null);
                mTenant.setError(null);
                String username = mUsername.getText().toString();
                if (!LoginValidator.UserValid(username)) {
                    mUsername.setError(getText(R.string.invalid_username));
                }
                String pw = mPassword.getText().toString();
                if (!LoginValidator.PasswordValid(pw)) {
                    mPassword.setError(getText(R.string.invalid_password));
                }
                String tenant = mTenant.getText().toString();
                if (!LoginValidator.TenantValid(tenant)) {
                    mTenant.setError(getText(R.string.invalid_tenant));
                }
                new GetToken(LoginActivity.this).execute(username,pw,tenant);
            }
        });
    }

    private class GetToken extends AsyncTask<String, Void, JSONObject> {

        private Context mContext;
        private JSONObject mToken;
        public GetToken (Context context){
            mContext = context;
        }
        @Override
        protected JSONObject doInBackground(String... params){
            Identity id = new Identity(mContext);
            mToken = id.GetAccessToken(params[0],params[1],params[2]);
            return mToken;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            mLogin.setError(null);
            if (result.has("error_description")) {
                try {
                    String error = result.getString("error_description");
                    if (error.contains("Tenant")) {
                        mTenant.requestFocus();
                        mTenant.setError(error);
                    }
                    else {
                        mPassword.requestFocus();
                        mPassword.setError(error);
                    }

                } catch(Exception ex) {
                    Log.d(LOG,ex.getMessage());
                }
            }
            //ErrorMessage
            //access_token
            Log.d(LOG,result.toString());
        }
    }

}

