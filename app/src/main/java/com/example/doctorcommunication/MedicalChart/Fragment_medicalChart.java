package com.example.doctorcommunication.MedicalChart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import com.example.doctorcommunication.DataManagement.Person1;
import com.example.doctorcommunication.MedicalChart.MCListViewAdapter;
import com.example.doctorcommunication.MedicalChart.MC_DotEventDecorator;
import com.example.doctorcommunication.MedicalChart.MC_PopupActivity;
import com.example.doctorcommunication.R;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


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

    //진료 후기 수정 버튼
    ImageButton MC_editBtn;
    //진료 후기 텍스트 - 입력란
    EditText MC_LineEditText;
    TextView MC_LineTextView;
    //(팝업창 이동) activity 실행 요청 확인을 위한 요청코드
    static final int REQ_ADD_CONTACT = 1;

    //진료 일정 ListView
    ListView listView;
    MCListViewAdapter listViewAdapter;

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
        //병원 일정 추가하기 버튼
        btn_addAppointDoctor = view.findViewById(R.id.btn_addAppointDoctor);
        //진료 일정 ListView
        listView = (ListView) view.findViewById(R.id.MC_listView);
        //Adapter 객체 생성
        listViewAdapter = new MCListViewAdapter();
        //리스트뷰 참조 및 어댑터 지정
        listView.setAdapter(listViewAdapter);

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
        //materialCalendarView.setDateSelected(date,true);

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




        btn_addAppointDoctor.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), MC_PopupActivity.class);
            //startActivity(intent);
            startActivityForResult(intent,REQ_ADD_CONTACT);
        });

        return view;
    }

    //팝업창으로부터 입력받은 정보 저장하는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {

                //일정 이름을 MC_PopupActivity로부터 받아옴
                String scheduleName = intent.getStringExtra("schedule_name");
                //장소를 MC_PopupActivity로부터 받아옴
                String location = intent.getStringExtra("location");
                //선택된 시간(timePicker) MC_PopupActivity로부터 받아옴
                String selectedTime = intent.getStringExtra("selected_time");
                //진료일정인지, 검사일정인지를 MC_PopupActivity로부터 받아옴 (진료일정이라면 "진료"값 저장)
                String typeOfSchedule = intent.getStringExtra("selected_button");
                Log.d("myapp","일정 이름 : "+scheduleName);
                Log.d("myapp","장소 : "+location);
                Log.d("myapp","선택된 시간 : "+selectedTime);
                Log.d("myapp","예약 종류 : "+typeOfSchedule);

                //일정이 생성될때마다 ListViewAdapter에 데이터를 추가함
                //일정의 종류가 진료인지, 검사인지 확인하여 각 값에 맞는 이미지 코드를 add함
                if(typeOfSchedule.equals("검사")){
                    listViewAdapter.addItem(R.drawable.clinic_checkup,scheduleName,location,selectedTime);
                }
                else if(typeOfSchedule.equals("진료")){
                    listViewAdapter.addItem(R.drawable.clinic_clinic,scheduleName,location,selectedTime);
                }

                listViewAdapter.notifyDataSetChanged();
            }
        }
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
            for(int i = 0; i< Person1.memos.length; i++){
                if(Person1.memos[i].getDate().equals(memo_selecteddate)){
                    memoContent = Person1.memos[i].getMemo();
                    Log.d("myapp","메모기록이 존재함!");
                    break;
                }
            }
            if(memoContent.equals("")) memoContent="진료 후기를 작성해주세요.";
            return memoContent;
        }
        //선택한 날짜에 저장된 메모를 찾아 메모 수정함
        public static void changeMemo(String memo_selecteddate, String memo) {
            for(int i=0;i<Person1.memos.length;i++){
                if(Person1.memos[i].getDate().equals(memo_selecteddate)){
                    Person1.memos[i].setMemo(memo);
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
            for(int i=0;i<Person1.appointments.length;i++){
                //CalendarDay day = CalendarDay.from(calendar);
                //병원예약날짜 받아오기
                String[] time = Person1.appointments[i].getDate().split("\\.");
                Log.d("myapp",Person1.appointments[i].getDate());
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);


                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
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