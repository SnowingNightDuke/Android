package com.fit5046.paindiary.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fit5046.paindiary.database.PainRecordDatabase;
import com.fit5046.paindiary.databinding.EntryFragmentBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.viewmodel.PainRecordViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import static java.lang.Integer.parseInt;

public class EntryFragment extends Fragment {
    private EntryFragmentBinding entryBinding;
    private PainRecordViewModel painRecordViewModel;
    public EntryFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        entryBinding = EntryFragmentBinding.inflate(inflater, container, false);
        View view = entryBinding.getRoot();
        //TODO: getApplication need to be verified.
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);

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
        //TODO: validate if all the required data has been input in field respectively
        if (validation()) {
            Toast.makeText(getContext(), "Successfully saved", Toast.LENGTH_LONG).show();
            //TODO: Disable input and pass data to room
            editingSwitcher(false);
            entryBinding.saveButton.setEnabled(false);
            entryBinding.editButton.setEnabled(true);
            sendData();
        } else {
            Toast.makeText(getContext(), "Save Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void sendData() {
        int painLevel = entryBinding.levelSeekBar.getProgress();
        String painLocation = entryBinding.locationSpinner.getSelectedItem().toString();
        String moodLevel = moodLevel();
        int stepsGoal = Integer.parseInt(entryBinding.stepEditText.getText().toString());
        int stepsTaken = Integer.parseInt(entryBinding.stepTakenEditText.getText().toString());
        String date = getTime();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("WEATHER_FILE", Context.MODE_PRIVATE);
        int temperature = sharedPreferences.getInt("temperature", 0);
        int humidity = sharedPreferences.getInt("humidity", 0);
        int pressure = sharedPreferences.getInt("pressure", 0);

        Intent intent = getActivity().getIntent();
        String email = intent.getStringExtra("userEmail");

        PainRecord painRecord = new PainRecord(painLevel, painLocation, moodLevel, stepsGoal, stepsTaken, date, temperature, humidity, pressure,email);
        painRecordViewModel.insert(painRecord);
        //AsyncTask.execute(() -> db.painRecordDAO().insert(painRecord));
    }

    private String getTime() {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(d);
    }

    private String moodLevel() {
        String moodString;
        int rating = (int)(entryBinding.moodRatingBar.getRating());
        switch (rating) {
            case 2:
                moodString = "low";
                break;
            case 3:
                moodString = "average";
                break;
            case 4:
                moodString = "good";
                break;
            case 5:
                moodString = "very good";
                break;
            default:
                moodString = "very low";
        }
        return moodString;
    }

    private void editingSwitcher (boolean switcher) {
        entryBinding.levelSeekBar.setEnabled(switcher);
        entryBinding.locationSpinner.setEnabled(switcher);
        entryBinding.moodRatingBar.setEnabled(switcher);
        entryBinding.stepEditText.setEnabled(switcher);
        entryBinding.stepTakenEditText.setEnabled(switcher);
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
