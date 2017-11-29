package com.suver.nate.patientscheduler.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.suver.nate.patientscheduler.Activities.ScheduleDetailActivity;
import com.suver.nate.patientscheduler.Api.ScheduleApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Helpers.ScheduleSort;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.Models.ScheduleListItem;
import com.suver.nate.patientscheduler.R;

import java.util.Arrays;


public class SchedulesFragment extends Fragment {
    private static final String list_key = "ScheduleListData";
    private static final Integer RequestCode = 0;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mScheduleList;
    ProgressBar mProgress;
    private ScheduleListItem[] mLastResult;
    private ScheduleAdapter mAdapter;
    public SchedulesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            retrieveData(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_schedules, container, false);
        mScheduleList = v.findViewById(R.id.scheduleList);
        mProgress = v.findViewById(R.id.progress_loader);
        mScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        mProgress.setVisibility(View.VISIBLE);
        new ScheduleList(getActivity().getBaseContext()).execute(ApplicationData.userSetting.getMapToEntityID());
        return v;
    }

    private void updateUI() {
        mProgress.setVisibility(View.INVISIBLE);
        if (mLastResult!=null) {
            mAdapter = new ScheduleAdapter(mLastResult);
            mScheduleList.setAdapter(mAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(list_key,mLastResult);
    }

    private void retrieveData(Bundle savedInstanceState) {
        mLastResult = (ScheduleListItem[]) savedInstanceState.getSerializable(list_key);
    }

    public static SchedulesFragment newInstance() {
        SchedulesFragment fragment = new SchedulesFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mService;
        private TextView mDate;
        private TextView mCaregiver;
        private TextView mStatus;

        private ScheduleListItem mScheduleData;
        public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_schedule, parent, false  ));
            itemView.setOnClickListener(this);
            mService = itemView.findViewById(R.id.schedule_service);
            mDate = itemView.findViewById(R.id.schedule_date);
            mCaregiver = itemView.findViewById(R.id.schedule_caregiver);
            mStatus = itemView.findViewById(R.id.schedule_status);
        }
        public void bind(ScheduleListItem sched) {
            mScheduleData = sched;
            mService.setText(mScheduleData.getService().getName());
            mStatus.setText(mScheduleData.getStatus().getFullDescription());
            mCaregiver.setText(mScheduleData.getCaregiverName());
            mDate.setText(mScheduleData.getPrintedDate());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ScheduleDetailActivity.newIntent(getActivity(),mScheduleData); //gson.toJson(mScheduleData)
            startActivity(intent);
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleHolder> {
        private ScheduleListItem[] mSchedules;
        public ScheduleAdapter(ScheduleListItem[] schedules) {
            mSchedules = schedules;
        }

        @Override
        public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScheduleHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ScheduleHolder holder, int position) {
            ScheduleListItem sched = mSchedules[position];
            holder.bind(sched);
        }
        @Override
        public int getItemCount() {
            return mSchedules.length;
        }
    }

    private class ScheduleList extends AsyncTask<Integer, Void, ScheduleListItem[]> {
        private Context mContext;
        ScheduleList(Context context){
            mContext = context;
        }
        @Override
        protected ScheduleListItem[] doInBackground(Integer... params){
            ScheduleApi api = new ScheduleApi(mContext);
            return api.GetList(params[0]);
        }
        @Override
        protected void onPostExecute(ScheduleListItem[] result) {
            //load schedules after search
            Arrays.sort(result, new ScheduleSort());
            mLastResult = result;
            updateUI();
        }
    }


}
