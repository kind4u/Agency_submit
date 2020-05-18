package com.example.admin.agency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a on 2018-05-12.
 */

public class AsyncAPI extends AsyncTask<String, String, String> {
    String userID;
    String userName1;
    Integer total;
    Integer aqua;
    Integer museum;
    Integer art;

    // 사용자 정보 설정 메서드
    void setUserInfo(String userID, String userName, Integer total, Integer aqua, Integer museum, Integer art) {
        this.userID =userID;
        this.userName1 = userName;
        this.total = total;
        this.aqua = aqua;
        this.museum = museum;
        this.art = art;
    }

    //공연/전시 정보를 담을 리스트(SingerItem 객체를 담아둘 ArrayList 생성)
    ArrayList<PerformItem> perform = new ArrayList<PerformItem>();
    ListView searchListView;
    ListViewAdapter adapter;

    public ArrayList<PerformItem> getPerform() {
        return perform;
    }

    public void settingListView(ListView searchListView, ListViewAdapter adapter) {
        this.searchListView = searchListView;
        this.adapter = adapter;
    }

    // 키워드 검색 시 progressDialog 생성을 위한 객체
    ProgressDialog asyncDialog;

    public AsyncAPI() {}
    public AsyncAPI(SearchActivity searchActivity) {
        // splash 화면에서 넘어온 것이 아니라면
        if (preActivitySplash == false) {
            // ProgressDialog 생성
            asyncDialog = new ProgressDialog(searchActivity);
        }
    }

    // 이전 액티비티 (Splash2[o] : true, Splash2[x] : false)
    Boolean preActivitySplash = false;

    // 공연/전시 정보 속성(태그)값
    public static ArrayList<String> title = new ArrayList<String>();
    public static ArrayList<String> thumbnail = new ArrayList<String>();
    public static ArrayList<String> seq = new ArrayList<String>();
    public static ArrayList<String> start = new ArrayList<String>();
    public static ArrayList<String> end = new ArrayList<String>();
    public static ArrayList<String> place = new ArrayList<String>();

    // 검색 키워드
    String key = null;

    // 파싱 값 저장할 Document
    private Document urlDocument = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // asynctask 시작할 때 progressDialog 보이게하기
        if(preActivitySplash == false) {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.show();
        }
    }

    // Asynctask 메서드 시작
    @Override
    protected String doInBackground(String... strings) {
        key = strings[0];

        // 검색 URL
        final String searchUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm?keyword=" + key  + "&serviceKey=aOQ8Xk9fmg1g8hQbbtRQl22g9HMJ3tCM7rd8LS8NXBWeVVwbutNdSW7%2F2oHRooppHN5wGLp7nrliLflOxRAKsQ%3D%3D";

        try {
            setUrlDocument(Jsoup.connect(searchUrl).get());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = getUrlDocument();

        // 속성(태그)별로 분류할 ArrayList 초기화
        arrayListClear();

        String str = null;

        // 제목택 가져오기
        str = doc.select("response msgBody perforList title").toString();
        str = str.replace("<title> ", "" ).replace("<title>", "").replace("</title>", "");
        String[] titleArr = str.split("\n");
        // title
        for(int i=0; i< titleArr.length; i++) {
            title.add(titleArr[i]);
        }
        // 일련번호 가져오기
        str = doc.select("response msgBody perforList seq").toString();
        str = str.replace("<seq>\n", "" ).replace("\n</seq>", "").replace(" ", "");
        String[] seqArr = str.split("\n");
        // seq
        for(int i=0; i< seqArr.length; i++) {
            seq.add(seqArr[i]);
        }
        // 썸네일택 가져오기
        str = doc.select("response msgBody perforList thumbnail").toString();
        str = str.replace("<thumbnail>\n", "" ).replace("<thumbnail>\n ", "" ).replace("\n</thumbnail>", "");
        String[] thumbArr = str.split("\n");
        // thumbnail
        for(int i=0; i< thumbArr.length; i++) {
            thumbnail.add(thumbArr[i]);
        }
        // 시작일 가져오기
        str = doc.select("response msgBody perforList startDate").toString();
        str = str.replace("<startDate>\n ", "" ).replace("<startDate>\n", "").replace("\n</startDate>", "");
        String[] startArr = str.split("\n");
        // start
        for(int i=0; i< startArr.length; i++) {
            start.add(startArr[i]);
        }
        // 마감일 가져오기
        str = doc.select("response msgBody perforList endDate").toString();
        str = str.replace("<endDate>\n ", "" ).replace("<endDate>\n ", "").replace("\n</endDate>", "");
        String[] endArr = str.split("\n");
        // end
        for(int i=0; i< endArr.length; i++) {
            end.add(endArr[i]);
        }
        // 장소 가져오기
        str = doc.select("response msgBody perforList place").toString();
        str = str.replace("<place>\n ", "" ).replace("\n</place>", "");
        String[] placeArr = str.split("\n");
        // end
        for(int i=0; i< placeArr.length; i++) {
            place.add(placeArr[i]);
        }
        // Splash2에서 넘어온 경우 ... 모든 리스트 불러오기
        if(getPreActivitySplash() == true) {
            Intent intent = new Intent(Splash2.context2, MainActivity.class);
            intent.putExtra("titleList", title);
            intent.putExtra("startList", getStart());
            intent.putExtra("endList", getEnd());
            intent.putExtra("placeList", getPlace());
            intent.putExtra("seq", getSeq());
            intent.putExtra("thumbList", getThumbnail());
            intent.putExtra("preActivityCheck", getPreActivitySplash());

            intent.putExtra("userID",userID);
            intent.putExtra("userName", userName1);
            intent.putExtra("total",total);
            intent.putExtra("aqua",aqua);
            intent.putExtra("museum",museum);
            intent.putExtra("art",art);

            Splash2.context2.startActivity(intent);
        }
        // Splash2 이외의 액티비티로부터 넘어온 경우 ... 키워드 검색을 위한 리스트 셋팅
        else {
            Intent intent = new Intent(Splash2.context2, PerformDetail.class);

            intent.putExtra("userID",userID);
            intent.putExtra("userName", userName1);
            intent.putExtra("total",total);
            intent.putExtra("aqua",aqua);
            intent.putExtra("museum",museum);
            intent.putExtra("art",art);

        }

        return null;
    }// doInBackground()

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // splash 화면에서 넘어온 것이 아니라면 asynctask 끝날 때 progressDialog 없애기
        if(preActivitySplash == false) {

            asyncDialog.dismiss();

            // PerformItem 객체 생성
            for(int i=0; i<title.size(); i++) {
                perform.add(new PerformItem(thumbnail.get(i), title.get(i), start.get(i), end.get(i), place.get(i)));
            }

            adapter.setAlbum(perform);

            //어댑터 객체를 리스트 뷰에 설정
            searchListView.setAdapter(adapter);

        }
        onCancelled();
    }

    // 공연 정보를 가지고 있는 ArrayList 초기화 메서드
    void arrayListClear() {
        getTitle().clear();
        getThumbnail().clear();
        getStart().clear();
        getEnd().clear();
        getSeq().clear();
        getPlace().clear();
    }

    // getter, setter 메서드

    public Document getUrlDocument() {
        return urlDocument;
    }

    public void setUrlDocument(Document urlDocument) {
        this.urlDocument = urlDocument;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public static ArrayList<String> getTitle() {
        return title;
    }

    public void setThumbnail(ArrayList<String> thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static ArrayList<String> getThumbnail() {
        return thumbnail;
    }

    public ArrayList<String> getSeq() {
        return seq;
    }

    public void setSeq(ArrayList<String> seq) {
        this.seq = seq;
    }

    public ArrayList<String> getStart() {
        return start;
    }

    public void setStart(ArrayList<String> start) {
        this.start = start;
    }

    public ArrayList<String> getEnd() {
        return end;
    }

    public void setEnd(ArrayList<String> end) {
        this.end = end;
    }

    public ArrayList<String> getPlace() {
        return place;
    }

    public void setPlace(ArrayList<String> place) {
        this.place = place;
    }

    public Boolean getPreActivitySplash() {
        return preActivitySplash;
    }

    // 이전 액티비티가 Splash 인지 확인하기 위한 setter
    public void setPreActivitySplash(Boolean preActivitySplash) {
        this.preActivitySplash = preActivitySplash;
    }


}
