package com.example.doctorcommunication;

public class Checked_Item {
    boolean checked;
    String ItemString;
    Checked_Item(boolean b, String t){
        checked=b;
        ItemString=t;
    }
    public boolean isChecked(){
        return checked;
    }
}
