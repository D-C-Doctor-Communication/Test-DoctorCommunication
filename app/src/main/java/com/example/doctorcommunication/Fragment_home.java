package com.example.doctorcommunication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Fragment_home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","home탭 열림");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

//세팅
        //카드 - 증상등록 버튼
        Button btn_addSymptom = (Button)view.findViewById(R.id.btn_addSymptom);
        //카드 - 의사와의 만남 버튼
        Button btn_meetingDoc = (Button)view.findViewById(R.id.btn_meetingDoc);
        //카드 - 녹음하기 버튼
        Button btn_recording = (Button)view.findViewById(R.id.btn_recording);
        //주간캘린더 - 각 요일별 날짜 카드뷰
        CardView[] wCalender = new CardView[7];
        wCalender[0] = (CardView)view.findViewById(R.id.wCalender_sun); //일요일
        wCalender[1] = (CardView)view.findViewById(R.id.wCalender_mon); //월요일
        wCalender[2] = (CardView)view.findViewById(R.id.wCalender_tue); //화요일
        wCalender[3] = (CardView)view.findViewById(R.id.wCalender_wed); //수요일
        wCalender[4] = (CardView)view.findViewById(R.id.wCalender_thu); //목요일
        wCalender[5] = (CardView)view.findViewById(R.id.wCalender_fri); //금요일
        wCalender[6] = (CardView)view.findViewById(R.id.wCalender_sat); //토요일



//카드1 - 증상등록으로 이동
        btn_addSymptom.setOnClickListener(v -> { //람다형식 사용 ~ new Button.OnClickListener()와 같은 기능
            Intent addSymptom = new Intent(getContext(), AddSymptom.class);
            startActivity(addSymptom);
        });

//카드2 - 의사와의 만남으로 이동
        btn_meetingDoc.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MeetingDoc.class);
            startActivity(intent);
        });

//카드3 - 녹음하기 팝업 띄움
        btn_recording.setOnClickListener(v -> {
            final View popupView = getLayoutInflater().inflate(R.layout.popup_recording, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(popupView);

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        //취소버튼
            Button btnCancel = popupView.findViewById(R.id.no_btn);
            btnCancel.setOnClickListener(v1 -> alertDialog.dismiss());

        });

//주간 캘린더 - 각 날짜에 맞도록 텍스트 주마다 변경
        WeekCalendar weekCalendar = new WeekCalendar();
        Date todayDate = new Date();
        weekCalendar.setWeekCalenderDate(view,todayDate);




        return view;
    }
}



class WeekCalendar{

    @SuppressLint("SetTextI18n")
    void setWeekCalenderDate(View view, Date date){ //주간캘린더 날짜변경 메소드

        Calendar calendarForMax = Calendar.getInstance(); //월별 최대 날짜값 얻기 위한 객체 생성

        TextView ymTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        Log.d("mytag","setWeekCalenderDate 과정 통과");
        SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //한국 기준 시간 사용
        todaySdf.format(date); //한국 시간 적용

        int YEAR = date.getYear()+1900; //1900년부터 count시작
        int MONTH = date.getMonth()+1; //0월부터 count시작
        String YEARandMonth = YEAR+"년 "+MONTH+"월";
        int DAY = date.getDay(); //일요일 : 1
        int DATE = date.getDate(); //몇일

        ymTextView.setText(YEARandMonth); //0000년 00월 텍스트 적용
        for(int i=0;i<7;i++){
            wDate[i].setText(Integer.toString(DATE+i- DAY));
            calendarForMax.set(YEAR,MONTH,DATE);
            //Log.d("mytag",Integer.toString(calendarForMax.getActualMaximum(Calendar.DAY_OF_MONTH)));
            if((DATE+i- DAY)>calendarForMax.getActualMaximum(Calendar.DAY_OF_MONTH)){ //최대 날짜보다 크면..
                wDate[i].setText(Integer.toString(7- DAY)); //7-요일값으로 텍스트 변경
            }
        }


    }
}


