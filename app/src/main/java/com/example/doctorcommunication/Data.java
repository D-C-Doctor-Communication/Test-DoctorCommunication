package com.example.doctorcommunication;

public class Data{
    String part; //부위
    String pain_level; //통증 정도
    String pain_characteristics; //통증 양상
    String pain_situation; //악화 상황
    String accompany_pain; //동반 증상
    String additional; //추가사항
    public Data(String part,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain){
        this.part = part;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";
    }
    public Data(String part,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain,String additional){
        super();
        this.additional = additional;
    }
}