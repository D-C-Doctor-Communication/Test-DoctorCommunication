package com.example.doctorcommunication;

import android.app.Person;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.temporal.ChronoUnit;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


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

    //진료 후기 수정 버튼
    ImageButton MC_editBtn;
    //진료 후기 텍스트 - 입력란
    EditText MC_LineEditText;
    TextView MC_LineTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myapp", "진료기록탭 열림");
        View view = inflater.inflate(R.layout.fragment_medical_chart, container, false);

        AndroidThreeTen.init(getActivity());

        //캘린더
        materialCalendarView = view.findViewById(R.id.calendarView);
        //선택한 날짜
        selectedDate = view.findViewById(R.id.selectedDate);
        //진료 일정 추가하기
        //btn_addAppointDoctor = view.findViewById(R.id.btn_addAppointDoctor);
        //진료 후기 수정 버튼
        MC_editBtn = view.findViewById(R.id.MC_editBtn);
        //진료 후기 텍스트 - 입력란
        MC_LineEditText = view.findViewById(R.id.MC_LineEditText);
        MC_LineTextView = view.findViewById(R.id.MC_LineTextView);
        //증상 기록 보기
        btn_showSymptomData = view.findViewById(R.id.btn_showSymptomData);

        //진료 후기 작성할때 키보드가 UI 가리는것 방지
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //기본 표시 날짜(오늘)
        SimpleDateFormat todayformat = new SimpleDateFormat("yyyy.MM.dd");



        CalendarDay date = CalendarDay.today();
        int basicYear = date.getYear();
        int basicMonth = date.getMonth()+1;
        int basicDay = date.getDay();
        String todayString = basicYear+"."+basicMonth+"."+basicDay;
        Log.d("myapp",basicYear+"."+basicMonth+"."+basicDay);
        monthCalendar.setDateText(basicYear,basicMonth,basicDay,selectedDate);

        //진료 후기 작성
        //선택한 날짜의 memo부분이 빈문자열일경우 "진료 후기를 작성해주세요"로 초기값 지정
        String Memotext1 = monthCalendar.getSameDateMomo(todayString);
        MC_LineTextView.setText(Memotext1);
        MC_LineEditText.setText(Memotext1);

        //수정버튼을 눌렀을때 텍스트뷰가 활성화중이라면 edit으로 변경
        // ,, edit이 활성화중이라면 텍스트뷰로 변경
        //버튼 누르기 전의 텍스트를 임시로 저장하여 setText으로 기본값 지정해야함
        MC_editBtn.setOnClickListener(v -> {
            changeTextEdit(todayString);
        });


        //점 표시
        ArrayList<CalendarDay> dates = monthCalendar.addDot();
        MC_DotEventDecorator dotEventDecorator = new MC_DotEventDecorator(dates);
        materialCalendarView.addDecorator(dotEventDecorator);

        //날짜를 누를 때마다..
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth()+1;
                int Day = date.getDay();

                //선택된 날짜 텍스트 변경 00.00(월)
                monthCalendar.setDateText(Year,Month,Day,selectedDate);
                //0000.00.00형식의 날짜 저장
                String selectedDateString = Year+"."+Month+"."+Day;
                //진료 후기 작성
                //선택한 날짜의 memo부분이 빈문자열일경우 "진료 후기를 작성해주세요"로 초기값 지정
                String memotext = monthCalendar.getSameDateMomo(selectedDateString);
                MC_LineTextView.setText(memotext);
                MC_LineEditText.setText(memotext);

                //수정버튼을 눌렀을때 텍스트뷰가 활성화중이라면 edit으로 변경
                // ,, edit이 활성화중이라면 텍스트뷰로 변경
                //버튼 누르기 전의 텍스트를 임시로 저장하여 setText으로 기본값 지정해야함
                MC_editBtn.setOnClickListener(v -> {
                    changeTextEdit(selectedDateString);
                });
            }
        });


        return view;
    }

    //진료 후기 수정아이콘 눌렀을 때 호출
    public void changeTextEdit(String selectedDateString){
        //텍스트뷰가 활성화중일 때
        if(MC_LineTextView.getVisibility()==View.VISIBLE){
            String temp1 = MC_LineTextView.getText().toString();
            MC_LineTextView.setVisibility(View.INVISIBLE);
            MC_LineEditText.setVisibility(View.VISIBLE);
            MC_LineEditText.setText(temp1);
        }
        //editText가 활성화중일 때
        else if(MC_LineEditText.getVisibility()==View.VISIBLE){
            MC_LineEditText.setVisibility(View.INVISIBLE);
            MC_LineTextView.setVisibility(View.VISIBLE);
            String temp = MC_LineEditText.getText().toString();
            MC_LineTextView.setText(temp);
            monthCalendar.changeMemo(selectedDateString,temp);
        }
    }


    static class monthCalendar{

        //선택한 날짜에 저장된 메모를 찾아 반환함
        static public String getSameDateMomo(String memo_selecteddate){
            String memoContent = "";
            for(int i=0;i<Person1.appointment.length;i++){
                if(Person1.appointment[i].getDate().equals(memo_selecteddate)){
                    memoContent = Person1.appointment[i].getMemo();
                    Log.d("myapp","메모기록이 존재함!");
                    break;
                }
            }
            if(memoContent.equals("")) memoContent="진료 후기를 작성해주세요.";
            return memoContent;
        }
        //선택한 날짜에 저장된 메모를 찾아 메모 수정함
        public static void changeMemo(String memo_selecteddate, String memo) {
            for(int i=0;i<Person1.appointment.length;i++){
                if(Person1.appointment[i].getDate().equals(memo_selecteddate)){
                    Person1.appointment[i].setMemo(memo);
                    Log.d("myapp","메모기록이 수정됨!");
                    break;
                }
            }
        }

        //Person1의 진료데이터가 존재하는지 판별하여
        //해당 날짜에 점을 찍어줌
        public static ArrayList<CalendarDay> addDot(){
            ArrayList<CalendarDay> dates = new ArrayList<>(); //점을 찍을 날짜를 저장,반환

            Calendar calendar = Calendar.getInstance();
            //Data에서 병원예약 날짜가 존재하면 해당 날짜를 위의 arrayList에 저장
            for(int i=0;i<Person1.appointment.length;i++){
                CalendarDay day = CalendarDay.from(calendar);
                //병원예약날짜 받아오기
                String[] time = Person1.appointment[i].getDate().split("\\.");
                Log.d("myapp",Person1.appointment[i].getDate());
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }
            return dates;
        }

        //00.00 (월) 텍스트 표시
        public static void setDateText(int Year, int Month, int Day,TextView selectedDate){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd ");

            CalendarDay date = CalendarDay.from(Year,Month,Day);
            Log.d("myapp","날짜 : "+date.getYear()+'.'+date.getMonth()+"."+date.getDay());

            org.threeten.bp.LocalDate date1 = LocalDate.of(Year, Month, Day);
            DayOfWeek dayOfWeek = date1.getDayOfWeek();
            String dayOfWeekDay = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREA);

            Log.d("myapp",date.toString());
            //nowString = dateFormat.format(date) + dayOfWeekDay;
            selectedDate.setText(date.getMonth()+"."+date.getDay()+"("+dayOfWeekDay+")");
        }

    }

}