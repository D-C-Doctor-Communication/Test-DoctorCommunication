package com.example.doctorcommunication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class appointmentDoctor {
    private String date;
    private String memo;

    appointmentDoctor(String date,String memo){
        this.date = date;
        this.memo = memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    String getDate(){
        return date;
    }
    String getMemo(){ return memo; }
}
