package com.fit5046.paindiary.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit5046.paindiary.R;
import com.fit5046.paindiary.databinding.RecyclerRowBinding;
import com.fit5046.paindiary.entity.PainRecord;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecordViewHolder> {
    private List<PainRecord> painRecordList;
    public RecyclerViewAdapter(Context context){}

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
            holder.date
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
        }
    }
}
