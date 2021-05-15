package com.fit5046.paindiary.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.fit5046.paindiary.R;
import com.fit5046.paindiary.databinding.RecyclerRowBinding;
import com.fit5046.paindiary.entity.PainRecord;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecordViewHolder> {
    private List<PainRecord> painRecordList;
    private Context context;
    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        PainRecord painRecord = painRecordList.get(position);
        if (painRecord != null) {
            holder.date.setText(painRecord.dateOfEntry);
            holder.intensity.setText(painRecord.painIntensityLevel);
            holder.painLocation.setText(painRecord.painLocation);
            holder.mood.setText(painRecord.moodLevel);
            holder.steps.setText(painRecord.stepsTaken);
            holder.temperature.setText(painRecord.temperature);
            holder.humidity.setText(painRecord.humidity);
            holder.pressure.setText(painRecord.pressure);
            holder.email.setText(painRecord.userEmail);
        }
    }

    public void setPainRecordList(List<PainRecord> painRecordList) {
        this.painRecordList = painRecordList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView intensity;
        TextView painLocation;
        TextView mood;
        TextView steps;
        TextView temperature;
        TextView humidity;
        TextView pressure;
        TextView email;
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            intensity = itemView.findViewById(R.id.intensityLevelTextView);
            painLocation = itemView.findViewById(R.id.painLocationTextView);
            mood = itemView.findViewById(R.id.moodTextView);
            steps = itemView.findViewById(R.id.stepTextView);
            temperature = itemView.findViewById(R.id.temperatureTextView);
            humidity = itemView.findViewById(R.id.humidityTextView);
            pressure = itemView.findViewById(R.id.pressureTextView);
            email = itemView.findViewById(R.id.userEmailTextView);
        }
    }
}
