package com.suver.nate.patientscheduler.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suver.nate.patientscheduler.Api.InvoiceApi;
import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.Models.InvoiceListItem;
import com.suver.nate.patientscheduler.R;

import java.io.File;
import java.text.NumberFormat;


public class InvoiceFragment extends Fragment {
    private static final String LOG = "InvoiceFragment";
    private static final String list_key = "InvoiceListData";

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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
        private TextView m_invoiceNumber;
        private TextView m_invoiceDate;
        private TextView m_invoiceServiceDates;
        private TextView m_invoiceAmount;
        private TextView m_invoiceBalance;
        private TextView m_invoicePayer;

        private InvoiceListItem mInvoiceData;
        public InvoiceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_invoice, parent, false  ));
            itemView.setOnClickListener(this);
            m_invoiceNumber = itemView.findViewById(R.id.invoiceNumber);
            m_invoiceDate = itemView.findViewById(R.id.invoiceDate);
            m_invoiceServiceDates = itemView.findViewById(R.id.invoiceServiceDates);
            m_invoiceAmount = itemView.findViewById(R.id.invoiceAmount);
            m_invoiceBalance = itemView.findViewById(R.id.invoiceBalance);
            m_invoicePayer = itemView.findViewById(R.id.invoicePayer);

        }
        public void bind(InvoiceListItem i) {
            mInvoiceData = i;
            NumberFormat currency = NumberFormat.getCurrencyInstance();
            m_invoiceNumber.setText(mInvoiceData.getInvoiceNumber());
            m_invoiceDate.setText(mInvoiceData.getInvoiceDate());
            m_invoiceServiceDates.setText(mInvoiceData.getServiceDates());
            m_invoiceAmount.setText(currency.format(mInvoiceData.getAmount()));
            m_invoiceBalance.setText(currency.format(mInvoiceData.getBalance()));
            m_invoicePayer.setText(mInvoiceData.getPayerName());
        }

        @Override
        public void onClick(View view) {
            new DownloadInvoice(getActivity()).execute(mInvoiceData.getId());
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

    private class DownloadInvoice extends AsyncTask<Integer, Void, File> {
        private Context mContext;
        DownloadInvoice(Context context){
            mContext = context;
        }
        @Override
        protected File doInBackground(Integer... params){
            InvoiceApi api = new InvoiceApi(mContext);

            return api.GetInvoice(params[0]);
        }
        @Override
        protected void onPostExecute(File file) {
            Uri path = Uri.fromFile(file);
            Log.d(LOG,"Uri: " + path.getPath());
            Log.d(LOG,"Storage Writeable?: " + isExternalStorageWritable());
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try{
                startActivity(pdfIntent);
            }catch(ActivityNotFoundException e){
                Toast.makeText(mContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }

        }

        private boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }
    }

//GetInvoice(Integer invoiceId, String invoiceFileName) {



}
