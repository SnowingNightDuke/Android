package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.Home;
import com.fit5046.paindiary.databinding.HomeFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

public class HomeFragment extends Fragment {
    private SharedViewModel model;
    private HomeFragmentBinding homeBinding;

    public HomeFragment(){

    }

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBinding = null;
    }
}
