package com.example.doctorcommunication;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeListViewAdapter extends BaseAdapter {

    private ImageView record_img;
    private TextView record_title;
    private TextView record_content_painLevel;
    private TextView record_content_characteristics;
    private TextView record_content_situation;

    private ArrayList<ListViewItem> listViewItemArrayList = new ArrayList<>();

    public HomeListViewAdapter(){} //HomeListViewAdapter클래스 기본 생성자


    //Adapter에 사용되는 리스트(데이터 배열) 개수 반환
    @Override
    public int getCount() {
        return ListViewItem.count;
    }

    //지정한 위치에 있는 아이템 반환
    @Override
    public Object getItem(int position) {
        return listViewItemArrayList.get(position);
    }
    //지정한 위치와 관련있는 아이템의 Id값 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    //position(매개변수)에 있는 데이터를 화면에 출력할 View 반환
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int posision = position;
        final Context context = parent.getContext();

        //home_listview_item 레이아웃 inflate해서 convertView를 얻음
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_listview_item,parent,false);

        }

        //화면에 표시될 뷰에서(Layout이 inflate된 뷰) 위젯에 대한 접근권을 얻음
        record_img = (ImageView)convertView.findViewById(R.id.record_img);
        record_title = (TextView)convertView.findViewById(R.id.record_title);
        record_content_painLevel = (TextView)convertView.findViewById(R.id.record_content_painLevel);
        record_content_characteristics = (TextView) convertView.findViewById(R.id.record_content_characteristics);
        record_content_situation = (TextView)convertView.findViewById(R.id.record_content_situation);

        ListViewItem listViewItem = listViewItemArrayList.get(posision);

        //아이템의 각 위치에 데이터를 적용
        record_img.setImageResource(listViewItem.getImage()); //get
        record_title.setText(listViewItem.getTitle());
        record_content_painLevel.setText(listViewItem.getPainLevel());
        record_content_characteristics.setText(listViewItem.getCharacteristics());
        record_content_situation.setText(listViewItem.getSituation());

        return convertView;
    }

    public void addItem(String title,int image,int content_painLevel,String content_characteristics,String content_situation){
        ListViewItem item = new ListViewItem();
        item.setImage(image);
        item.setTitle(title);
        item.setPainLevel(content_painLevel);
        item.setCharacteristics(content_characteristics);
        item.setSituation(content_situation);

        listViewItemArrayList.add(item);
    }
}
