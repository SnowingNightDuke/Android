package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.databinding.MapFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

public class RecordFragment extends Fragment {
    private SharedViewModel model;
    private MapFragmentBinding recordBinding;

    public RecordFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recordBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = recordBinding.getRoot();
        return view;
    }
}
