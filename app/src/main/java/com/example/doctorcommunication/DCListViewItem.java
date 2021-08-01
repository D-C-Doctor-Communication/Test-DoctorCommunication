package com.example.doctorcommunication;

import android.widget.TextView;

public class DCListViewItem {

    private String dc_list_title_date; //(제목)날짜
    private String dc_list_title_partAndLevel; //(제목)증상이름&정도
    private String dc_list_content_part;//증상이름(부위)
    private String dc_list_content_level;//정도
    private String dc_list_content_characteristics;//양상
    private String dc_list_content_situation;//악화상황
    private String dc_list_content_accompany_pain;//동반증상
    private String dc_list_content_additional;//추가사항


    public String getTitleDate() {
        return dc_list_title_date;
    }
    public String getTitlePartAndLevel() {
        return dc_list_title_partAndLevel;
    }
    public String getPart() {
        return dc_list_content_part;
    }
    public String getLevel() {
        return dc_list_content_level;
    }
    public String getChar() {
        return dc_list_content_characteristics;
    }
    public String getSituation() {
        return dc_list_content_situation;
    }
    public String getAccompany() {
        return dc_list_content_accompany_pain;
    }
    public String getAdditional() {
        return dc_list_content_additional;
    }


    public void setTitleDate(String dc_list_title_date) {
        this.dc_list_title_date = dc_list_title_date;
    }
    public void setTitlePartAndLevel(String dc_list_title_partAndLevel) {
        this.dc_list_title_partAndLevel = dc_list_title_partAndLevel;
    }
    public void setPart(String dc_list_content_part) {
        this.dc_list_content_part = dc_list_content_part;
    }
    public void setLevel(int dc_list_content_level) {
        String resultLevelString="알 수 없음";
        switch (dc_list_content_level){
            case 0: resultLevelString = "통증없음"; break;
            case 1:
            case 2: resultLevelString = "가벼운 통증"; break;
            case 3:
            case 4: resultLevelString = "보통 통증"; break;
            case 5:
            case 6: resultLevelString = "심한 통증"; break;
            case 7:
            case 8: resultLevelString = "매우 심한 통증"; break;
            case 9:
            case 10: resultLevelString = "최악의 고통"; break;
            default: resultLevelString="알 수 없음";
        }

        this.dc_list_content_level = resultLevelString;
    }
    public void setChar(String dc_list_content_characteristics) {
        this.dc_list_content_characteristics = dc_list_content_characteristics;
    }
    public void setSituation(String dc_list_content_situation) {
        this.dc_list_content_situation = dc_list_content_situation;
    }
    public void setAccompany(String dc_list_content_accompany_pain) {
        this.dc_list_content_accompany_pain = dc_list_content_accompany_pain;
    }
    public void setAdditional(String dc_list_content_additional) {
        if(dc_list_content_additional!=null)
        this.dc_list_content_additional = dc_list_content_additional;
        else this.dc_list_content_additional = "해당없음";

    }


}
