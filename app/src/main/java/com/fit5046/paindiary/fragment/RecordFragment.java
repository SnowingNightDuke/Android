package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit5046.paindiary.databinding.RecordFragmemtBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.recyclerview.RecyclerViewAdapter;
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

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        recordBinding.recordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recordBinding.recordRecyclerView.addItemDecoration(dividerItemDecoration);

        painRecordViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                recordBinding.recordRecyclerView.setAdapter(new RecyclerViewAdapter(painRecords));
            }
        });
    }

}
