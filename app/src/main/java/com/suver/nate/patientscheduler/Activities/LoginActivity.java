package com.suver.nate.patientscheduler.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.suver.nate.patientscheduler.Api.DropdownListApi;
import com.suver.nate.patientscheduler.Api.Identity;
import com.suver.nate.patientscheduler.Api.OfficeSettingsApi;
import com.suver.nate.patientscheduler.Api.ScheduleApi;
import com.suver.nate.patientscheduler.Api.UserSettingsApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Helpers.TaskDescriptionLookup;
import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.Token;
import com.suver.nate.patientscheduler.Models.UserSetting;
import com.suver.nate.patientscheduler.R;
import com.suver.nate.patientscheduler.Validation.LoginValidator;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String LOG = "LoginScreen";
    private static final Integer RequestCode = 0;
    private static final Integer TaskListIdentifierCode = 27;
    EditText mUsername;
    EditText mPassword;
    EditText mTenant;
    ProgressBar mProgress;
    Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.usernameText);
        mPassword = findViewById(R.id.pwText);
        mTenant = findViewById(R.id.tenantText);
        mLogin = findViewById(R.id.loginButton);
        mProgress = findViewById(R.id.progress_loader);
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
                ApplicationData.tenant = tenant;
                mProgress.setVisibility(View.VISIBLE);
                mLogin.setEnabled(false);
                new GetToken(LoginActivity.this).execute(username,pw,tenant);
            }
        });


    }

    private class GetToken extends AsyncTask<String, Void, Token> {

        private Context mContext;
        GetToken(Context context){
            mContext = context;
        }
        @Override
        protected Token doInBackground(String... params){
            Identity id = new Identity(mContext, params[2]);
            return id.GetAccessToken(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(Token result) {
            mLogin.setError(null);
            ApplicationData.token = result;
            if (result.getErrorDescription().length()>0) {
                String error = result.getErrorDescription();
                if (error.contains("Tenant")) {
                    mTenant.requestFocus();
                    mTenant.setError(error);
                }
                else {
                    mPassword.requestFocus();
                    mPassword.setError(error);
                }
                mProgress.setVisibility(View.INVISIBLE);
                mLogin.setEnabled(true);
            }
            else {
                new GetUserSetting(LoginActivity.this).execute();
            }
            Log.d(LOG,result.toString());
        }
    }

    private class GetUserSetting extends AsyncTask<String, Void, UserSetting> {
        private Context mContext;
        GetUserSetting(Context context){
            mContext = context;
        }
        @Override
        protected UserSetting doInBackground(String... params){
            UserSettingsApi api = new UserSettingsApi(mContext);
            UserSetting u = api.Get();
            return u;
        }
        @Override
        protected void onPostExecute(UserSetting result) {
            ApplicationData.userSetting = result;
            new GetOfficeSettings(LoginActivity.this).execute(result.getBranchID());
            new TaskList(LoginActivity.this).execute(result.getBranchID(),TaskListIdentifierCode );
        }
    }

    private class GetOfficeSettings extends AsyncTask<Integer, Void, OfficeSettings> {
        private Context mContext;
        GetOfficeSettings(Context context){
            mContext = context;
        }
        @Override
        protected OfficeSettings doInBackground(Integer... params){
            OfficeSettingsApi api = new OfficeSettingsApi(mContext);
            OfficeSettings retval = api.Get(params[0]);
            return retval;
        }
        @Override
        protected void onPostExecute(OfficeSettings result) {
            ApplicationData.officeSettings = result;
            mProgress.setVisibility(View.INVISIBLE);
            Intent intent = MainActivity.newIntent(LoginActivity.this,result.toString());
            startActivityForResult(intent, RequestCode);
        }
    }

    private class TaskList extends AsyncTask<Integer, Void, TaskDescriptionLookup> {
        private Context mContext;
        TaskList(Context context){
            mContext = context;
        }
        @Override
        protected TaskDescriptionLookup doInBackground(Integer... params){
            DropdownListApi api = new DropdownListApi(mContext);
            return api.GetTaskList(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(TaskDescriptionLookup result) {
            ApplicationData.tasks = result;
        }
    }

}

