package com.fit5046.paindiary.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit5046.paindiary.databinding.RecyclerRowBinding;
import com.fit5046.paindiary.entity.PainRecord;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<PainRecord> painRecordList;
    public RecyclerViewAdapter(List<PainRecord> painRecords){
        this.painRecordList = painRecords;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final PainRecord painRecord = painRecordList.get(position);
        viewHolder.binding.idTextView.setText(Integer.toString(painRecord.pid));
        viewHolder.binding.dateTextView.setText(painRecord.dateOfEntry);
        viewHolder.binding.painLocationTextView.setText(painRecord.painLocation);;
        viewHolder.binding.intensityLevelTextView.setText(Integer.toString(painRecord.painIntensityLevel));
        viewHolder.binding.moodLevelTextView.setText(painRecord.moodLevel);
        viewHolder.binding.stepsGoalTextView.setText(Integer.toString(painRecord.stepsGoal));
        viewHolder.binding.stepsTakenTextView.setText(Integer.toString(painRecord.stepsTaken));
        viewHolder.binding.temperatureTextView.setText(Integer.toString(painRecord.temperature));
        viewHolder.binding.humidityTextView.setText(Integer.toString(painRecord.humidity));
        viewHolder.binding.pressureTextView.setText(Integer.toString(painRecord.pressure));
        viewHolder.binding.userEmailTextView.setText(painRecord.userEmail);


    }

    public void addPainRecord(List<PainRecord> painRecordList) {
        this.painRecordList = painRecordList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return painRecordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerRowBinding binding;
        public ViewHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
