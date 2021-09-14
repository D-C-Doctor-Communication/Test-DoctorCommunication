package com.example.doctorcommunication.DoctorMeeting;

import android.util.Log;

public class ContentData {
    //parent - 날짜,증상명,통증정도()
    //child - 부위,통증정도,통증양상,악화상황,동반증상,추가사항
    private String part;
    private String pain_level;
    private String situation;
    private String characteristics;
    private String accompany_symp;
    private String additional;


    public ContentData(String part,String pain_level,String situation,String characteristics,String accompany_symp,String additional){
        Log.d("myapp","Content 들어감");
        this.part = part;
        this.pain_level = pain_level;
        this.situation = situation;
        this.characteristics = characteristics;
        if(accompany_symp.equals("e")){
            this.accompany_symp = "해당 없음";
        }
        else{
            this.accompany_symp = accompany_symp;
        }
        if(additional.equals("e")){
            this.additional = "해당 없음";
        }
        else{
            this.additional = additional;
        }


    }

    public String getCharacteristics(){ return characteristics; }

    public String getPart() {
        return part;
    }

    public String getPain_level() {

        String value="";
        switch(Integer.parseInt(pain_level)){
            case 0: value = "통증이 없음 (0)";
            case 1: value = "괜찮은 통증 (1)";
            case 2: value = "조금 아픈 통증 (2)";
            case 3: value = "웬만한 통증 (3)";
            case 4: value = "괴로운 통증 (4)";
            case 5: value = "매우 괴로운 통증 (5)";
            case 6: value = "극심한 통증 (6)";
            case 7: value = "매우 극심한 통증 (6)";
            case 8: value = "끔찍한 통증 (6)";
            case 9: value = "참을 수 없는 통증 (6)";
            case 10: value = "상상할 수 없는 통증 (6)";
        }
        return value;
    }

    public String getSituation() {
        return situation;
    }

    public String getAccompany_symp() {
        return accompany_symp;
    }

    public String getAdditional() {
        return additional;
    }
}
