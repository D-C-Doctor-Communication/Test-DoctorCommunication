package com.example.doctorcommunication;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;



public class MeetingDoc extends AppCompatActivity {

    //리스트뷰 사용을 위한 리스트뷰와 어댑터
    private ListView dcListView;
    private DCListViewAdapter dcListViewAdapter;


    private TextView startDate; //기간선택 - 시작 날짜를 표시할 TextView
    private TextView endDate; //기간선택 - 종료 날짜를 표시할 TextView


    private Button gotoGraph; // 심각도 그래프로 이동하는 버튼

    //날짜선택 버튼 위 증상 텍스트
    TextView symptom_title;
    //증상선택 버튼
    Button btn_1_symptom;
    Button btn_2_symptom;
    Button btn_3_symptom;
    //리스트 레이아웃 / 버튼 클릭시 사라질 "선택해주세요"레이아웃
    RelativeLayout symthom_description_notSelected;
    ListView DC_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp","의사와의 만남탭 열림");
//        Log.d("myapp",Person1.symptom1.getDate());


//기본 세팅
//액션바 사라짐 -> ?
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 각 프레그먼트들로 이동하기 위한 객체 생성
        //이전버튼, 녹음버튼있는 툴바(액션바 기능)
        Toolbar toolbar = findViewById(R.id.toolbar_meeting_doctor); //마이크, 이전버튼 들어있는 toolbar 생성
        setSupportActionBar(toolbar); //toolbar를 액션바로서 지정

        // 증상에 대한 심각도 그래프로 이동하는 버튼
        gotoGraph = findViewById(R.id.btn_gotoGraph);
        gotoGraph.setOnClickListener(v->{
            Log.d("mytag","그래프 이동 버튼 눌림");
           //심각도 그래프 버튼을 누르면 상태분석 페이지 프래그먼트를 불러옴
            FragmentTransaction gotoGraph = getSupportFragmentManager().beginTransaction();
            Fragment_conditionAnalysis fragment = new Fragment_conditionAnalysis();
            //FrameLayout을 사용하였기 때문에 겹쳐보이지 않도록 meeting_doctor_Frame를 unvisible로 설정
            FrameLayout doc_frame = findViewById(R.id.meeting_doctor_Frame);
            doc_frame.setVisibility(View.INVISIBLE);
            //프레그먼트를 프레임과 교체하여 띄움
            gotoGraph.replace(R.id.meeting_doctor_Frame,fragment); //meeting_doctor_Frame : meeting_doctor 최상위 레이아웃
            gotoGraph.commit();
        });

        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDate = (TextView)findViewById(R.id.startDate);//기간선택 - 시작 날짜를 표시할 TextView
        endDate = (TextView)findViewById(R.id.endDate);//기간선택 - 종료 날짜를 표시할 TextView

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);

        //각 증상 선택 버튼을 눌렀을때 누른 버튼의 아이디값을 받아옴
        btn_1_symptom = findViewById(R.id.btn_1_symptom);
        btn_2_symptom = findViewById(R.id.btn_2_symptom);
        btn_3_symptom = findViewById(R.id.btn_3_symptom);

        //리스트 레이아웃 / 버튼 클릭시 사라질 "선택해주세요"레이아웃
        //symthom_description_notSelected = findViewById(R.id.symthom_description_notSelected);
        DC_listView = findViewById(R.id.DC_listView);

// -> 증상 선택 기능

        btn_1_symptom.setOnClickListener(v -> {
            Log.d("myapp","가래 버튼 눌림");
            symptom_title.setText("가래");
//            DC_listView.setVisibility(View.VISIBLE);
//            symthom_description_notSelected.setVisibility(View.INVISIBLE);
        });

        btn_2_symptom.setOnClickListener(v -> {
            Log.d("myapp","발열 버튼 눌림");
            symptom_title.setText("발열");
//            DC_listView.setVisibility(View.VISIBLE);
//            symthom_description_notSelected.setVisibility(View.INVISIBLE);
        });

        btn_3_symptom.setOnClickListener(v -> {
            Log.d("myapp","두통 버튼 눌림");
            symptom_title.setText("두통");
//            DC_listView.setVisibility(View.VISIBLE);
//            symthom_description_notSelected.setVisibility(View.INVISIBLE);
        });

// -> 날짜 선택 기능

        //시작날짜 텍스트를 누르면 DatePickerDialog 동작
        startDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance(); //Calendar 객체 생성
            int mYear = calendar.get(Calendar.YEAR); //기본으로 선택된 날짜의 "년도" 지정 (지금은 현재로 지정됨)
            int mMonth = calendar.get(Calendar.MONTH); //기본으로 선택된 날짜의 "월" 지정 (지금은 현재로 지정됨)
            int mDay = calendar.get(Calendar.DAY_OF_MONTH); //기본으로 선택된 날짜의 "일" 지정 (지금은 현재로 지정됨)

            //DatePickerDialog 불러옴
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) ->{
                        startDate.setText(year + "." + (month + 1) + "." + dayOfMonth);

                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,mYear,mMonth,mDay
            );
            //DatePickerDialog 표시
            datePickerDialog.show();

        });

        //종료날짜 텍스트를 누르면 DatePickerDialog 동작 (위의 startDate방식과 동일함)
        endDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance(); //Calendar 객체 생성
            int mYear = calendar.get(Calendar.YEAR); //기본으로 선택된 날짜의 "년도" 지정 (지금은 현재로 지정됨)
            int mMonth = calendar.get(Calendar.MONTH); //기본으로 선택된 날짜의 "월" 지정 (지금은 현재로 지정됨)
            int mDay = calendar.get(Calendar.DAY_OF_MONTH); //기본으로 선택된 날짜의 "일" 지정 (지금은 현재로 지정됨)

            //DatePickerDialog 불러옴
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> endDate.setText(year + "." + (month + 1) + "." + dayOfMonth)
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,mYear,mMonth,mDay
            );
            //DatePickerDialog 표시
            datePickerDialog.show();
        });


// -> List View 기능
        //Adapter 생성
        dcListViewAdapter = new DCListViewAdapter();
        //ListView 참조 및 어댑터 연결
        dcListView = (ListView)findViewById(R.id.DC_listView);
        dcListView.setAdapter(dcListViewAdapter);

        //addItem시 데이터 삽입 순서
        //dc_list_title_date
        //dc_list_title_partAndLevel
        //dc_list_content_part
        //dc_list_content_level
        //dc_list_content_characteristics
        //dc_list_content_situation
        //dc_list_content_accompany_pain
        //dc_list_content_additional
        //if(dcListViewAdapter.getCount()!=0) dcListView.setVisibility(View.VISIBLE);
        Log.d("myapp","adapter addItem 전");
        dcListViewAdapter.addItem(Person1.symptom1.getDate(),
                Person1.symptom1.getPart()+Person1.symptom1.getPain_level()
                ,Person1.symptom1.getPart(),Person1.symptom1.getPain_level(),
                Person1.symptom1.getPain_characteristics(),Person1.symptom1.getPain_situation(),
                Person1.symptom1.getAccompany_pain(),Person1.symptom1.getAdditional());
        dcListViewAdapter.addItem(Person1.symptom2.getDate(),
                Person1.symptom2.getPart()+Person1.symptom2.getPain_level()
                ,Person1.symptom2.getPart(),Person1.symptom2.getPain_level(),
                Person1.symptom2.getPain_characteristics(),Person1.symptom2.getPain_situation(),
                Person1.symptom2.getAccompany_pain(),Person1.symptom2.getAdditional());
        dcListViewAdapter.addItem(Person1.symptom3.getDate(),
                Person1.symptom3.getPart()+Person1.symptom3.getPain_level()
                ,Person1.symptom3.getPart(),Person1.symptom3.getPain_level(),
                Person1.symptom3.getPain_characteristics(),Person1.symptom3.getPain_situation(),
                Person1.symptom3.getAccompany_pain(),Person1.symptom3.getAdditional());
        Log.d("myapp","dc Adapter added");
        dcListViewAdapter.notifyDataSetChanged();
        Log.d("myapp","adapter addItem 후");


    }


// -> toolbar 기능
    @Override
    //toolbar와 메뉴 구성 layout 연결
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.meeting_doc_toolbar_menu,menu);
        return true;
    }
    @Override
    //toolbar의 메뉴에 따라 각 경우의 동작 지정
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //이전버튼
            case R.id.backToHome:
                Intent backToHome = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(backToHome);
            //마이크버튼(녹음하기)
            case R.id.record:
                Intent record = new Intent(getApplicationContext(),Recording.class);
                startActivity(record);
        }
        return true;
    }

}