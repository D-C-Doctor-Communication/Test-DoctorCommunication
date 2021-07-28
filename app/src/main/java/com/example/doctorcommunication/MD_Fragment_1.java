package com.example.doctorcommunication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class MD_Fragment_1 extends Fragment {

    TextView[] textView = new TextView[6];
    TextView[] textView2 = new TextView[6];
    TextView[] textView3 = new TextView[6];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sym_des_fragment_1, container, false);

        Log.d("mytag","가래 프레그먼트 페이지로 이동함");
        textView[0] = view.findViewById(R.id.sym1_1_txt1);
        textView[1] = view.findViewById(R.id.sym1_1_txt2);
        textView[2] = view.findViewById(R.id.sym1_1_txt3);
        textView[3] = view.findViewById(R.id.sym1_1_txt4);
        textView[4] = view.findViewById(R.id.sym1_1_txt5);
        textView[5] = view.findViewById(R.id.sym1_1_txt6);

        textView2[0] = view.findViewById(R.id.sym1_2_txt1);
        textView2[1] = view.findViewById(R.id.sym1_2_txt2);
        textView2[2] = view.findViewById(R.id.sym1_2_txt3);
        textView2[3] = view.findViewById(R.id.sym1_2_txt4);
        textView2[4] = view.findViewById(R.id.sym1_2_txt5);
        textView2[5] = view.findViewById(R.id.sym1_2_txt6);

        textView3[0] = view.findViewById(R.id.sym1_3_txt1);
        textView3[1] = view.findViewById(R.id.sym1_3_txt2);
        textView3[2] = view.findViewById(R.id.sym1_3_txt3);
        textView3[3] = view.findViewById(R.id.sym1_3_txt4);
        textView3[4] = view.findViewById(R.id.sym1_3_txt5);
        textView3[5] = view.findViewById(R.id.sym1_3_txt6);

        //테스트용 데이터 객체 생성
        Data pain1 = new Data("2021-05-25","목","매우 심한 통증(7)","저리고 찌릿찌릿한","물을 마실 때", "체중 감소");
        Data pain2 = new Data("2021-05-26","목","심한 통증(6)","저리고 찌릿찌릿한","말을 할 때", "피로감","노란 가래가 나옴");
        Data pain3 = new Data("2021-05-29","목","매우 심한 통증(7)","찌르는 듯한","말을 할 때", "체중 감소 / 피로감","잔기침을 많이 함");

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

        textView3[0].setText(pain3.part);
        textView3[1].setText(pain3.pain_level);
        textView3[2].setText(pain3.pain_characteristics);
        textView3[3].setText(pain3.pain_situation);
        textView3[4].setText(pain3.accompany_pain);
        textView3[5].setText(pain3.additional);

//        String part; //부위
//        String pain_level; //통증 정도
//        String pain_characteristics; //통증 양상
//        String pain_situation; //악화 상황
//        String accompany_pain; //동반 증상
//        String additional; //추가사항
//        String date;

        return view;
    }
}
