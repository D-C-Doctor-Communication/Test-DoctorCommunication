package com.example.doctorcommunication;

//android 버전 30쓸거면 androidx.Fragment 사용할것
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_conditionAnalysis extends Fragment {

    //상단 날짜 선택 바
    private Button nextBtn,previousBtn;
    private TextView monthSelect;

    //[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]
    //병원 예약 횟수 텍스트
    private TextView reservation_count;
    //심각도 5 이상 텍스트
    private TextView severity_more_5;
    //총 기록된 통증 수 텍스트
    private TextView accrue_symptom_count;

    //그래프
    private LineChart lineChart;
    //그래프 증상 선택 버튼
    private Button select_symptom;

    //증상 빈도 순위
    private TextView firstSymptom;
    private TextView secondSymptom;
    private TextView thirdSymptom;


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
        //증상 빈도 순위
        firstSymptom = view.findViewById(R.id.first_symptom);
        secondSymptom = view.findViewById(R.id.second_symptom);
        thirdSymptom = view.findViewById(R.id.third_symptom);
        //그래프
        lineChart = view.findViewById(R.id.condition_chart);
        //그래프 증상선택
        select_symptom = view.findViewById(R.id.select_symptom);




        //현재 날짜를 기준으로 상단 선택 바 텍스트 기본 지정
        SimpleDateFormat simpleFormatting = new SimpleDateFormat ( "yyyy년 MM월");
        Calendar time = Calendar.getInstance();
        String monthSelectText = simpleFormatting.format(time.getTime());
        monthSelect.setText(monthSelectText);

        //날짜 비교 위해 날짜형식을 "yyyy년 MM월" -> 0000.00형으로 바꿈
        String dataString = changeToString(monthSelectText);
        //기본 선택된 달의 각 텍스트 표시
        reservation_count.setText(OrganizedData.appointmentDC(dataString)+"회");
        severity_more_5.setText(OrganizedData.moreThanFive(dataString)+"회");
        accrue_symptom_count.setText(OrganizedData.accruedData(dataString)+"개");
        //증상 순위
        setRanking(dataString,firstSymptom,secondSymptom,thirdSymptom);



        //그래프 증상선택 버튼 이벤트
        select_symptom.setOnClickListener(v -> {
            final View popupView = getLayoutInflater().inflate(R.layout.popup_selectsymptom, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(popupView);

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            //취소버튼
            Button btnApplySym = popupView.findViewById(R.id.btn_apply_symptom);
            btnApplySym.setOnClickListener(v1 -> alertDialog.dismiss());
        });



        //상단 날짜 선택 바 -> 이전 버튼 눌렀을 경우 1달씩 줄임
        previousBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , -1);
            String previousMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(previousMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = changeToString(previousMonthText); //날짜형식 바꿈
            reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"회");
            severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"회");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"개");


            //증상 순위
            setRanking(dataStr,firstSymptom,secondSymptom,thirdSymptom);

        });


        //상단 날짜 선택 바 -> 다음음 버튼 눌렀을 경우 1달씩 늘림
        nextBtn.setOnClickListener(v -> {
            time.add(Calendar.MONTH , +1);
            String nextMonthText = simpleFormatting.format(time.getTime());
            monthSelect.setText(nextMonthText);
            //버튼 눌릴때마다 텍스트 새로고침
            String dataStr = changeToString(nextMonthText); //날짜형식 바꿈
            reservation_count.setText(OrganizedData.appointmentDC(dataStr)+"회");
            severity_more_5.setText(OrganizedData.moreThanFive(dataStr)+"회");
            accrue_symptom_count.setText(OrganizedData.accruedData(dataStr)+"개");

            //증상 순위
            setRanking(dataStr,firstSymptom,secondSymptom,thirdSymptom);
        });





        return view;
    }




//기능별 공통사용 메소드
    //선택한 날과 각 데이터의 달을 비교하기위해 선택한 달을 0000.00형으로 바꿈
    public static String changeToString(String selectedMonth){
        String strDate = selectedMonth.substring(0,4)+"."+selectedMonth.substring(6,8);
        return strDate;
    }
    //데이터의 기록 날짜가 상단 바에서 선택한 달과 일치하면 true 반환
    public static boolean isInSameMonth(String recordedDate,String strDate){ //0000년 00월
        //0000.00.00(데이터.getDate())과 선택한(0000.00) 달 비교
        if(recordedDate.substring(0,7).equals(strDate)) return true;
        return false;
    }


//증상순위
    static void setRanking(String strDate,TextView one,TextView two,TextView three){
        HashMap<String,Integer> data = new HashMap<>();//new에서 타입 파라미터 생략가능
        //각 증상을 key값으로, 증상의 개수를 value값으로 가지는 Map 생성
        for(int i=0;i<Person1.symptom.length;i++){
            data.put(Person1.symptom[i].getPart(),0);
        }
        //증상에 따라 +1
        for(int i=0;i<Person1.symptom.length;i++) {
            if(isInSameMonth(Person1.symptom[i].getDate(),strDate))
                data.put(Person1.symptom[i].getPart(), data.get(Person1.symptom[i].getPart())+1);
        }
        //value 개수를 기준으로 내림차순 정렬 (정렬결과에 따라 순위 지정)
        // Map.Entry 리스트 작성
        List<Map.Entry<String, Integer>> list_entries = new ArrayList<>(data.entrySet());
        // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
        Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
            // compare로 값을 비교
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2)
            {   // 내림 차순으로 정렬
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        if(list_entries.get(0).getValue()!=0) one.setText(list_entries.get(0).getKey());
        else one.setText("해당없음");
        if(list_entries.get(1).getValue()!=0) two.setText(list_entries.get(1).getKey());
        else two.setText("해당없음");
        if(list_entries.get(2).getValue()!=0) three.setText(list_entries.get(2).getKey());
        else three.setText("해당없음");
        //값을 받으려면 list_entries.get(i).getValue().toString();

    }


//그래프의 x값(각 주별 심각도 평균)
    static int[] getAverageOfWeek(String strDate){ //상단바에서 선택한 날짜
        int[] graphData = new int[4];   //그래프의 x좌표
        for(int i=0;i<graphData.length;i++){
            graphData[i] = 0;
        }
        int firstWeek = 0,fNum = 0;     //1주차 심각도의 총합과 개수
        int secondWeek = 0,sNum = 0;    //2주차 심각도의 총합과 개수
        int thirdWeek = 0,tNum = 0;     //3주차 심각도의 총합과 개수
        int fourthWeek = 0,foNum = 0;   //4주차 심각도의 총합과 개수
        for(int i=0;i<Person1.symptom.length;i++){
            switch (isInSameWeek(Person1.symptom[i].getDate(),strDate)){
                case 1 : //1주차
                    firstWeek += Person1.symptom[i].getPain_level();
                    fNum++;
                    break;
                case 2 : //2주차
                    secondWeek += Person1.symptom[i].getPain_level();
                    sNum++;
                    break;
                case 3 : //3주차
                    thirdWeek += Person1.symptom[i].getPain_level();
                    tNum++;
                    break;
                case 4 : //4주차
                    fourthWeek += Person1.symptom[i].getPain_level();
                    foNum++;
                    break;
            }
        }

        //각 데이터의 값이 0일경우 그래프에도 0으로 표시
        if(fNum!=0) {
            graphData[0] = firstWeek/fNum;
            Log.d("check",firstWeek+" / "+fNum+" : "+graphData[0]);
        }
        if(sNum!=0){
            graphData[1] = secondWeek/sNum;
            Log.d("check",secondWeek+" / "+sNum+" : "+graphData[1]);
        }
        if(tNum!=0) {
            graphData[2] = thirdWeek/tNum;
            Log.d("check",thirdWeek+" / "+tNum+" : "+graphData[2]);
        }
        if(foNum!=0) {
            graphData[3] = fourthWeek/foNum;
            Log.d("check",fourthWeek+" / "+foNum+" : "+graphData[3]);
        }



        return graphData;
    }
    //각 날짜가 선택한 달과 일치하지 않으면 0 반환
    //1주차에 존재하면 1 반환
    //2주차 : 2, 3주차 : 3, 4주차 : 4
    static int isInSameWeek(String recordedDate,String strDate){ //각각 데이터가 입력된 날짜, 상단 바에서 선택한 날짜
        //0000.00.00(데이터.getDate())과 선택한(0000.00) 달 비교
        if(recordedDate.substring(0,7).equals(strDate)){
            int checkDate = Integer.parseInt(recordedDate.substring(8)); //몇일인지 저장
            if(checkDate>=1&&checkDate<=7) return 1;
            else if(checkDate>=8&&checkDate<=14) return 2;
            else if(checkDate>=15&&checkDate<=21) return 3;
            else if(checkDate>=22&&checkDate<=31) return 4;
        }
        return 0;
    }



//[병원 예약 횟수, 심각도 5 이상, 총 기록된 통증 수]와 관련된 작업 클래스
    static class OrganizedData{
        //총 기록된 통증 수
        public static int accruedData(String strDate){
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

//그래프 관련 메소드
    //그래프 초기화(기본설정)
    private void initGraph(){
        //그래프 데이터 리스트 생성 (x축 한칸당 값, y값)
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 0));
        entries.add(new Entry(4, 4));


        //두통에 대한 그래프 선 그리기
        LineDataSet lineDataSet = new LineDataSet(entries, "두통");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleHoleColor(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        //X값 속성 설정
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축이 아래에 위치하도록 설정
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(20, 20, 0);
        xAxis.setDrawLabels(true); //왼쪽 라벨
        xAxis.setDrawAxisLine(false); //왼쪽 라벨라인
        xAxis.setDrawGridLines(false); //세로선
        //Y값 속성 설정
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false); //오른쪽 라벨
        yRAxis.setDrawAxisLine(false); //오른쪽 라벨라인
        yRAxis.setDrawGridLines(false);

        //부가설명 공백으로 처리
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        lineChart.setDoubleTapToZoomEnabled(false); //그래프 이동(줌기능)
        lineChart.setDrawGridBackground(false);

        // lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        return;
    }


}