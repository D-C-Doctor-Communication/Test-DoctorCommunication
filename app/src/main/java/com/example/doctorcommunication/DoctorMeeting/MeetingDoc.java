package com.example.doctorcommunication.DoctorMeeting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doctorcommunication.DataManagement.Person1;
import com.example.doctorcommunication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private TextView gotoGraph;
    //증상선택 버튼 키값
    private final int[] buttonKey = {R.id.btn_1_symptom,R.id.btn_2_symptom,R.id.btn_3_symptom,R.id.btn_4_symptom,R.id.btn_5_symptom
            ,R.id.btn_6_symptom,R.id.btn_7_symptom,R.id.btn_8_symptom,R.id.btn_9_symptom,R.id.btn_10_symptom
            ,R.id.btn_11_symptom,R.id.btn_12_symptom,R.id.btn_13_symptom,R.id.btn_14_symptom,R.id.btn_15_symptom
            ,R.id.btn_16_symptom,R.id.btn_17_symptom,R.id.btn_18_symptom,R.id.btn_19_symptom,R.id.btn_20_symptom};
    //버튼 - 증상선택 버튼
    private final Button[] symptomBtn = new Button[buttonKey.length];
    //선택된 버튼 인덱스
    private int selectedButtonIdx=0;
    //증상선택 버튼 내용
    private final String[] buttonValue = {"복통", "두통", "요통","손목 통증","흉통","무릎 통증","속 쓰림","팔꿈치 통증","엉덩이 통증","발열","기침","인후통","콧물","귀 통증","이명","피로","호흡곤란","떨림","소화불량","발목 통증"};
    //기간선택 (시작날짜/끝날짜)
    private Calendar startDate, endDate;

    //증상기록 리스트
    ExpandableListView expandableListView;
    CustomAdapter adapter;
    ArrayList<ParentData> groupListDatas;
    ArrayList<ArrayList<ContentData>> childListDatas;

    //증상 없을경우 이미지
    ImageView notice_noData;
    //증상버튼 선택 안했을 경우 invisible
    RelativeLayout selectedDataLayout;

    //파이어베이스
    static FirebaseAuth firebaseAuth;
    String fire_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp", "의사와의 만남탭 열림");

        //기본 세팅
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 각 프레그먼트들로 이동하기 위한 객체 생성
        //툴바 구성 - 이전버튼, 녹음버튼있
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.dc_actionbar);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_btn);
        //증상 선택 버튼
        for (int i = 0; i < buttonKey.length; i++) {
            symptomBtn[i] = findViewById(buttonKey[i]);
        }
        //datePicker에 들어갈 시작/끝날짜 calendar 객체
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        //증상기록 리스트 뷰 연결
        expandableListView = findViewById(R.id.DC_listview);
        //데이터 없을경우
        notice_noData = findViewById(R.id.notice_noData);
        selectedDataLayout = findViewById(R.id.selectedDataLayout);



        //증상 심각도 팝업 띄우기
        gotoGraph = findViewById(R.id.btn_gotoGraph);
        gotoGraph.setOnClickListener(v -> {
            String graphDate = startDate.get(Calendar.YEAR)+""+startDate.get(Calendar.MONTH);
            //팝업 띄우기
            GraphDialog graphDialog = new GraphDialog(MeetingDoc.this,graphDate,buttonValue[selectedButtonIdx]);
            graphDialog.setCancelable(true);
            graphDialog.setCanceledOnTouchOutside(true);
            graphDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            graphDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            graphDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            graphDialog.show();
        });

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);
        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDateText = findViewById(R.id.startDate);
        endDateText = findViewById(R.id.endDate);


// -> 날짜 선택 기능
        //기본날짜 오늘로 지정
        setDateText(startDate);
        setDateText(endDate);
        //시작날짜 DatePickerDialog 동작
        startDateText.setOnClickListener(v -> {
            //DatePickerDialog 객체 생성
            //R.style.MyDatePickerStyle,
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        startDateText.setText(PickedDate);
                        startDate.set(year,month+1,dayOfMonth);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    , startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH)-1, startDate.get(Calendar.DAY_OF_MONTH)
            );
            //오늘 이후 비활성화
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            //DatePickerDialog 표시
            datePickerDialog.show();
        });

        //종료날짜 DatePickerDialog 동작
        endDateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        endDateText.setText(PickedDate);
                        endDate.set(year,month+1,dayOfMonth);
                    }
                    , endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)-1, endDate.get(Calendar.DAY_OF_MONTH)
            );
            //오늘 이후 비활성화
            Calendar c = Calendar.getInstance();
            c.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH)-1,startDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            //DatePickerDialog 표시
            datePickerDialog.show();
        });
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

        //시작 / 끝 날짜
        int start = startDate.get(Calendar.YEAR)*10000+startDate.get(Calendar.MONTH)*100+startDate.get(Calendar.DAY_OF_MONTH);
        int end = endDate.get(Calendar.YEAR)*10000+endDate.get(Calendar.MONTH)*100+endDate.get(Calendar.DAY_OF_MONTH);

        if(Integer.parseInt(date)>=start && Integer.parseInt(date)<=end) return true;

        return false;
    }

// -> toolbar 기능 (뒤로가기 버튼)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //db연동필요 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //리스트 데이터 설정
    private void setData(int valudIdx){
        Log.d("myapp","넘어옴");
        //부모,자식 데이터 arraylist
        groupListDatas = new ArrayList<ParentData>();
        childListDatas = new ArrayList<ArrayList<ContentData>>();
        int sizeList = 0;
        //누른 버튼의 값 (증상 선택)
        String selectedSymptom = buttonValue[valudIdx];
        //파이어베이스
        firebaseAuth =  FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        //선택된 증상 데이터 선별
        for(int i=0;i< Person1.symptom.length;i++) {
            if (Person1.symptom[i].getSymptom_name().equals(selectedSymptom)&&checkIsBetween(Person1.symptom[i].getDate())) {
                Log.d("myapp","통과됨");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                String init = Person1.symptom[i].getDate();
                String dateStr = init.substring(0,4)+"."+init.substring(4,6)+"."+init.substring(6);
                String yoil = "";
                try {
                    Date date = sdf.parse(Person1.symptom[i].getDate());
                    calendar.setTime(date);
                    yoil = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.KOREAN);
                } catch (ParseException e) {
                    Log.d("myapp","예외발생");
                    e.printStackTrace();
                }
                //date
                //symp_name
                //pain_level
                groupListDatas.add(new ParentData(
                        dateStr + " ("+yoil+")",
                        Person1.symptom[i].getPart(),
                        Person1.symptom[i].getPain_level())
                );
                //list_part
                //list_painLevel
                //list_characteristics
                //list_situation
                //list_accompany_pain
                //list_additional
                childListDatas.add(new ArrayList<ContentData>());
                childListDatas.get(sizeList).add(new ContentData(
                        Person1.symptom[i].getPart(),
                        Person1.symptom[i].getPain_level(),
                        Person1.symptom[i].getPain_characteristics(),
                        Person1.symptom[i].getPain_situation(),
                        Person1.symptom[i].getAccompany_pain(),
                        Person1.symptom[i].getAdditional())
                );
                sizeList++;
            }
        }
        if(groupListDatas.size()==0) notice_noData.setVisibility(View.VISIBLE);
        else notice_noData.setVisibility(View.GONE);
    }


    //증상별로 데이터 넘겨주기
    public void sympOnClick(View view){
        for(int i=0;i<buttonKey.length;i++){
            //레이아웃 변경
            selectedDataLayout.setVisibility(View.VISIBLE);
            symptomBtn[i].setBackgroundResource(R.drawable.dc_button_nonclicked);
            int color_black = ContextCompat.getColor(getApplicationContext(),R.color.black);
            symptomBtn[i].setTextColor(color_black);
            if(view.getId()==buttonKey[i]){
                //레이아웃 변경
                int color_white = ContextCompat.getColor(getApplicationContext(),R.color.white);
                symptomBtn[i].setTextColor(color_white);
                symptomBtn[i].setBackgroundResource(R.drawable.dc_button_clicked);
                Log.d("myapp", buttonValue[i] + " 버튼 눌림");
                //텍스트 지정
                symptom_title.setText(buttonValue[i]);
                //선택한 증상에 맞는 데이터 처리 (리스트 데이터 준비)
                setData(i);
                adapter = new CustomAdapter(this,groupListDatas,childListDatas);
                //리스트 생성
                expandableListView.setAdapter(adapter);
                selectedButtonIdx = i;
            }
        }

    }
}
