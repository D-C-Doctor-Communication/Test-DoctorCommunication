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

public class MD_Fragment_2 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sym_des_fragment_2, container, false);

        Log.d("mytag","발열 프레그먼트 페이지로 이동함");
        TextView[] textView = new TextView[6];

        textView[0] = view.findViewById(R.id.sym2_1_txt1);
        textView[1] = view.findViewById(R.id.sym2_1_txt2);
        textView[2] = view.findViewById(R.id.sym2_1_txt3);
        textView[3] = view.findViewById(R.id.sym2_1_txt4);
        textView[4] = view.findViewById(R.id.sym2_1_txt5);
        textView[5] = view.findViewById(R.id.sym2_1_txt6);

        //테스트용 데이터 객체 생성
        Data pain1 = new Data("2021-05-29","머리","심한 통증(6)","몸이 무거운","밥 먹은 직후", "어지럼증");

        //데이터에 따라 텍스트 지정
        textView[0].setText(pain1.part);
        textView[1].setText(pain1.pain_level);
        textView[2].setText(pain1.pain_characteristics);
        textView[3].setText(pain1.pain_situation);
        textView[4].setText(pain1.accompany_pain);
        textView[5].setText(pain1.additional);

        return view;
    }
}