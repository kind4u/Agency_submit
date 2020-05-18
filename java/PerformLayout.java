package com.example.admin.agency;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by a on 2018-06-17.
 */

public class PerformLayout extends LinearLayout {
    ListViewAdapter adapter;

    public void refreshImg(ListViewAdapter adapter) {
        this.adapter = adapter;
    }

    Context mContext;
    LayoutInflater inflater;

    ImageView imageView;
    TextView nameTextView;
    TextView itemStartView;
    TextView itemEndView;
    TextView itemPlaceView;


    //생성자-1
    public PerformLayout(Context context) {
        super(context);
        mContext = context;

        //객체가 생성될 때 초기화
        init();
    }

    //생성자-2
    public PerformLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        //객체가 생성될 때 초기화
        init();
    }

    //초기화 메서드
    private void init() {
        // 아이템의 화면을 구성한 XML 레이아웃(singer_item.xml)을 인플레이션
        inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.perform_item, this, true);

        //부분화면 레이아웃에 정의된 객체 참조
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        itemStartView = (TextView) findViewById(R.id.itemStartDayTextView);
        itemEndView = (TextView) findViewById(R.id.itemEndDayTextView);
        itemPlaceView = (TextView) findViewById(R.id.itemPlaceView);
    }//end of init()

    public void setImage(String thumb) {//이미지 리소스 id 설정

        ImageSetting asyncTask = new ImageSetting();
        asyncTask.imageView(imageView);
        asyncTask.execute(thumb);

        adapter.notifyDataSetChanged();
    }

    public void setNameText(String name) {//이름 설정
        nameTextView.setText(name);
    }

    // 수정필요
    public void setStartText(String startDay) {//소속 설정
        itemStartView.setText(startDay);
    }

    public void setEndText(String endDay) {//소속 설정
        itemEndView.setText(endDay);
    }

    public void setPlaceText(String place) {//노래 설정
        itemPlaceView.setText(place);
    }
}
