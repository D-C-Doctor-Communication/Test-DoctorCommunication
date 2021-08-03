package com.example.doctorcommunication;

//android 버전 30쓸거면 androidx.Fragment 사용할것
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.LongFunction;

public class Fragment_conditionAnalysis extends Fragment {

    //상단 날짜 선택 바
    Button nextBtn,previousBtn;
    TextView monthSelect;

    //[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]
    //병원 예약 횟수 텍스트
    TextView reservation_count;
    //심각도 5 이상 텍스트
    TextView severity_more_5;
    //총 기록된 통증 수 텍스트
    TextView accrue_symptom_count;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","상태분석탭 열림");
        View view =  inflater.inflate(R.layout.fragment_condition_analysis,container,false);

        //상단 날짜 선택 바
        nextBtn = view.findViewById(R.id.next_btn);
        previousBtn = view.findViewById(R.id.previous_btn);
        monthSelect = view.findViewById(R.id.month_select);
        //병원 예약 횟수 텍스트
        reservation_count = view.findViewById(R.id.reservation_count);
        //심각도 5 이상 텍스트
        severity_more_5 = view.findViewById(R.id.severity_more_5);
        //총 기록된 통증 수 텍스트
        accrue_symptom_count = view.findViewById(R.id.accrue_symptom_count);



        //현재 날짜를 기준으로 상단 선택 바 텍스트 기본 지정
        SimpleDateFormat simpleFormatting = new SimpleDateFormat ( "yyyy년 MM월");
        Calendar time = Calendar.getInstance();
        String monthSelectText = simpleFormatting.format(time.getTime());
        monthSelect.setText(monthSelectText);

        //날짜 비교 위해 날짜형식을 "yyyy년 MM월" -> 0000.00형으로 바꿈
        String dataString = OrganizedData.changeToString(monthSelectText);
        //기본 선택된 달의 각 텍스트 표시
        reservation_count.setText(OrganizedData.appointmentDC(dataString)+"회");
        severity_more_5.setText(OrganizedData.moreThanFive(dataString)+"회");
        accrue_symptom_count.setText(OrganizedData.accruedData(dataString)+"개");





        //상단 날짜 선택 바 -> 이전 버튼 눌렀을 경우 1달씩 줄임
        previousBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , -1);
            String previousMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(previousMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = OrganizedData.changeToString(previousMonthText); //날짜형식 바꿈
            reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"회");
            severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"회");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"개");
        });


        //상단 날짜 선택 바 -> 다음음 버튼 눌렀을 경우 1달씩 림
        nextBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , +1);
            String nextMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(nextMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = OrganizedData.changeToString(nextMonthText); //날짜형식 바꿈
            reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"회");
            severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"회");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"개");
        });




        return view;
    }






    //[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]와 관련된 작업 클래스
    static class OrganizedData{
        public static String changeToString(String selectedMonth){
            //선택한 날과 각 데이터의 달을 비교하기위해 선택한 달을 0000.00형으로 바꿈
            String strDate = selectedMonth.substring(0,4)+"."+selectedMonth.substring(6,8);
            return strDate;
        }

        //데이터의 기록 날짜가 상단 바에서 선택한 달과 일치하면 true 반환
        public static boolean isInSameMonth(String recordDate,String strDate){ //0000년 00월
            Log.d("myapp","isInSameMonth 진입");

            //0000.00.00(데이터.getDate())과 선택한(0000.00) 달 비교
            if(recordDate.substring(0,7).equals(strDate)) return true;

            return false;
        }

        //총 기록된 통증 수
        public static int accruedData(String strDate){
            Log.d("myapp","accruedData 진입");

            int numberOfData = 0;
            for(int i=0;i<Person1.symptom.length;i++){
                //데이터가 기록된 날짜가 선택된 달과 일치할경우 1씩 증가
                if(isInSameMonth(Person1.symptom[i].getDate(),strDate)) numberOfData++;
            }
            return numberOfData;
        }

        //심각도 5 이상
        public static int moreThanFive(String strDate){
            int numberOfData = 0;
            for(int i=0;i<Person1.symptom.length;i++){
                //데이터가 기록된 날짜가 선택된 달과 일치할경우 1씩 증가
                if(isInSameMonth(Person1.symptom[i].getDate(),strDate)){
                    if(Person1.symptom[i].getPain_level()>=5) numberOfData++;
                }
            }
            return numberOfData;
        }

        //병원 예약 횟수
        public static int appointmentDC(String strDate){
            int numberOfData = 0;
            for(int i=0;i<Person1.appointment.length;i++){
                //데이터날짜가 선택된 달과 일치할경우 1씩 증가
                if(isInSameMonth(Person1.appointment[i].getDate(),strDate)) numberOfData++;
            }
            return numberOfData;
        }
    }
}