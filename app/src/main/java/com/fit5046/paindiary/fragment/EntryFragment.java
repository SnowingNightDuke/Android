package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fit5046.paindiary.databinding.EntryFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

public class EntryFragment extends Fragment {
    private EntryFragmentBinding entryBinding;

    public EntryFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        entryBinding = EntryFragmentBinding.inflate(inflater, container, false);
        View view = entryBinding.getRoot();

        return view;
    }
}
