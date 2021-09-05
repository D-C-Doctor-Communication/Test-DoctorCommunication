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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
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
    //버튼 - 증상선택 버튼
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
        //datePicker에 들어갈 시작/끝날짜 calendar 객체
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();



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

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);
        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDateText = findViewById(R.id.startDate);
        endDateText = findViewById(R.id.endDate);


        //증상 선택 버튼
        for (int i = 0; i < buttonKey.length; i++) {
            symptomBtn[i] = findViewById(buttonKey[i]);
        }


// -> 증상 선택 기능
        //버튼은 수정할것임(사용할 수 있는 데이터에 맞추도록)
        //각 증상에 대한 리스트
        ListView dcListView = findViewById(R.id.DC_listView);

        //임시 onClickListener
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
        //기본날짜 오늘로 지정
        setDateText(startDate);
        setDateText(endDate);
        //시작날짜 DatePickerDialog 동작
        startDateText.setOnClickListener(v -> {
            //DatePickerDialog 객체 생성
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        startDateText.setText(PickedDate);
                        startDate.set(year,month+1,dayOfMonth);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    , startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH)
            );
            //DatePickerDialog 표시
            datePickerDialog.show();
        });

        //종료날짜 DatePickerDialog 동작
        endDateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        endDateText.setText(PickedDate);
                        endDate.set(year,month+1,dayOfMonth);
                    }
                    , endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
    }

//db연동필요 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //기록 리스트 생성하는 메소드
    public void createDCList(int index, ListView listView) {
        //listView 참조 및 Adapter 연결
        DCListViewAdapter adapter = new DCListViewAdapter();
        listView.setAdapter(adapter);
        //조건에 맞으면 리스트 생성
        //Person1객체에서 각 증상정보를 가져와 list adapter에 추가함
        for (int i = 0; i < Person1.symptom.length; i++) {
            if (checkIsBetween(Person1.symptom[i].getDate()) && Person1.symptom[i].getPart().equals(buttonValue[index]))
                adapter.addItem(Person1.symptom[i].getDate(),
                        Person1.symptom[i].getPart() + Person1.symptom[i].getPain_level()
                        , Person1.symptom[i].getPart(), Integer.parseInt(Person1.symptom[i].getPain_level()),
                        Person1.symptom[i].getPain_characteristics(), Person1.symptom[i].getPain_situation(),
                        Person1.symptom[i].getAccompany_pain(), Person1.symptom[i].getAdditional());
        }
        adapter.notifyDataSetChanged();
    }

    //날짜 초기값 지정
    public void setDateText(Calendar cal){
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        startDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        endDateText.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        cal.set(mYear,mMonth+1,mDay);
    }

    //날짜 조건 확인 (시작날짜와 끝날짜 사이)
    public boolean checkIsBetween(String date){
        try {
            //string형 날짜를 calendar형으로 변환
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
            Date date_wantCheck = dateFormat.parse(date);
            Calendar checkDate = Calendar.getInstance();
            checkDate.setTime(date_wantCheck);
            //날짜가 범위 사이에 있는지 확인
            return checkDate.compareTo(startDate) >= 0 && checkDate.compareTo(endDate) <= 0;
        }
        catch (ParseException e){
            Log.d("myapp", "예외 발생"+date);
        }
        return false;
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
}
