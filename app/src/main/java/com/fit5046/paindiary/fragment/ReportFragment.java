package com.fit5046.paindiary.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.CircularGauge;
import com.anychart.charts.Pie;
import com.anychart.charts.Radar;
import com.fit5046.paindiary.databinding.ReportFragmentBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    private ReportFragmentBinding reportBinding;
    private PainRecordViewModel painRecordViewModel;
    private String[] painLoc = {"back", "neck", "head", "knees", "hips", "abdomen", "elbows", "shoulders", "shins", "jaw", "facial"};
    private int[] count;
    private int stepsGoal;
    private int stepsDone;
    public ReportFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
        painRecordViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                statistics(painRecords);
                retrieveSteps(painRecords);
                setupPieChart();
                reportBinding.pieButton.setOnClickListener(v -> {
                    setupPieChart();
                });
                reportBinding.donutButton.setOnClickListener(v -> {
                    setupDonutChart();
                });

            }
        });
        return view;
    }

    private void retrieveSteps(List<PainRecord> painRecords) {
        PainRecord latestRecord = painRecords.get(painRecords.size() - 1);
        stepsDone = latestRecord.stepsTaken;
        stepsGoal = latestRecord.stepsGoal;
    }

    private void statistics(List<PainRecord> painRecords) {
        int backCount = 0;
        int neckCount = 0;
        int headCount = 0;
        int kneesCount = 0;
        int hipsCount = 0;
        int abdomenCount = 0;
        int elbowsCount = 0;
        int shouldersCount = 0;
        int shinsCount = 0;
        int jawCount = 0;
        int facialCount = 0;

        for (PainRecord painRecord : painRecords) {
            if (painRecord.painLocation.equals("back")) {
                backCount++;
            } else if (painRecord.painLocation.equals("neck")) {
                neckCount++;
            } else if (painRecord.painLocation.equals("head")) {
                headCount++;
            } else if (painRecord.painLocation.equals("knees")) {
                kneesCount++;
            } else if (painRecord.painLocation.equals("hips")) {
                hipsCount++;
            } else if (painRecord.painLocation.equals("abdomen")) {
                abdomenCount++;
            } else if (painRecord.painLocation.equals("elbows")) {
                elbowsCount++;
            } else if (painRecord.painLocation.equals("shoulders")) {
                shouldersCount++;
            } else if (painRecord.painLocation.equals("shins")) {
                shinsCount++;
            } else if (painRecord.painLocation.equals("jaw")) {
                jawCount++;
            } else if (painRecord.painLocation.equals("facial")) {
                facialCount++;
            }
        }
        count = new int[]{backCount, neckCount, headCount, kneesCount, hipsCount
                ,abdomenCount, elbowsCount, shouldersCount, shinsCount, jawCount, facialCount};
    }

    public void setupDonutChart() {
        PieChart pieChart = reportBinding.donutView;
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(stepsDone,"Steps Taken"));
        pieEntries.add(new PieEntry(stepsGoal, "Steps Goal"));
        PieDataSet dataSet = new PieDataSet(pieEntries, "Steps Taken");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
        reportBinding.anyChartView.setVisibility(View.INVISIBLE);
        reportBinding.donutView.setVisibility(View.VISIBLE);
    }

    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < painLoc.length; i++) {
            dataEntries.add(new ValueDataEntry(painLoc[i], count[i]));
        }
        pie.data(dataEntries);
        pie.title("Pain Locations");
        reportBinding.anyChartView.setChart(pie);
        reportBinding.anyChartView.setVisibility(View.VISIBLE);
        reportBinding.donutView.setVisibility(View.INVISIBLE);
    }

}
