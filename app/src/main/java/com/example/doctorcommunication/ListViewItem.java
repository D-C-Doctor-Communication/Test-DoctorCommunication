package com.example.doctorcommunication;

import android.widget.ImageView;
import android.widget.TextView;

public class ListViewItem {
    private int record_img;
    private String record_title;
    private String record_content_painLevel;
    private String record_content_characteristics;
    private String record_content_situation;

    public void setImage(int image){
        this.record_img = image;
    }
    public void setTitle(String title){
        this.record_title = title;
    }
    public void setPainLevel(String content_painLevel){
        this.record_title = content_painLevel;
    }
    public void setCharacteristics(String content_characteristics){
        this.record_title = content_characteristics;
    }
    public void setSituation(String content_situation){
        this.record_title = content_situation;
    }

    public int getImage(){
        return this.record_img;
    }
    public String getTitle(){
        return this.record_title;
    }
    public String getPainLevel(){
        return this.record_content_painLevel;
    }
    public String getCharacteristics(){
        return this.record_content_characteristics;
    }
    public String getSituation(){
        return this.record_content_situation;
    }
}
