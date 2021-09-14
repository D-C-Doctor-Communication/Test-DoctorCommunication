package com.example.doctorcommunication.MedicalChart;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        LinearLayout layout = findViewWithTag(R.layout.mc_info_popup_item);
        TextView textView = layout.findViewById(R.id.title_symp);
        TextView textView2 = layout.findViewById(R.id.part);
        textView.setText("적용됨");
        textView2.setText("적용됨2");
        inflater.inflate(R.layout.mc_info_popup_item,this,true);
    }

}