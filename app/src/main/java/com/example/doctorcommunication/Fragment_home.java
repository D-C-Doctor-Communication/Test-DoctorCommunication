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
import java.util.Locale;


public class Fragment_home extends Fragment {
    //리스트뷰
    private ListView listView;
    private HomeListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","home탭 열림");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

//세팅
        //설정버튼
//        ImageButton setting = view.findViewById(R.id.to_setting);
//        setting.setOnClickListener(v -> {
////            Intent intent = new Intent(getActivity(), SettingActivity.class);
////            startActivity(intent);
//            Log.d("myapp","설정 버튼 눌림");
//        });


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

        //리스트뷰를 이용한 주간캘린더 날짜별 증상 나타내기(정적)
        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;
        //ListView listview = (ListView) view.findViewById(R.id.home_listView) ;
        //listview.setAdapter(adapter) ;


        //Adapter 생성(동적)
        adapter = new HomeListViewAdapter();
        //listView 참조 및 Adapter 연결
        listView = (ListView)view.findViewById(R.id.home_listView);
        //Adapter 지정
        listView.setAdapter(adapter);
        //data의 날짜가 선택된 날짜와 일치하면 adapter에 날짜에 해당하는 데이터 추가


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


        //addItem를 통해 동적으로 ListView 생성됨 -> (증상제목,이미지(리소스아이디),증상정도,양상,악화상황)
        adapter.addItem(Person1.symptom1.getPart(),R.drawable.img_pain_sym1,Person1.symptom1.getPain_level(),Person1.symptom1.getPain_characteristics(),Person1.symptom1.getPain_situation());
        adapter.addItem(Person1.symptom2.getPart(),R.drawable.img_pain_sym2,Person1.symptom2.getPain_level(),Person1.symptom2.getPain_characteristics(),Person1.symptom2.getPain_situation());
        adapter.addItem(Person1.symptom3.getPart(),R.drawable.img_pain_sym3,Person1.symptom3.getPain_level(),Person1.symptom3.getPain_characteristics(),Person1.symptom3.getPain_situation());
        adapter.notifyDataSetChanged();


        //각 날짜를 클릭했을 때 날짜와 일치하는 데이터 불러오기
        wCalender[0].setOnClickListener(v -> { //일요일
            Log.d("myapp","일요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[0].getText().toString();
            //data의 날짜가 선택된 날짜와 일치하면 isSameDate 속성을 true로 변경
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);

        });
        wCalender[1].setOnClickListener(v -> {
            Log.d("myapp","월요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[1].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        wCalender[2].setOnClickListener(v -> {
            Log.d("myapp","화요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[2].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        wCalender[3].setOnClickListener(v -> {
            Log.d("myapp","수요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[3].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        wCalender[4].setOnClickListener(v -> {
            Log.d("myapp","목요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[4].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        wCalender[5].setOnClickListener(v -> {
            Log.d("myapp","금요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[5].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        wCalender[6].setOnClickListener(v -> {
            Log.d("myapp","토요일 눌림");
            //0000.00.00형식의 String 만들기
            String clickedDate = ymTextView.getText().toString().substring(0,4)+"."+ymTextView.getText().toString().substring(6,8);
            clickedDate += "."+wDate[6].getText().toString();
            int sameDatacount = WeekCalendar.setSameDatetoTrue(clickedDate);
            Log.d("myapp"," "+sameDatacount);
        });
        //int sameDatacount = 0일 경우 : 날짜를 선택해 주십시오 or 기록된 통증이 없습니다.

        return view;
    }


    //이 클래스 메소드는 각 캘린더의 날짜를 클릭했을 때
    //각각의 날짜별로 어댑터를 생성하여 생성된 어댑터와 일치하는 데이터의 개수를 넘겨줌
    //public void addItem(String title,int image,int content_painLevel,String content_characteristics,String content_situation){
    static class Adapter {
        static void getAdapterData(HomeListViewAdapter adapter,int sameDatacount){
            Log.d("myapp","getAdapterData 이동");
            Log.d("myapp",Person1.symptom1.isSameDate+" ");
            //for(int i=0;i<sameDatacount;i++){}
            if(Person1.symptom1.isSameDate==true) adapter.addItem(
                    Person1.symptom1.getPart(),R.drawable.img_pain_sym1,
                    Person1.symptom1.getPain_level(),Person1.symptom1.getPain_characteristics(),
                    Person1.symptom1.getPain_situation());
            if(Person1.symptom2.isSameDate==true) adapter.addItem(
                    Person1.symptom2.getPart(),R.drawable.img_pain_sym2,
                    Person1.symptom2.getPain_level(),Person1.symptom2.getPain_characteristics(),
                    Person1.symptom2.getPain_situation());
            if(Person1.symptom3.isSameDate==true) adapter.addItem(
                    Person1.symptom3.getPart(),R.drawable.img_pain_sym3,
                    Person1.symptom3.getPain_level(),Person1.symptom3.getPain_characteristics(),
                    Person1.symptom3.getPain_situation());
            if(Person1.symptom4.isSameDate==true) adapter.addItem(
                    Person1.symptom4.getPart(),R.drawable.img_pain_sym1,
                    Person1.symptom4.getPain_level(),Person1.symptom4.getPain_characteristics(),
                    Person1.symptom4.getPain_situation());

            adapter.notifyDataSetChanged();


        }
    }

    //주간캘린더 관련 작업 모아둔 클래스
    static class WeekCalendar{

        static int setSameDatetoTrue(String date){
            int countSameDate = 0;
            if(Person1.symptom1.getDate().equals(date)){
                Person1.symptom1.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom2.getDate().equals(date)){
                Person1.symptom2.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom3.getDate().equals(date)){
                Person1.symptom3.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom4.getDate().equals(date)){
                Person1.symptom4.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom5.getDate().equals(date)){
                Person1.symptom5.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom6.getDate().equals(date)){
                Person1.symptom6.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom7.getDate().equals(date)){
                Person1.symptom7.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom8.getDate().equals(date)){
                Person1.symptom8.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom9.getDate().equals(date)){
                Person1.symptom9.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom10.getDate().equals(date)){
                Person1.symptom10.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom11.getDate().equals(date)){
                Person1.symptom11.checkSameDate();
                countSameDate++;
            }
            if(Person1.symptom12.getDate().equals(date)){
                Person1.symptom12.checkSameDate();
                countSameDate++;
            }

            return countSameDate;
        }

        @SuppressLint("SetTextI18n")
        void setWeekCalenderDate(View view, Date date,TextView ymTextView,TextView[] wDate){ //주간캘린더 날짜변경 메소드

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






