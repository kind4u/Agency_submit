package com.example.admin.agency;

import android.content.Intent;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by a on 2018-05-21.
 */

public class AsyncPerforInfo extends AsyncTask<String, String, String> {
    final String perforInfoUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey=aOQ8Xk9fmg1g8hQbbtRQl22g9HMJ3tCM7rd8LS8NXBWeVVwbutNdSW7%2F2oHRooppHN5wGLp7nrliLflOxRAKsQ%3D%3D&seq=";

    private Document infoDocument = null;

    public Document getInfoDocument() {
        return infoDocument;
    }

    public void setInfoDocument(Document infoDocument) {
        this.infoDocument = infoDocument;
    }

    private String price;
    private String phone;
    private String placeUrl;
    private String placeAddr;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }

    // 선택 사항 항목
    String selectThumbnail;
    int selectImg;
    String selectName;
    String selectPlace;
    String selectStartDay;
    String selectEndDay;
    private String userName;

    public String getSelectThumbnail() {
        return selectThumbnail;
    }

    public void setSelectThumbnail(String selectThumbnail) {
        this.selectThumbnail = selectThumbnail;
    }

    public int getSelectImg() {
        return selectImg;
    }

    public void setSelectImg(int selectImg) {
        this.selectImg = selectImg;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getSelectPlace() {
        return selectPlace;
    }

    public void setSelectPlace(String selectPlace) {
        this.selectPlace = selectPlace;
    }

    public String getSelectStartDay() {
        return selectStartDay;
    }

    public void setSelectStartDay(String selectStartDay) {
        this.selectStartDay = selectStartDay;
    }

    public String getSelectEndDay() {
        return selectEndDay;
    }

    public void setSelectEndDay(String selectEndDay) {
        this.selectEndDay = selectEndDay;
    }

    public void setListItem(MainActivity.ListItem listItem) {
        setSelectImg(listItem.img);
        setSelectName(listItem.name);
        setSelectPlace(listItem.place);
        setSelectStartDay(listItem.startDay);
        setSelectEndDay(listItem.endDay);
        setSelectThumbnail(listItem.thumbnailUrl);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String infoKey = null;
            infoKey = params[0].toString();
            userName = params[1].toString();

            setInfoDocument(Jsoup.connect(perforInfoUrl + infoKey).get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPrice(infoDocument.select("response msgBody price").toString());
        setPrice(getPrice().replace("<price>\n ", "").replace("\n</price>", ""));
        // 장소 url 저장
        setPlaceUrl(infoDocument.select("response msgBody placeUrl").toString());
        setPlaceUrl(getPlaceUrl().replace("<placeUrl>\n ", "").replace("\n</placeUrl>",""));
        // 장소 주소 저장
        setPlaceAddr(infoDocument.select("response msgBody placeAddr").toString());
        setPlaceAddr(getPlaceAddr().replace("<placeAddr>\n ", "").replace("\n</placeAddr>",""));
        // 전화 번호 저장
        setPhone(infoDocument.select("response msgBody phone").toString());
        setPhone(getPhone().replace("<phone>\n ", "").replace("</phone>",""));

        return userName;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Intent intent = new Intent(Splash2.context2, PerformDetail.class);
        intent.putExtra("img", getSelectImg());
        intent.putExtra("imgUrl", getSelectThumbnail());
        intent.putExtra("perforname", getSelectName());
        intent.putExtra("place", getSelectPlace());
        intent.putExtra("startDay", getSelectStartDay());
        intent.putExtra("endDay", getSelectEndDay());
        intent.putExtra("price", getPrice());
        intent.putExtra("writer",s.toString());
        Splash2.context2.startActivity(intent);

        onCancelled();
    }
}
