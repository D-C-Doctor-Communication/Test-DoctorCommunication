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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        wCalender[0] = view.findViewById(R.id.wCalender_sun); //일요일
        wCalender[1] = view.findViewById(R.id.wCalender_mon); //월요일
        wCalender[2] = view.findViewById(R.id.wCalender_tue); //화요일
        wCalender[3] = view.findViewById(R.id.wCalender_wed); //수요일
        wCalender[4] = view.findViewById(R.id.wCalender_thu); //목요일
        wCalender[5] = view.findViewById(R.id.wCalender_fri); //금요일
        wCalender[6] = view.findViewById(R.id.wCalender_sat); //토요일

//카드1 - 증상등록으로 이동
        btn_addSymptom.setOnClickListener(v -> { //람다형식 사용 ~ new Button.OnClickListener()와 같은 기능
            Intent addSymptom = new Intent(getContext(), SearchList.class);
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

        TextView ymTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        WeekCalendar weekCalendar = new WeekCalendar();
        Date todayDate = new Date();
        weekCalendar.setWeekCalenderDate(view,todayDate,ymTextView,wDate);



//ListView

        ListView listView = (ListView)view.findViewById(R.id.home_listView);
        //오늘로 기본 리스트 보여짐

        WeekCalendar.createDataListToday(ymTextView,wDate,listView);


        //각 날짜를 클릭했을 때 날짜와 일치하는 데이터 불러오기
        wCalender[0].setOnClickListener(v -> { //일요일
            Log.d("myapp","일요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,0,listView);
        });
        wCalender[1].setOnClickListener(v -> {
            Log.d("myapp","월요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,1,listView);
        });
        wCalender[2].setOnClickListener(v -> {
            Log.d("myapp","화요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,2,listView);
        });
        wCalender[3].setOnClickListener(v -> {
            Log.d("myapp","수요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,3,listView);
        });
        wCalender[4].setOnClickListener(v -> {
            Log.d("myapp","목요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,4,listView);
        });
        wCalender[5].setOnClickListener(v -> {
            Log.d("myapp","금요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,5,listView);
        });
        wCalender[6].setOnClickListener(v -> {
            Log.d("myapp","토요일 눌림");
            WeekCalendar.createDataList(ymTextView,wDate,6,listView);
        });



        return view;
    }
    static class WeekCalendar{
        static void createDataListToday(TextView ymTextView, TextView[] wDate, ListView listView){
            Calendar calendar = Calendar.getInstance();

            createDataList(ymTextView,wDate,calendar.get(Calendar.DAY_OF_WEEK)-1,listView);
        }


        static void createDataList(TextView ymTextView, TextView[] wDate, int index, ListView listView){

            //각 요일별 isSameDate속성 false로 초기화
            initializeisSameDate();
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[index].getText().toString();
            //data의 날짜가 선택된 날짜와 일치하면 isSameDate 속성을 true로 변경
            int sameDatacount = setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount); //이후에 객체를 배열로 만들면 for문에 sameDataCount 사용

            //listView 참조 및 Adapter 연결
            HomeListViewAdapter adapter = new HomeListViewAdapter();
            //Adapter 지정
            listView.setAdapter(adapter);
            //선택한 날짜와 같은 데이터일때 어댑터에 아이템 추가
            for(int i = 0; i<Person1.symptom.length; i++){
                if(Person1.symptom[i].isSameDate) adapter.addItem(Person1.symptom[i].getPart(),R.drawable.img_pain_sym1,Integer.parseInt(Person1.symptom[i].getPain_level()),Person1.symptom[i].getPain_characteristics(),Person1.symptom[i].getPain_situation());
            }
            adapter.notifyDataSetChanged();
            Log.d("myapp","Adapter added");
        }

        static void initializeisSameDate(){
            for(int i=0;i<Person1.symptom.length;i++){
                Person1.symptom[i].isSameDate = false;
            }
        }

        static int setSameDatetoTrue(String date){
            int countSameDate = 0;
            for(int i=0;i<Person1.symptom.length;i++) {
                if(Person1.symptom[i].getDate().equals(date)){
                    Person1.symptom[i].checkSameDate();
                    countSameDate++;
                }
            }

            return countSameDate;
        }

        @SuppressLint("SetTextI18n")
        void setWeekCalenderDate(View view, Date date, TextView ymTextView, TextView[] wDate){ //주간캘린더 날짜변경 메소드

            Log.d("mytag","setWeekCalenderDate 과정 통과");
            //날짜 형식 지정
            SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //한국 기준 시간 사용
            todaySdf.format(date); //한국 시간 적용

            //시작 날짜를 일요일로 고정
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1)));

            //0000년 00월 텍스트 적용
            ymTextView.setText(todaySdf.format(cal.getTime()).substring(0,4)+"년 "+todaySdf.format(cal.getTime()).substring(5,7)+"월");

            for ( int i = 0; i < 7; i++ ) {
                //00일 텍스트 적용
                wDate[i].setText(todaySdf.format(cal.getTime()).substring(8));
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }

        }
    }
}
