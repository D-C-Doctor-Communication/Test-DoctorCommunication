package com.example.doctorcommunication.DoctorMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doctorcommunication.DataManagement.Person1;
import com.example.doctorcommunication.ConditionAnalysis.Fragment_conditionAnalysis;
import com.example.doctorcommunication.MainActivity;
import com.example.doctorcommunication.R;
import com.example.doctorcommunication.Recording.Recording;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MeetingDoc extends AppCompatActivity {

    //기간선택 - 시작 날짜를 표시할 TextView
    private TextView startDateText;
    //기간선택 - 종료 날짜를 표시할 TextView
    private TextView endDateText;
    //날짜선택 버튼 위 증상 텍스트
    private TextView symptom_title;

    //버튼 - 심각도 그래프로 이동하는 버튼
    private Button gotoGraph;
    //증상선택 버튼
    private final Button[] symptomBtn = new Button[3]; //증상 개수(임시 3)
    //button 키값
    private final int[] buttonKey = {R.id.btn_1_symptom, R.id.btn_2_symptom, R.id.btn_3_symptom};
    private final String[] buttonValue = {"두통", "복통", "요통"};
    //기간선택 (시작날짜/끝날짜)
    private Calendar startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp", "의사와의 만남탭 열림");

        //기본 세팅
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 각 프레그먼트들로 이동하기 위한 객체 생성
        //툴바 구성 - 이전버튼, 녹음버튼있
        Toolbar toolbar = findViewById(R.id.toolbar_meeting_doctor); //마이크, 이전버튼 들어있는 toolbar 생성
        setSupportActionBar(toolbar);


        // 증상에 대한 심각도 그래프로 이동하는 버튼
        gotoGraph = findViewById(R.id.btn_gotoGraph);
        gotoGraph.setOnClickListener(v -> {
            Log.d("mytag", "그래프 이동 버튼 눌림");
            //심각도 그래프 버튼을 누르면 상태분석 페이지 프래그먼트를 불러옴
            FragmentTransaction gotoGraph = getSupportFragmentManager().beginTransaction();
            Fragment_conditionAnalysis fragment = new Fragment_conditionAnalysis();
            //FrameLayout을 사용하였기 때문에 겹쳐보이지 않도록 의사와의 만남 페이지 unvisible로 설정
            FrameLayout doc_frame = findViewById(R.id.meeting_doctor_Frame);
            doc_frame.setVisibility(View.INVISIBLE);
            //프레그먼트를 프레임과 교체하여 띄움
            gotoGraph.replace(R.id.meeting_doctor_Frame, fragment); //meeting_doctor_Frame : meeting_doctor 최상위 레이아웃
            gotoGraph.commit();
        });

        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDateText = (TextView) findViewById(R.id.startDate);
        endDateText = (TextView) findViewById(R.id.endDate);

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);
        //증상 선택 버튼
        for (int i = 0; i < buttonKey.length; i++) {
            symptomBtn[i] = findViewById(buttonKey[i]);
        }


// -> 증상 선택 기능
        //각 증상에 대한 리스트
        ListView dcListView = findViewById(R.id.DC_listView);


        symptomBtn[0].setOnClickListener(v -> {
            Log.d("myapp", buttonValue[0] + " 버튼 눌림");
            symptom_title.setText(buttonValue[0]);
            createDCList(0, dcListView);
        });

        symptomBtn[1].setOnClickListener(v -> {
            Log.d("myapp", buttonValue[1] + " 버튼 눌림");
            symptom_title.setText(buttonValue[1]);
            createDCList(1, dcListView);
        });

        symptomBtn[2].setOnClickListener(v -> {
            Log.d("myapp", buttonValue[2] + " 버튼 눌림");
            symptom_title.setText(buttonValue[2]);
            createDCList(2, dcListView);
        });

// -> 날짜 선택 기능

        //기본 날짜 텍스트 오늘로 설정
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        startDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        endDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);

        //시작날짜 DatePickerDialog 동작
        startDateText.setOnClickListener(v -> {

            //DatePickerDialog 객체 생성
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        startDateText.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    , mYear, mMonth, mDay
            );
            //DatePickerDialog 표시
            datePickerDialog.show();

        });

        //종료날짜 DatePickerDialog 동작
        endDateText.setOnClickListener(v -> {

            //DatePickerDialog 불러옴
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 endDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        endDateText.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    , mYear, mMonth, mDay
            );
            //DatePickerDialog 표시
            datePickerDialog.show();
        });
    }


    // -> toolbar 기능
    @Override
    //toolbar와 메뉴 구성 layout 연결
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.meeting_doc_toolbar_menu, menu);
        return true;
    }

    @Override
    //toolbar의 메뉴에 따라 각 경우의 동작 지정
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //이전버튼
            case R.id.backToHome:
                Intent backToHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backToHome);
                //마이크버튼(녹음하기)
            case R.id.record:
                Intent record = new Intent(getApplicationContext(), Recording.class);
                startActivity(record);
        }
        return true;
    }

    //클릭한 버튼의 증상명과 데이터의 증상이 일치한지 확인
    public boolean checkSymptom(int index, String part) {
        //버튼별 증상 배열
        String[] btnString = new String[]{"가래", "복통", "두통"};
        if (part.equals(btnString[index])) return true;
        return false;
    }

    //텍스트 지정
    //public void setCalendarDate(Calendar calendarDate,TextView textView){

    //}


    //기록 리스트 생성하는 메소드
    public void createDCList(int index, ListView listView) {
        //listView 참조 및 Adapter 연결
        DCListViewAdapter adapter = new DCListViewAdapter();
        //Adapter 지정
        listView.setAdapter(adapter);
        //데이터의 날짜가 datePicker의 두 날짜 사이에있으면 true 반환
        for (int i = 0; i < Person1.symptom.length; i++) {
            if (checkIsBetween(Person1.symptom[i].getDate()) && checkSymptom(index, Person1.symptom[i].getPart()))
                adapter.addItem(Person1.symptom[i].getDate(),
                        Person1.symptom[i].getPart() + Person1.symptom[i].getPain_level()
                        , Person1.symptom[i].getPart(), Integer.parseInt(Person1.symptom[i].getPain_level()),
                        Person1.symptom[i].getPain_characteristics(), Person1.symptom[i].getPain_situation(),
                        Person1.symptom[i].getAccompany_pain(), Person1.symptom[i].getAdditional());
        }

        adapter.notifyDataSetChanged();
    }


    //데이터의 날짜가 datePicker의 두 날짜 사이에있으면 true 반환
    public boolean checkIsBetween(String date) {
        try {
            //startDate,endDate : DatePicker에서 선택한 시작/종료 날짜
            //wantCheck : 증상이 기록된 날짜
            String start = startDateText.getText().toString();
            String end = endDateText.getText().toString();

            Calendar StartDay, EndDay, CheckDate; //Date객체형으로 바꾼 시작 / 끝 날짜
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //형변환을 위한 데이트포멧 객체 생성

            Date date_start = dateFormat.parse(start); //Date형으로 변환
            Date date_end = dateFormat.parse(end);
            Date date_wantCheck = dateFormat.parse(date);

            StartDay = Calendar.getInstance(); //Calender형으로 변환
            StartDay.setTime(date_start);
            EndDay = Calendar.getInstance();
            EndDay.setTime(date_end);
            CheckDate = Calendar.getInstance();
            CheckDate.setTime(date_wantCheck);

            if ((CheckDate.before(EndDay) || CheckDate.equals(EndDay)) && !CheckDate.before(StartDay)) {
                Log.d("myapp", "통과됨");
                return true;
            }
            Log.d("myapp", "통과되지 않음");
            return false;
        } catch (Exception e) {
            Log.d("myapp", "예외 발생");
            return false;
        }
    }
}
