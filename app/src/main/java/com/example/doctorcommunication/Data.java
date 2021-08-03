package com.example.doctorcommunication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Data{

    private String date; //Date클래스 형식을 String형으로 변환
    private String part; //부위
    private int pain_level; //통증 정도
    private String pain_characteristics; //통증 양상
    private String pain_situation; //악화 상황
    private String accompany_pain; //동반 증상
    private String additional; //추가사항
    static int number = 0; //추가된 증상 개수
    boolean isSameDate = false; //home listview에서 클릭한 날짜와 일치하는지 확인

    Data(){
    }
    //추가사항 없는 값 생성자
    Data(String date,String part,int pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain){
        this.date = date;
        this.part = part;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";
        number++; //기록된 증상 개수

    }
    //추가사항 있는 값 생성자
    Data(String date,String part,int pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain,String additional){
        this.date = date;
        this.part = part;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = additional;
        number++; //기록된 증상 개수
    }

    //각 값별 get메서드
    String getDate(){ return date; }
    String getPart(){ return part; }
    int getPain_level(){ return pain_level; }
    String getPain_characteristics(){ return pain_characteristics; }
    String getPain_situation(){ return pain_situation; }
    String getAccompany_pain(){ return accompany_pain; }
    String getAdditional(){
        if(additional!=null) return additional;
        return "해당없음";
    }


    //home listview에서 클릭한 날짜와 일치하는지 확인 -> 일치하면 true
    void checkSameDate(){
        this.isSameDate = true;
    }


}

class Person1{
    static Data[] symptom = {
        new Data("2021.08.01","가래",2,"투명하거나 하얀 가래","시간이 갈수록","목통증"),
        new Data("2021.08.01","복통",4,"쿡쿡 찌르듯이","시간이 갈수록","피로","몸살이 난것처럼 몸에 힘이 나지 않았다."),
        new Data("2021.08.02","두통",4,"지끈지끈한","피곤하면","발열"),
        new Data("2021.08.03","복통",1,"더부룩하며 팽창","음식을 섭취하고 난뒤","피로"),
        new Data("2021.08.03","두통",5,"조이는 듯한","기침하면","구토","구토감이 들어 새벽에 잠에서 깼다."),
        new Data("2021.08.04","가래",3,"노란 가래","아침(오전)","기침"),
        new Data("2021.08.04","가래",8,"노란 가래","아침(오전)","목통증"),
        new Data("2021.08.04","복통",6,"날카로운 통증","시간이 갈수록","피로","하루종일 지속되었다."),
        new Data("2021.08.06","복통",2,"쿡쿡 찌르듯이","시간이 갈수록","기침"),
        new Data("2021.08.06","두통",2,"단순 통증","피곤하면","메스꺼움"),
        new Data("2021.08.06","가래",4,"투명하거나 하얀 가래","시간이 갈수록","두통","차가운 물을 마시면 심해진다."),
        new Data("2021.08.06","가래",3,"투명하거나 하얀 가래","시간이 갈수록","피로"),
    };

}
