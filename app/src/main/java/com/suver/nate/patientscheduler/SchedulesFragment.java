package com.suver.nate.patientscheduler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suver.nate.patientscheduler.Api.OfficeSettingsApi;
import com.suver.nate.patientscheduler.Api.ScheduleApi;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.Models.OfficeSettings;
import com.suver.nate.patientscheduler.Models.Schedule;

import org.json.JSONObject;

import java.util.Arrays;


public class SchedulesFragment extends Fragment {
    private static final String list_key = "ScheduleListData";
    private OnFragmentInteractionListener mListener;
    private RecyclerView mScheduleList;
    private Schedule[] mLastResult;
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
        mScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        new ScheduleList(getActivity().getBaseContext()).execute(ApplicationData.userSetting.getMapToEntityID());
        return v;
    }

    private void updateUI() {
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
        mLastResult = (Schedule[]) savedInstanceState.getSerializable(list_key);
    }

    public static SchedulesFragment newInstance() {
        SchedulesFragment fragment = new SchedulesFragment();
        return fragment;
    }

   // public void onButtonPressed(Uri uri) {
   //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
   // }

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

        private Schedule mScheduleData;
        public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_schedule, parent, false  ));
            itemView.setOnClickListener(this);
            mService = itemView.findViewById(R.id.schedule_service);
            mDate = itemView.findViewById(R.id.schedule_date);
            mCaregiver = itemView.findViewById(R.id.schedule_caregiver);
            mStatus = itemView.findViewById(R.id.schedule_status);
        }
        public void bind(Schedule sched) {
            mScheduleData = sched;
            mService.setText(mScheduleData.getService().getName());
            mStatus.setText(mScheduleData.getStatus().getFullDescription());
            mCaregiver.setText(mScheduleData.getCaregiverName());
            mDate.setText(mScheduleData.getPrintedDate());
        }

        @Override
        public void onClick(View view) {
          //  new Search(getActivity().getBaseContext()).execute(mCityData.getResourceUrl());
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleHolder> {
        private Schedule[] mSchedules;
        public ScheduleAdapter(Schedule[] schedules) {
            mSchedules = schedules;
        }

        @Override
        public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScheduleHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ScheduleHolder holder, int position) {
            Schedule sched = mSchedules[position];
            holder.bind(sched);
        }
        @Override
        public int getItemCount() {
            return mSchedules.length;
        }
    }

    private class ScheduleList extends AsyncTask<Integer, Void, Schedule[]> {
        private Context mContext;
        private JSONObject mToken;
        ScheduleList(Context context){
            mContext = context;
        }
        @Override
        protected Schedule[] doInBackground(Integer... params){
            ScheduleApi api = new ScheduleApi(mContext);
            return api.GetList(params[0]);
        }
        @Override
        protected void onPostExecute(Schedule[] result) {
            //load schedules after search
            Arrays.sort(result, new ScheduleSort());
            mLastResult = result;
            updateUI();
        }
    }


}
