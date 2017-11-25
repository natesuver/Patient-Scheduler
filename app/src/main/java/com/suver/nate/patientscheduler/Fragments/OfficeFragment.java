package com.suver.nate.patientscheduler.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suver.nate.patientscheduler.ApplicationData;
import com.suver.nate.patientscheduler.Interfaces.OnFragmentInteractionListener;
import com.suver.nate.patientscheduler.R;

public class OfficeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private TextView mOfficeTitle;
    private TextView mOfficeAddress;
    public OfficeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_office, container, false);
        mOfficeTitle = v.findViewById(R.id.officeTitle);
        mOfficeAddress = v.findViewById(R.id.office);
        mOfficeTitle.setText(ApplicationData.officeSettings.getName());
        mOfficeAddress.setText(ApplicationData.officeSettings.GetFullAddress());
        return v;
    }

    public static OfficeFragment newInstance() {
        OfficeFragment fragment = new OfficeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
