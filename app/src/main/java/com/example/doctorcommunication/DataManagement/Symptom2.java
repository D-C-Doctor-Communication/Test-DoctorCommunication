package com.example.doctorcommunication.DataManagement;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

//상태등록에서 입력되는 통증정보와 날짜정보에 대한 클래스
public class Symptom2 implements Serializable {
//data
@PropertyName("part")
    private String part; //부위
    @PropertyName("symptom")
    private String symptom; //부위
    @PropertyName("painLevel")
    private String painLevel; //통증 정도
    @PropertyName("pain_characteristics")
    private String pain_characteristics; //통증 양상
    @PropertyName("pain_situation")
    private String pain_situation; //악화 상황
    @PropertyName("accompany_pain")
    private String accompany_pain; //동반 증상
    @PropertyName("additional")
    private String additional; //추가사항

    public Symptom2(){

    }

    //추가사항 없는 값 생성자
    public Symptom2(String part, String painLevel, String pain_characteristics
            , String pain_situation, String accompany_pain){
        this.part = part;
        this.painLevel = painLevel;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";
    }
    //추가사항 있는 값 생성자
    public Symptom2(String part, String painLevel, String pain_characteristics
            , String pain_situation, String accompany_pain, String additional){
        this.part = part;
        this.painLevel = painLevel;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = additional;
    }

    public String getPart(){ return part; }
    public String getSymptom(){ return symptom; }
    public String getPainLevel(){ return painLevel; }
    public String getPain_characteristics(){ return pain_characteristics; }
    public String getPain_situation(){ return pain_situation; }
    public String getAccompany_pain(){ return accompany_pain; }
    public String getAdditional(){
        if(additional!=null) return additional;
        return "해당없음";
    }


}