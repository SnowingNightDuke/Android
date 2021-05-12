package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.databinding.HomeFragmentBinding;
import com.fit5046.paindiary.databinding.MapFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

public class MapFragment extends Fragment {
    private SharedViewModel model;
    private MapFragmentBinding mapBinding;

    public MapFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();
        return view;
    }
}
