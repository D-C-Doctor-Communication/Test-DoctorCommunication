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
    TextView textView[] = new TextView[6];
    TextView textView2[] = new TextView[6];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sym_des_fragment_3, container, false);

        Log.d("mytag","두통 프레그먼트 페이지로 이동함");
        textView[0] = view.findViewById(R.id.sym3_1_txt1);
        textView[1] = view.findViewById(R.id.sym3_1_txt2);
        textView[2] = view.findViewById(R.id.sym3_1_txt3);
        textView[3] = view.findViewById(R.id.sym3_1_txt4);
        textView[4] = view.findViewById(R.id.sym3_1_txt5);
        textView[5] = view.findViewById(R.id.sym3_1_txt6);

        textView2[0] = view.findViewById(R.id.sym3_2_txt1);
        textView2[1] = view.findViewById(R.id.sym3_2_txt2);
        textView2[2] = view.findViewById(R.id.sym3_2_txt3);
        textView2[3] = view.findViewById(R.id.sym3_2_txt4);
        textView2[4] = view.findViewById(R.id.sym3_2_txt5);
        textView2[5] = view.findViewById(R.id.sym3_2_txt6);

        //테스트용 데이터 객체 생성
        Data pain1 = new Data("2021-06-01","머리","약한 통증(1)","멍한 느낌의","아침에 일어났을 때", "이명");
        Data pain2 = new Data("2021-06-05","머리","보통 통증(4)","조이는듯한","말을 할 때", "피로감");


        //데이터에 따라 텍스트 지정
        textView[0].setText(pain1.part);
        textView[1].setText(pain1.pain_level);
        textView[2].setText(pain1.pain_characteristics);
        textView[3].setText(pain1.pain_situation);
        textView[4].setText(pain1.accompany_pain);
        textView[5].setText(pain1.additional);

        textView2[0].setText(pain2.part);
        textView2[1].setText(pain2.pain_level);
        textView2[2].setText(pain2.pain_characteristics);
        textView2[3].setText(pain2.pain_situation);
        textView2[4].setText(pain2.accompany_pain);
        textView2[5].setText(pain2.additional);

        return view;
    }
}
