package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.databinding.MapFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

public class ReportFragment extends Fragment {
    private SharedViewModel model;
    private MapFragmentBinding reportBinding;

    public ReportFragment() {

    }
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();
        return view;
    }
}
