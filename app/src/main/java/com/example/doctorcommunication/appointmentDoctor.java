package com.example.doctorcommunication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class appointmentDoctor {
    private String date;

    appointmentDoctor(String date){
        this.date = date;
    }

    String getDate(){
        return date;
    }
}
