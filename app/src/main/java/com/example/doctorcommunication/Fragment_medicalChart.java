package com.example.doctorcommunication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;


public class Fragment_medicalChart extends Fragment {

    //캘린더
    MaterialCalendarView materialCalendarView;
    //선택한 날짜
    TextView selectedDate;
    //선택한 날짜 formatter
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd (E)");
    //진료 일정 추가하기
    TextView btn_addAppointDoctor;
    //진료 후기 작성하기
    TextView btn_writeReview;
    //증상 기록 보기
    Button btn_showSymptomData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","진료기록탭 열림");
        View view =  inflater.inflate(R.layout.fragment_medical_chart,container,false);

        //캘린더
        materialCalendarView = view.findViewById(R.id.calendarView);
        //선택한 날짜
        selectedDate = view.findViewById(R.id.selectedDate);
        //진료 일정 추가하기
        btn_addAppointDoctor = view.findViewById(R.id.btn_addAppointDoctor);
        //진료 후기 작성하기
        btn_writeReview = view.findViewById(R.id.btn_writeReview);
        //증상 기록 보기
        btn_showSymptomData = view.findViewById(R.id.btn_showSymptomData);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d("myapp",selectedDate.toString());
            }
        });
        return view;
    }

}