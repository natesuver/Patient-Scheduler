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

import com.suver.nate.patientscheduler.Api.InvoiceApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.Models.InvoiceListItem;
import com.suver.nate.patientscheduler.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {
    private static final String list_key = "InvoiceListData";
    private static final Integer RequestCode = 0;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mInvoiceList;
    private InvoiceListItem[] mLastResult;
    private InvoiceFragment.InvoiceAdapter mAdapter;
    public InvoiceFragment() {

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
        View v =  inflater.inflate(R.layout.fragment_invoice, container, false);
        mInvoiceList = v.findViewById(R.id.invoiceList);
        mInvoiceList.setLayoutManager(new LinearLayoutManager(getActivity()));

        new InvoiceFragment.InvoiceList(getActivity().getBaseContext()).execute(ApplicationData.userSetting.getMapToEntityID());
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(list_key,mLastResult);
    }

    private void retrieveData(Bundle savedInstanceState) {
        mLastResult = (InvoiceListItem[]) savedInstanceState.getSerializable(list_key);
    }

    public static InvoiceFragment newInstance() {
        InvoiceFragment fragment = new InvoiceFragment();
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

    private class InvoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTaskName;
        private TextView mPlanNotes;

        private InvoiceListItem mInvoiceData;
        public InvoiceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_invoice, parent, false  ));
            itemView.setOnClickListener(this);
            mTaskName = itemView.findViewById(R.id.taskName);
            mPlanNotes = itemView.findViewById(R.id.planNotes);
        }
        public void bind(InvoiceListItem p) {
            mInvoiceData = p;
           // mPlanNotes.setText(mInvoiceData.getNotes());
           // mTaskName.setText(ApplicationData.tasks.GetTaskDescription(p.getTaskId()));
        }

        @Override
        public void onClick(View view) {
            //no nothing
        }
    }

    private class InvoiceAdapter extends RecyclerView.Adapter<InvoiceFragment.InvoiceHolder> {
        private InvoiceListItem[] mInvoices;
        public InvoiceAdapter(InvoiceListItem[] Invoices) {
            mInvoices = Invoices;
        }

        @Override
        public InvoiceFragment.InvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InvoiceFragment.InvoiceHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(InvoiceFragment.InvoiceHolder holder, int position) {
            InvoiceListItem sched = mInvoices[position];
            holder.bind(sched);
        }
        @Override
        public int getItemCount() {
            return mInvoices.length;
        }
    }

    private class InvoiceList extends AsyncTask<Integer, Void, InvoiceListItem[]> {
        private Context mContext;
        InvoiceList(Context context){
            mContext = context;
        }
        @Override
        protected InvoiceListItem[] doInBackground(Integer... params){
            InvoiceApi api = new InvoiceApi(mContext);
            return api.GetList(params[0]);
        }
        @Override
        protected void onPostExecute(InvoiceListItem[] result) {
            mLastResult = result;
            mAdapter = new InvoiceFragment.InvoiceAdapter(mLastResult);
            mInvoiceList.setAdapter(mAdapter);
        }
    }

}
