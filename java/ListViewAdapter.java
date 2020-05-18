package com.example.admin.agency;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by a on 2018-06-13.
 */

public class ListViewAdapter extends BaseAdapter {
    //전달받은 Context 객체를 저장할 변수
    Context mContext;
    int perform_item;
    ArrayList<PerformItem> performance;
    LayoutInflater inflater;

    public void setAlbum(ArrayList<PerformItem> performance) {
        this.performance = performance;
    }

    //어댑터 생성자
    public ListViewAdapter(Context context, int perform_item, ArrayList<PerformItem> performance) {
        mContext = context;
        this.perform_item = perform_item;
        this.performance = performance;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

        /* 어댑터를 리스트뷰에 설정하면 리스트뷰(위젯)가 자동 호출함
                - 리스트뷰가 아댑터에게 요청하는 메서드들... */

    // 리스트 크기 반환
    @Override
    public int getCount() {
        return performance.size();
    }

    // 클릭한 인덱스의 데이터 반환
    @Override
    public Object getItem(int position) {
        return performance.get(position);
    }

    // 클릭한 인덱스의 값 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    //리스트에 아이템 추가
    public void addItem(PerformItem item) {
        performance.add(item);
    }

    //리스트의 모든 아이템 삭제
    public void clear() {
        performance.clear();
    }

    // 아이템을 화면에 표시하기 위한 메서드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PerformLayout performLayout = null;

        if (convertView == null) {
            performLayout = new PerformLayout(mContext);
        } else {
            performLayout = (PerformLayout) convertView;
        }

        // 인덱스 값을 통하여 리스트 아이템 가져오기
        PerformItem items = performance.get(position);

        performLayout.refreshImg(ListViewAdapter.this);
        Log.d("ListViewAdapter 에서 확인", items.getThumb());
        // 인덱스 값을 통하여 리스트 이미지 설정
        performLayout.setImage(items.getThumb());


        // 인덱스 값을 통하여 리스트에 이름 가져오기
        performLayout.setNameText(items.getName());

        // 인덱스 값을 통하여 리스트에 시작일 가져오기
        performLayout.setStartText(items.getStartDay());

        // 인덱스 값을 통하여 리스트에 종료일 가져오기
        performLayout.setEndText(items.getEndDay());

        // 인덱스 값을 통하여 리스트에 장소 가져오기
        performLayout.setPlaceText(items.getPlace());

        //레이아웃 반환
        return performLayout;
    }

}
