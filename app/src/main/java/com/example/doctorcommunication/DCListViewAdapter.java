package com.example.doctorcommunication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DCListViewAdapter extends BaseAdapter {
    private TextView dc_list_title_date; //(제목)날짜
    private TextView dc_list_title_partAndLevel; //(제목)증상이름&정도
    private TextView dc_list_content_part;//증상이름(부위)
    private TextView dc_list_content_level;//정도
    private TextView dc_list_content_characteristics;//양상
    private TextView dc_list_content_situation;//악화상황
    private TextView dc_list_content_accompany_pain;//동반증상
    private TextView dc_list_content_additional;//추가사항

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
