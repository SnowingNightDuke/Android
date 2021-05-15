package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.fit5046.paindiary.databinding.RecordFragmemtBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.viewmodel.PainRecordViewModel;

import java.util.List;

public class RecordFragment extends Fragment {
    private RecordFragmemtBinding recordBinding;
    private PainRecordViewModel painRecordViewModel;
    public RecordFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recordBinding = recordBinding.inflate(inflater, container, false);
        View view = recordBinding.getRoot();
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
        //TODO: observe: the first parameter should be verified. Original: this
        painRecordViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(@Nullable final List<PainRecord> painRecords) {
                String allPainRecords = "";
                for (PainRecord temp : painRecords) {
                    String painRecordDetails = (temp.pid + " "
                            + temp.dateOfEntry + " "
                            + temp.painLocation + " "
                            + temp.painIntensityLevel + " "
                            + temp.moodLevel + " "
                            + temp.stepsTaken + " "
                            + temp.temperature + " "
                            + temp.humidity + " "
                            + temp.pressure);
                }
            }
        });



        return view;
    }
}
