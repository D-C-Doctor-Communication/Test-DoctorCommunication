package com.example.doctorcommunication;

//android 버전 30쓸거면 androidx.Fragment 사용할것
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
//Graph 관련 import
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class Fragment_conditionAnalysis extends Fragment {

    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","상태분석탭 열림");
        View view =  inflater.inflate(R.layout.fragment_condition_analysis,container,false);

        btn = view.findViewById(R.id.btn_1_symptom);

        //선 그래프
        LineChart lineChart;

        ArrayList<Entry> entry_chart = new ArrayList<>();


        lineChart = (LineChart) view.findViewById(R.id.chart);//layout의 id
        LineData chartData = new LineData();

        int y = 0;
        for(int i=0;i<10;i++){

            entry_chart.add(new Entry(i, y+=1));
        }

    /* 만약 (2, 3) add하고 (2, 5)한다고해서
    기존 (2, 3)이 사라지는게 아니라 x가 2인곳에 y가 3, 5의 점이 찍힘 */

        LineDataSet lineDataSet = new LineDataSet(entry_chart,"두통");
        chartData.addDataSet(lineDataSet);

        lineChart.setData(chartData);

        lineChart.invalidate();

        return view;
    }

}