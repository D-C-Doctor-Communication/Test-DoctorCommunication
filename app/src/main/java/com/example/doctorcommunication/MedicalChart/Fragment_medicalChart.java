package com.example.doctorcommunication.MedicalChart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import com.example.doctorcommunication.DataManagement.Appointment;
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
    //진료 일정이 없습니다 텍스트
    TextView noneDataText;
    //진료 후기 작성하기
    TextView btn_writeReview;
    //진료 후기 수정 버튼
    ImageButton MC_editBtn;
    //진료 후기 텍스트 - 입력란
    EditText MC_LineEditText;
    TextView MC_LineTextView;
    //(팝업창 이동) activity 실행 요청 확인을 위한 요청코드
    static final int REQ_ADD_CONTACT = 1;
    //리스트
    ListView listView;
    MCListViewAdapter listViewAdapter;
    //각 날짜를 클릭했을 때 사용할 어댑터
    MCListViewAdapter adapter = new MCListViewAdapter();
    //기록 데이터 확인 버튼
    Button show_data;

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
        //진료 일정이 없습니다 TextView
        noneDataText = view.findViewById(R.id.noneDataText);
        //기록 데이터 확인 버튼
        show_data = view.findViewById(R.id.show_data);

        //진료 일정 ListView
        listView = (ListView) view.findViewById(R.id.MC_listView);
        //리스트뷰 자동 높이조절 메소드
        setListViewHeight(listView);
        //진료 후기 작성할때 키보드가 UI 가리는것 방지
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //기본 표시 날짜(오늘)
        CalendarDay date = CalendarDay.today();
        int basicYear = date.getYear();
        int basicMonth = date.getMonth()+1;
        int basicDay = date.getDay();
        selectedDateString = basicYear+""+basicMonth+""+basicDay;

        //00.00 (월) 텍스트 지정
        monthCalendar.setDateText(basicYear,basicMonth,basicDay,selectedDate);
        //캘린더 기본 선택된 날짜 지정
        materialCalendarView.setDateSelected(date,true);
        //캘린더 점찍기
        ArrayList<CalendarDay> dates = monthCalendar.addDot();
        MC_DotEventDecorator dotEventDecorator = new MC_DotEventDecorator(dates);
        materialCalendarView.addDecorator(dotEventDecorator);


        //진료 일정 조회 + 리스트 생성
        checkAppointment();
        //진료 후기 작성
        String memotext = monthCalendar.getSameDateMomo(selectedDateString);
        MC_LineTextView.setText(memotext);
        MC_LineEditText.setText(memotext);
        //수정버튼을 눌렀을때 텍스트뷰,에디트뷰 상태에 따라 수정기능 on off
        MC_editBtn.setOnClickListener(v -> {
            changeTextEdit(selectedDateString);
        });


        //캘린더 날짜 변경시 -
        materialCalendarView.setOnDateChangedListener((widget, date1, selected) -> {
            //선택된 날짜 저장
            int Year = date1.getYear();
            int Month = date1.getMonth()+1;
            int Day = date1.getDay();
            //선택된 날짜 텍스트 변경 00.00(월)
            monthCalendar.setDateText(Year,Month,Day,selectedDate);
            //00000000형식의 날짜 저장
            selectedDateString = Year+""+Month+""+Day;

            //어댑터 초기화
            adapter.clearData();
            //진료 일정 조회 + 리스트 생성
            checkAppointment();

            //진료 후기 작성
            String memotext1 = monthCalendar.getSameDateMomo(selectedDateString);
            MC_LineTextView.setText(memotext1);
            MC_LineEditText.setText(memotext1);
            //수정버튼을 눌렀을때 텍스트뷰,에디트뷰 상태에 따라 수정기능 on off
            MC_editBtn.setOnClickListener(v -> {
                changeTextEdit(selectedDateString);
            });
        });

        //데이터 확인 팝업 띄움
        show_data.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),DataInfo_PopupActivity.class);
            startActivity(intent);
        });


        //진료 일정 추가
        btn_addAppointDoctor.setOnClickListener(v->{
            //팝업 호출
            Intent intent = new Intent(getActivity(), MC_PopupActivity.class);
            startActivityForResult(intent,REQ_ADD_CONTACT);
        });

        return view;
    }


    //데이터 관련 메소드
        //진료 일정 조회 + 리스트 생성
        public void checkAppointment(){
            //listview 참조 및 adapter 연결
            MCListViewAdapter listViewAdapter = new MCListViewAdapter();
            listView.setAdapter(listViewAdapter);

            for(int i=0;i<Person1.appointments.length;i++){
                if(Person1.appointments[i].getDate().equals(selectedDateString)){
                    if(Person1.appointments[i].getSort().equals("검사")){
                        listViewAdapter.addItem(R.drawable.clinic_checkup,Person1.appointments[i].getName(),Person1.appointments[i].getLocation(),Person1.appointments[i].getTime());
                        listView.setVisibility(View.VISIBLE);
                        noneDataText.setVisibility(View.INVISIBLE);
                        btn_addAppointDoctor.setBackgroundColor(Color.parseColor("#0f000000"));
                    }
                    else if(Person1.appointments[i].getSort().equals("진료")){
                        listViewAdapter.addItem(R.drawable.clinic_clinic,Person1.appointments[i].getName(),Person1.appointments[i].getLocation(),Person1.appointments[i].getTime());
                        listView.setVisibility(View.VISIBLE);
                        noneDataText.setVisibility(View.INVISIBLE);
                        btn_addAppointDoctor.setBackgroundColor(Color.parseColor("#0f000000"));
                    }
                }
            }
            listViewAdapter.notifyDataSetChanged();
            Log.d("myapp","Adapter added");
        }

        //진료 후기 작성
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
        //listView의 개수에 맞춰 높이조절
        private void setListViewHeight(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, WindowManager.LayoutParams.WRAP_CONTENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }

        //팝업창으로부터 입력받은 정보 저장하는 메소드
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == REQ_ADD_CONTACT) {
                if (resultCode == RESULT_OK) {
                    //일정이 존재하면 listView visible로 바꿈
                    noneDataText.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    //일정 이름을 MC_PopupActivity로부터 받아옴
                    String scheduleName = intent.getStringExtra("schedule_name");
                    //장소를 MC_PopupActivity로부터 받아옴
                    String location = intent.getStringExtra("location");
                    //선택된 시간(timePicker) MC_PopupActivity로부터 받아옴
                    String selectedTime = intent.getStringExtra("selected_time");
                    //진료일정인지, 검사일정인지를 MC_PopupActivity로부터 받아옴 (진료일정이라면 "진료"값 저장)
                    String typeOfSchedule = intent.getStringExtra("selected_button");
                    //일정이 생성될때마다 ListViewAdapter에 데이터를 추가함
                    //일정의 종류가 진료인지, 검사인지 확인하여 각 값에 맞는 이미지 코드를 add함
                    //listview 참조 및 adapter 연결
                    listView.setAdapter(adapter);
                    if(typeOfSchedule.equals("검사")){
                        adapter.addItem(R.drawable.clinic_checkup,scheduleName,location,selectedTime);
                    }
                    else if(typeOfSchedule.equals("진료")){
                        adapter.addItem(R.drawable.clinic_clinic,scheduleName,location,selectedTime);
                    }
                    //일정 생성 객체
                    addToAppointmentData(selectedDateString,scheduleName,location,selectedTime,typeOfSchedule);
                    adapter.notifyDataSetChanged();

                }
            }
        }

    //캘린더 관련 메소드
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
        //캘린더 dot 이벤트
        public static ArrayList<CalendarDay> addDot(){
            ArrayList<CalendarDay> dates = new ArrayList<>(); //점을 찍을 날짜를 저장,반환
            Calendar calendar = Calendar.getInstance();
            //Data에서 병원예약 날짜가 존재하면 해당 날짜를 위의 arrayList에 저장
            for(int i=0;i<Person1.appointments.length;i++){
                //병원예약날짜 받아오기
                String dateValue = Person1.appointments[i].getDate();
                int year = Integer.parseInt(dateValue.substring(0,4));
                int month = Integer.parseInt(dateValue.substring(4,6));
                int dayy = Integer.parseInt(dateValue.substring(6));

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
            org.threeten.bp.LocalDate date1 = LocalDate.of(Year, Month, Day);
            DayOfWeek dayOfWeek = date1.getDayOfWeek();
            String dayOfWeekDay = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREA);
            selectedDate.setText(date.getMonth()+"."+date.getDay()+"("+dayOfWeekDay+")");
        }
    }

    //팝업창을 통해 입력받은 정보로 병원 일정 객체 생성
    public void addToAppointmentData(String date, String scheduleName,String location,String selectedTime,String typeOfSchedule){
        //Person1.appointments에 객체 생성
        Log.d("myapp","일정 생성 완료 => 날짜 : "+date+" 일정이름 : "+scheduleName+" 장소 : "+location+" 시간 : "+selectedTime+" 종류 : "+typeOfSchedule);
    }

}