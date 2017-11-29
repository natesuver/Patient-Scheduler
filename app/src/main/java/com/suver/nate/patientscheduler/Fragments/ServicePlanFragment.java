package com.suver.nate.patientscheduler.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suver.nate.patientscheduler.Api.ServicePlanApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.Models.ServicePlanListItem;
import com.suver.nate.patientscheduler.R;

public class ServicePlanFragment extends Fragment {
    private static final String list_key = "ServicePlanListData";
    private static final Integer RequestCode = 0;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mServicePlanList;
    private ServicePlanListItem[] mLastResult;
    private ServicePlanFragment.ServicePlanAdapter mAdapter;
    public ServicePlanFragment() {

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
        View v =  inflater.inflate(R.layout.fragment_service_plan, container, false);
        mServicePlanList = v.findViewById(R.id.servicePlanList);
        mServicePlanList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();

       new ServicePlanFragment.ServicePlanList(getActivity().getBaseContext()).execute(ApplicationData.userSetting.getMapToEntityID());
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(list_key,mLastResult);
    }

    private void retrieveData(Bundle savedInstanceState) {
        mLastResult = (ServicePlanListItem[]) savedInstanceState.getSerializable(list_key);
    }

    public static ServicePlanFragment newInstance() {
        ServicePlanFragment fragment = new ServicePlanFragment();
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

    private class ServicePlanHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTaskName;
        private TextView mPlanNotes;

        private ServicePlanListItem mServicePlanData;
        public ServicePlanHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_serviceplan, parent, false  ));
            itemView.setOnClickListener(this);
            mTaskName = itemView.findViewById(R.id.taskName);
            mPlanNotes = itemView.findViewById(R.id.planNotes);
        }
        public void bind(ServicePlanListItem p) {
            mServicePlanData = p;
            mPlanNotes.setText(mServicePlanData.getNotes());
            mTaskName.setText(ApplicationData.tasks.GetTaskDescription(p.getTaskId()));
        }

        @Override
        public void onClick(View view) {
            //do nothing
        }
    }

    private class ServicePlanAdapter extends RecyclerView.Adapter<ServicePlanFragment.ServicePlanHolder> {
        private ServicePlanListItem[] mServicePlans;
        public ServicePlanAdapter(ServicePlanListItem[] ServicePlans) {
            mServicePlans = ServicePlans;
        }

        @Override
        public ServicePlanFragment.ServicePlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ServicePlanFragment.ServicePlanHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ServicePlanFragment.ServicePlanHolder holder, int position) {
            ServicePlanListItem sched = mServicePlans[position];
            holder.bind(sched);
        }
        @Override
        public int getItemCount() {
            return mServicePlans.length;
        }
    }

    private class ServicePlanList extends AsyncTask<Integer, Void, ServicePlanListItem[]> {
        private Context mContext;
        ServicePlanList(Context context){
            mContext = context;
        }
        @Override
        protected ServicePlanListItem[] doInBackground(Integer... params){
            ServicePlanApi api = new ServicePlanApi(mContext);
            return api.GetList(params[0]);
        }
        @Override
        protected void onPostExecute(ServicePlanListItem[] result) {
            mLastResult = result;
            mAdapter = new ServicePlanFragment.ServicePlanAdapter(mLastResult);
            mServicePlanList.setAdapter(mAdapter);
        }
    }

}
