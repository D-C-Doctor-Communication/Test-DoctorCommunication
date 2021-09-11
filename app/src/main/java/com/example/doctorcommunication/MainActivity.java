package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.doctorcommunication.ConditionAnalysis.Fragment_conditionAnalysis;
import com.example.doctorcommunication.DataManagement.Symptom2;
import com.example.doctorcommunication.HomeScreen.Fragment_home;
import com.example.doctorcommunication.MedicalChart.Fragment_medicalChart;
import com.example.doctorcommunication.Settings.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //splash 종료(기본테마 적용)
        setTheme(R.style.Theme_DoctorCommunication);
        firebaseAuth =  FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //splash
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if(insetsController!=null){
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        }
        else{
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
//home,진료기록,상태분석 fragment
        homeFragment = new Fragment_home();
        //android 기본 제공되는 액션바 제거 - 사용자 정의 액션바 사용하기 위함
        getDelegate().getSupportActionBar();
        //메인화면 네비게이션 뷰 생성(activity_main에 정의됨)
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        //기본으로 선택되어있는 프래그먼트 지정
        setDefaultFragment();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //의사와의 만남 -> 메인 -> 상태분석 데이터 이동
        Intent fromDC_intent = getIntent();
        if(fromDC_intent.getIntExtra("fileMovement",0)==1){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,(Fragment)(new Fragment_conditionAnalysis()));
        }


//설정 fragment
        //설정버튼
        ImageButton setting = findViewById(R.id.to_setting);
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            Log.d("myapp","설정 버튼 눌림");
        });


//home,진료기록,상태분석 fragment
        bottomNavigationView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener)(item -> {

            //네비바에서 선택한 아이디가 이미 눌려있는 아이디라면 동작하지 않음
            if (item.getItemId() == bottomNavigationView.getSelectedItemId()) {
                return false;
            } else {
                //네비게이션바에서 선택한 아이디에 따라 프레그먼트 교체
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.nav_home: //홈 프래그먼트
                        transaction.replace(R.id.fragment_container,(Fragment)(new Fragment_home()));
                        Log.d("myapp","home탭 열림");
                        break;
                    case R.id.nav_conditionAnaly: //상태분석 프래그먼트
                        transaction.replace(R.id.fragment_container,(Fragment)(new Fragment_conditionAnalysis()));
                        Log.d("myapp","상태분석탭 열림");
                        break;
                    case R.id.nav_medicalCharts: //진료기록 프래그먼트
                        transaction.replace(R.id.fragment_container,(Fragment)(new Fragment_medicalChart()));
                        Log.d("myapp","진료기록탭 열림");
                        break;
                }
                //프레그먼트 교체 내용 적용
                transaction.commit();
                return true;
            }
        }));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        myRef.child(uid).child("date").child("20210901").child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Symptom2 symptom = snapshot.getValue(Symptom2.class);
                String dd = snapshot.child("symptom").getValue(String.class);
                //Log.d("dd", "part: " + symptom.getSymptom()+dd);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
    //기본으로 선택되어있는 프래그먼트 지정
    public void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, homeFragment);
        transaction.commit();
    }
}