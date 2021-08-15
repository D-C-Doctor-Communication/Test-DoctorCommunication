package com.example.doctorcommunication.DoctorMeeting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doctorcommunication.R;

import java.util.ArrayList;

public class DCListViewAdapter extends BaseAdapter {
    private TextView dc_list_title_date; //(제목)날짜
    private TextView dc_list_title_partAndLevel; //(제목)증상이름&정도
    private TextView dc_list_content_part;//증상이름(부위)
    private TextView dc_list_content_level;//정도
    private TextView dc_list_content_characteristics;//양상
    private TextView dc_list_content_situation;//악화상황
    private TextView dc_list_content_accompany_pain;//동반증상
    private TextView dc_list_content_additional;//추가사항

    private ArrayList<DCListViewItem> listViewItemArrayDCList = new ArrayList<DCListViewItem>();

    //DCListViewAdapter 기본 생성자
    public DCListViewAdapter(){}

    //Adapter에 사용되는 데이터의 개수
    @Override
    public int getCount() {
        return listViewItemArrayDCList.size();
    }

    //postion에 위치한 데이터를 화면에 출력하는 뷰를 반환
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //"dc_listview_item" 레이아웃을 inflate해서 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dc_listview_item,parent,false);
        }
        //화면에 표시될 뷰에서(Layout이 inflate된 뷰) 위젯에 대한 접근권을 얻음
        dc_list_title_date = convertView.findViewById(R.id.dc_list_title_date);
        dc_list_title_partAndLevel = convertView.findViewById(R.id.dc_list_title_partAndLevel);
        dc_list_content_part = convertView.findViewById(R.id.dc_list_content_part);
        dc_list_content_level = convertView.findViewById(R.id.dc_list_content_level);
        dc_list_content_characteristics = convertView.findViewById(R.id.dc_list_content_characteristics);
        dc_list_content_situation = convertView.findViewById(R.id.dc_list_content_situation);
        dc_list_content_accompany_pain = convertView.findViewById(R.id.dc_list_content_accompany_pain);
        dc_list_content_additional = convertView.findViewById(R.id.dc_list_content_additional);

        DCListViewItem dcListViewItem = listViewItemArrayDCList.get(pos);

        dc_list_title_date.setText(dcListViewItem.getTitleDate());
        dc_list_title_partAndLevel.setText(dcListViewItem.getTitlePartAndLevel());
        dc_list_content_part.setText(dcListViewItem.getPart());
        dc_list_content_level.setText(dcListViewItem.getLevel());
        dc_list_content_characteristics.setText(dcListViewItem.getChar());
        dc_list_content_situation.setText(dcListViewItem.getSituation());
        dc_list_content_accompany_pain.setText(dcListViewItem.getAccompany());
        dc_list_content_additional.setText(dcListViewItem.getAdditional());



        return convertView;
    }




    //지정한 위치에 있는 아이템 반환
    @Override
    public Object getItem(int position) {
        return listViewItemArrayDCList.get(position);
    }
    //지정한 위치와 관련있는 아이템의 Id값 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    //dc_list_title_date
    //dc_list_title_partAndLevel
    //dc_list_content_part
    //dc_list_content_level
    //dc_list_content_characteristics
    //dc_list_content_situation
    //dc_list_content_accompany_pain
    //dc_list_content_additional

    public void addItem(String dc_list_title_date,
                        String dc_list_title_partAndLevel,
                        String dc_list_content_part,
                        int dc_list_content_level,
                        String dc_list_content_characteristics,
                        String dc_list_content_situation,
                        String dc_list_content_accompany_pain,
                        String dc_list_content_additional){

        Log.d("myapp","addItem 진입");
        DCListViewItem item = new DCListViewItem();
        item.setTitleDate(dc_list_title_date);
        item.setTitlePartAndLevel(dc_list_title_partAndLevel);
        item.setPart(dc_list_content_part);
        item.setLevel(dc_list_content_level);
        item.setChar(dc_list_content_characteristics);
        item.setSituation(dc_list_content_situation);
        item.setAccompany(dc_list_content_accompany_pain);
        if(dc_list_content_additional!=null||dc_list_content_additional!="") item.setAdditional(dc_list_content_additional);
        else item.setAdditional("해당없음");

        listViewItemArrayDCList.add(item);
    }


}
