package com.suver.nate.patientscheduler.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suver.nate.patientscheduler.Api.ImageApi;
import com.suver.nate.patientscheduler.Api.OfficeSettingsApi;
import com.suver.nate.patientscheduler.Api.ScheduleApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Fragments.SchedulesFragment;
import com.suver.nate.patientscheduler.Helpers.ScheduleSort;
import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.ScheduleDetail;
import com.suver.nate.patientscheduler.Models.ScheduleListItem;
import com.suver.nate.patientscheduler.Models.Task;
import com.suver.nate.patientscheduler.R;

import org.json.JSONObject;

import java.util.Arrays;

public class ScheduleDetailActivity extends AppCompatActivity {
    private static final String ScheduleLI = "ScheduleListItem";
    private ScheduleListItem mScheduleListItem;
    private ScheduleDetail mScheduleDetail;
    private Task[] mTaskData;

    //cgName
    //appt
    //cert

    private ImageView mImg;
    private TextView mName;
    private TextView mAppt;
    private TextView mCert;
    private FrameLayout mTasks;
    private RecyclerView mTaskList;
    private ScheduleDetailActivity.TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        mScheduleListItem=(ScheduleListItem)getIntent().getParcelableExtra(ScheduleLI);
        mImg = findViewById(R.id.cgImage);
        mName = findViewById(R.id.cgName);
        mAppt = findViewById(R.id.appt);
        mCert = findViewById(R.id.cert);
        mTaskList = findViewById(R.id.taskList);

        mTaskList.setLayoutManager(new LinearLayoutManager(ScheduleDetailActivity.this));
        new GetScheduleDetail(ScheduleDetailActivity.this).execute(mScheduleListItem.getId());
        new GetTasks(ScheduleDetailActivity.this).execute(mScheduleListItem.getId());

    }

    private void updateUi() {
        mName.setText(mScheduleListItem.getCaregiverName());
        mAppt.setText(mScheduleListItem.getPrintedDate());
        mCert.setText(mScheduleDetail.getPlanOfCare().getPrintedCert());
    }
    public static Intent newIntent(Context packageContext, ScheduleListItem sched) {
        Intent intent = new Intent(packageContext,ScheduleDetailActivity.class);
        intent.putExtra(ScheduleLI, sched);
        return intent;
    }

    private class GetScheduleDetail extends AsyncTask<Integer, Void, ScheduleDetail> {
        private Context mContext;
        GetScheduleDetail(Context context){
            mContext = context;
        }
        @Override
        protected ScheduleDetail doInBackground(Integer... params){
            ScheduleApi api = new ScheduleApi(mContext);
            return api.Get(params[0]);
        }
        @Override
        protected void onPostExecute(ScheduleDetail result) {
            mScheduleDetail = result;
            new GetCaregiverPicture(mContext).execute(mScheduleDetail.getCaregiverId());
            updateUi();
        }
    }

    private class GetTasks extends AsyncTask<Integer, Void, Task[]> {
        private Context mContext;
        GetTasks(Context context){
            mContext = context;
        }
        @Override
        protected Task[] doInBackground(Integer... params){
            ScheduleApi api = new ScheduleApi(mContext);
            return api.GetTaskList(params[0]);
        }
        @Override
        protected void onPostExecute(Task[] result) {
            mTaskData = result;
            mTaskAdapter = new ScheduleDetailActivity.TaskAdapter(mTaskData);
            mTaskList.setAdapter(mTaskAdapter);
        }
    }

    private class GetCaregiverPicture extends AsyncTask<Integer, Void, Bitmap> {
        private Context mContext;
        GetCaregiverPicture(Context context){
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(Integer... params){
            ImageApi api = new ImageApi(mContext);
            return api.GetCaregiverImage(params[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            mImg.setImageBitmap(result);

        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mPlanNote;
        private TextView mVisitNote;
        private TextView mTaskName;
        private CheckBox mPerformed;

        private Task mTask;
        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false  ));
            itemView.setOnClickListener(this);
            mPlanNote = itemView.findViewById(R.id.planNotes);
            mVisitNote = itemView.findViewById(R.id.visitNotes);
            mTaskName = itemView.findViewById(R.id.taskName);
            mPerformed = itemView.findViewById(R.id.performed);
        }
        public void bind(Task task) {
            mTask = task;
            mPlanNote.setText(task.getPlanNotes());
            mVisitNote.setText(task.getVisitNote());
            mTaskName.setText(ApplicationData.tasks.GetTaskDescription(task.getTaskId()));
            mPerformed.setChecked(task.getPerformed());
        }

        @Override
        public void onClick(View view) {
            //do nothing.. for now.
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<ScheduleDetailActivity.TaskHolder> {
        private ScheduleListItem[] mSchedules;
        public TaskAdapter(Task[] tasks) {
            mTaskData = tasks;
        }

        @Override
        public ScheduleDetailActivity.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ScheduleDetailActivity.this);
            return new ScheduleDetailActivity.TaskHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ScheduleDetailActivity.TaskHolder holder, int position) {
            Task task = mTaskData[position];
            holder.bind(task);
        }
        @Override
        public int getItemCount() {
            return mTaskData.length;
        }
    }


}
