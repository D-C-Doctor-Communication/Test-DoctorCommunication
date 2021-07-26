package com.example.doctorcommunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Fragment Fragment_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android 기본 제공되는 액션바 제거 - 사용자 정의 액션바 사용하기 위함
        getDelegate().getSupportActionBar();
        //메인화면 네비게이션 뷰 생성(activity_main에 정의됨)
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        //기본으로 선택되어있는 프래그먼트 지정
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
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


    }
}