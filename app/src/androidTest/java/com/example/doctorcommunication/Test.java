package com.example.doctorcommunication;
/*
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.ObjectInputStream;
import java.time.Year;
import java.util.Calendar;

public class Test extends AppCompatActivity { //구현 후에 DateChangedListener 사용해서 시작 날짜가 끝 날짜보다 뒤에 있지 않도록 ..
    private TextView textView_Date;
    private TextView textView_Date2;
    //private DatePickerDialog.OnDateSetListener callbackMethod;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button btn1 = (Button)findViewById(R.id.button1);
        Button btn2 = (Button)findViewById(R.id.button2);
        textView_Date = (TextView)findViewById(R.id.textView_date1);
        textView_Date2 = (TextView)findViewById(R.id.textView_date2);

        btn1.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(calendar.YEAR);
            int mMonth = calendar.get(calendar.MONTH);
            int mDay = calendar.get(calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textView_Date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        });

        btn2.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(calendar.YEAR);
            int mMonth = calendar.get(calendar.MONTH);
            int mDay = calendar.get(calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textView_Date2.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        });


    }
}

 */
