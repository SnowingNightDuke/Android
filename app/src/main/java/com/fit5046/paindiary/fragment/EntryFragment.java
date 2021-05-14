package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fit5046.paindiary.databinding.EntryFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends Fragment {
    private EntryFragmentBinding entryBinding;

    public EntryFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        entryBinding = EntryFragmentBinding.inflate(inflater, container, false);
        View view = entryBinding.getRoot();
        spinner();
        entryBinding.editButton.setEnabled(false);
        entryBinding.editButton.setOnClickListener(v -> {
            entryBinding.saveButton.setEnabled(true);
            entryBinding.editButton.setEnabled(false);
            editingSwitcher(true);
        });
        entryBinding.saveButton.setOnClickListener(v -> {
            save();
        });
        return view;
    }

    private void save() {
        boolean success = false;
        //TODO: validate if all the required data has been input in field respectively
        if (validation()) {
            success = true;
        }
        if (success) {
            Toast.makeText(getContext(), "Successfully saved", Toast.LENGTH_LONG).show();
            //TODO: Disable input and pass data to room
            editingSwitcher(true);
            sendData();
        } else {
            Toast.makeText(getContext(), "Save Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData() {
    }

    private void editingSwitcher (boolean switcher) {
        entryBinding.levelSeekBar.setEnabled(!switcher);
        entryBinding.locationSpinner.setEnabled(!switcher);
        entryBinding.moodRatingBar.setEnabled(!switcher);
        entryBinding.stepEditText.setEnabled(!switcher);
        entryBinding.saveButton.setEnabled(!switcher);
        entryBinding.editButton.setEnabled(switcher);
    }

    private boolean validation() {
        return true;
    }


    private void spinner() {
        List<String> list = new ArrayList<>();
        list.add("back");
        list.add("neck");
        list.add("head");
        list.add("knees");
        list.add("hips");
        list.add("abdomen");
        list.add("elbows");
        list.add("shoulders");
        list.add("shins");
        list.add("jaw");
        list.add("facial");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        entryBinding.locationSpinner.setAdapter(spinnerAdapter);
    }
}
