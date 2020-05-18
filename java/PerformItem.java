package com.example.admin.agency;

/**
 * Created by a on 2018-06-17.
 */

public class PerformItem {
    String thumb;
    int resId;//이미지 리소스 id
    String name;//이름
    String startDay;// 시작일
    String place; // 장소
    String endDay; // 최종일

    //생성자
//    public PerformItem(int resId, String name, String startDay, String endDay, String place) {
    public PerformItem(String thumb, String name, String startDay, String endDay, String place) {
        this.resId = resId;
        this.thumb = thumb;
        this.name = name;
        this.startDay = startDay;
        this.endDay = endDay;
        this.place = place;
    }

    // getter, setter 메서드

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
