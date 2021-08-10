package com.example.doctorcommunication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;


public class Fragment_medicalChart extends Fragment {

    //캘린더
    MaterialCalendarView materialCalendarView;
    //선택한 날짜
    TextView selectedDate;
    String selectedDateString;
    //선택한 날짜 formatter
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd (E)");
    //진료 일정 추가하기
    TextView btn_addAppointDoctor;
    //진료 후기 작성하기
    TextView btn_writeReview;
    //증상 기록 보기
    Button btn_showSymptomData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myapp", "진료기록탭 열림");
        View view = inflater.inflate(R.layout.fragment_medical_chart, container, false);

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

        //점 표시
        //materialCalendarView.addDecorator(new MC_DotEventDecorator(Color.BLUE,addDot()));
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.d("myapp", Day + " ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd ");
                Date date1 = new Date();
                String nowString = simpleDateFormat.format(date1);

                date1.setYear(Year - 1900);
                date1.setMonth(Month);
                date1.setDate(Day);

                Log.d("myapp", date1.getDay() + " ");

                String dayOfWeekDay = "";
                switch (date1.getDay()) {
                    case 5:
                        dayOfWeekDay = "(일)";
                        break;
                    case 6:
                        dayOfWeekDay = "(월)";
                        break;
                    case 0:
                        dayOfWeekDay = "(화)";
                        break;
                    case 1:
                        dayOfWeekDay = "(수)";
                        break;
                    case 2:
                        dayOfWeekDay = "(목)";
                        break;
                    case 3:
                        dayOfWeekDay = "(금)";
                        break;
                    case 4:
                        dayOfWeekDay = "(토)";
                        break;
                    default:
                        dayOfWeekDay = "알 수 없음";
                }

                selectedDateString = Year + "." + Month + "." + Day;
                nowString = dateFormat.format(date1) + dayOfWeekDay;
                selectedDate.setText(nowString);

            }
        });
        return view;
    }

    //Person1의 진료데이터가 존재하는지 판별하여
    //해당 날짜에 점을 찍어줌
    /*
    public ArrayList addDot(){
        ArrayList<CalendarDay> dates = new ArrayList<>(); //점을 찍을 날짜를 저장,반환

        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        for(int i=0;i<Person1.appointment.length;i++){
            CalendarDay day = CalendarDay.from(localDate);
            String[] time = Person1.appointment[i].getDate().split(".");
            int year = Integer.parseInt(time[0]);
            int month = Integer.parseInt(time[1]);
            int dayy = Integer.parseInt(time[2]);

            dates.add(day);
            calendar.set(year,month-1,dayy);
        }
        return dates;
    }
     */
}