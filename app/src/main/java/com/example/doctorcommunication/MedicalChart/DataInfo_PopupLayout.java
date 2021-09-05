package com.example.doctorcommunication.MedicalChart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.doctorcommunication.R;

public class DataInfo_PopupLayout extends LinearLayout {


    public DataInfo_PopupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public DataInfo_PopupLayout(Context context) {
        super(context);

        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mc_info_popup_item,this,true);
    }

}