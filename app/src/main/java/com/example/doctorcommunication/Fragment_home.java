package com.example.doctorcommunication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class Fragment_home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","home탭 열림");
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        //카드 - 증상등록 버튼
        Button btn_addSymptom = (Button)view.findViewById(R.id.btn_addSymptom);
        //카드 - 의사와의 만남 버튼
        Button btn_meetingDoc = (Button)view.findViewById(R.id.btn_meetingDoc);
        //카드 - 녹음하기 버튼
        Button btn_recording = (Button)view.findViewById(R.id.btn_recording);


        btn_addSymptom.setOnClickListener(v -> { //람다형식 사용 ~ new Button.OnClickListener()와 같은 기능
            Intent addSymptom = new Intent(getContext(), AddSymptom.class);
            startActivity(addSymptom);
        });


        btn_meetingDoc.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MeetingDoc.class);
            startActivity(intent);
        });

        //녹음하기 버튼 눌렀을 경우 - 팝업 띄움
        btn_recording.setOnClickListener(v -> {
            final View popupView = getLayoutInflater().inflate(R.layout.fragment_recording, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(popupView);

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        //취소버튼
            Button btnCancel = popupView.findViewById(R.id.no_btn);
            btnCancel.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    alertDialog.dismiss();
                }
            });

        });

        return view;
    }
}
