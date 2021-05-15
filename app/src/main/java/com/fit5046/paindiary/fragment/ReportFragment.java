package com.fit5046.paindiary.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.fit5046.paindiary.databinding.ReportFragmentBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.viewmodel.PainRecordViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportFragment extends Fragment {

    private ReportFragmentBinding reportBinding;
    private PainRecordViewModel painRecordViewModel;
    private final String[] painLoc = {"back", "neck", "head", "knees", "hips", "abdomen", "elbows", "shoulders", "shins", "jaw", "facial"};
    private int[] count;
    private int stepsGoal;
    private int stepsDone;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    public ReportFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();
        test();
        initDatePicker();
        initDatePicker2();
        reportBinding.startDateButton.setOnClickListener(v -> {
            datePickerDialog.show();
            reportBinding.startDateButton.setText(getTodaysDate());
        });
        reportBinding.endDateButton.setOnClickListener(v -> {
            datePickerDialog2.show();
            reportBinding.endDateButton.setText(getTodaysDate());
        });


        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PainRecordViewModel.class);
        painRecordViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                statistics(painRecords);
                retrieveSteps(painRecords);
                //setupPieChart();
                reportBinding.pieButton.setOnClickListener(v -> {
                    setupPieChart();
                });
                reportBinding.donutButton.setOnClickListener(v -> {
                    setupDonutChart();
                });
                reportBinding.lineButton.setOnClickListener(v -> {
                    setupLineChart();
                });
            }
        });
        return view;
    }

    private void spinner() {
        List<String> list = new ArrayList<>();
        list.add("Temperature");
        list.add("Humidity");
        list.add("Pressure");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        reportBinding.weatherSpinner.setAdapter(spinnerAdapter);
    }

    private void setupLineChart() {
        reportBinding.anyChartView.setVisibility(View.INVISIBLE);
        reportBinding.donutView.setVisibility(View.INVISIBLE);
        reportBinding.startDateButton.setVisibility(View.VISIBLE);
        reportBinding.endDateButton.setVisibility(View.VISIBLE);
        reportBinding.weatherSpinner.setVisibility(View.VISIBLE);
        spinner();
    }

    private void test() {
        reportBinding.anyChartView.setVisibility(View.INVISIBLE);
        reportBinding.donutView.setVisibility(View.INVISIBLE);
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void retrieveSteps(List<PainRecord> painRecords) {
        PainRecord latestRecord = painRecords.get(painRecords.size() - 1);
        stepsDone = latestRecord.stepsTaken;
        stepsGoal = latestRecord.stepsGoal;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                reportBinding.startDateButton.setText(date);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, dayOfMonth);
    }

    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                reportBinding.endDateButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(getContext(), style, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString (int day, int month, int year) {
        return getMonthFormat(month) + "/" + day + "/" + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
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
