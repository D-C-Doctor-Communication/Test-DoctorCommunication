package com.example.doctorcommunication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MD_Fragment_3 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sym_des_fragment_2, container, false);

        Log.d("mytag","두통 프레그먼트 페이지로 이동함");
        textView = view.findViewById(R.id.symthom_description_1_text);

        //테스트용 데이터 객체 생성
        Data pain1 = new Data("목","매우 심한 통증(7)","저리고 찌릿찌릿한","물을 마실 때", "체중 감소");
        Data pain2 = new Data("목","심한 통증(6)","저리고 찌릿찌릿한","말을 할 때", "피로감","노란 가래가 나옴");
        Data pain3 = new Data("목","매우 심한 통증(7)","찌르는 듯한","말을 할 때", "체중 감소 / 피로감","잔기침을 많이 함");

        //데이터에 따라 텍스트 지정
        textView.setText(pain1.pain_level);

        return view;
    }
}
