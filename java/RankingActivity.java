package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener{

    ViewPager viewPager;
    TabLayout tabLayout;
    String myName;
    String firstStringName, firstStringTotal, firstStringAqua, firstStringMuseum, firstStringArt;
    String secondStringName, secondStringTotal, secondStringAqua, secondStringMuseum, secondStringArt;
    String thirdStringName, thirdStringTotal, thirdStringAqua, thirdStringMuseum, thirdStringArt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Intent forRankIntent = getIntent();
        myName = forRankIntent.getStringExtra("myName");
        ViewGroup firstRank = (ViewGroup) findViewById(R.id.firstRank);
        ViewGroup secondRank = (ViewGroup) findViewById(R.id.secondRank);
        ViewGroup thirdRank = (ViewGroup) findViewById(R.id.thirdRank);
        TextView firstNickname = (TextView) findViewById(R.id.firstNickname);
        TextView secondNickname = (TextView) findViewById(R.id.secondNickname);
        TextView thirdNickname = (TextView) findViewById(R.id.thirdNickname);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        //viewPager에 Adapter 설정
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		/* TabLayout은 탭 화면의 버튼 부분을 다양하게 구성할 수 있어 TabHost 보다 많이 사용함
           - TabLayout은 탭 본문을 ViewPager와 연동할 수 있어 매우 유용함
           - TabLayout은 탭 버튼만을 위한 뷰이며, 탭 버튼에 의해 나올 화면은
             FrameLayout을 사용하거나 ViewPager와 연동해야 함
           - ViewPager와 연결되면 ViewPager에서 지정한 타이틀 문자열이 자동으로 탭 버튼의
             문자열이 되며, 사용자가 ViewPager 화면을 조정하면 탭 버튼의 활성 상태도 자동으로 변경됨
        */
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        //tabLayout을 ViewPager와 연동 및 이벤트 핸들러 등록
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        try {
            JSONObject jsonObject2 = new JSONObject(forRankIntent.getStringExtra("ranking"));
            JSONArray jsonArray2 = jsonObject2.getJSONArray("response");
            JSONObject firstUserObject = jsonArray2.getJSONObject(0);
            firstStringName = firstUserObject.getString("userName");
            firstStringTotal = firstUserObject.getString("total");
            firstStringAqua = firstUserObject.getString("aqua");
            firstStringMuseum = firstUserObject.getString("museum");
            firstStringArt = firstUserObject.getString("art");
            firstNickname.setText(firstStringName);
            JSONObject secondUserObject = jsonArray2.getJSONObject(1);
            secondStringName = secondUserObject.getString("userName");
            secondStringTotal = secondUserObject.getString("total");
            secondStringAqua = secondUserObject.getString("aqua");
            secondStringMuseum = secondUserObject.getString("museum");
            secondStringArt = secondUserObject.getString("art");
            secondNickname.setText(secondStringName);
            JSONObject thirdUserObject = jsonArray2.getJSONObject(2);
            thirdStringName = thirdUserObject.getString("userName");
            thirdStringTotal = thirdUserObject.getString("total");
            thirdStringAqua = thirdUserObject.getString("aqua");
            thirdStringMuseum = thirdUserObject.getString("museum");
            thirdStringArt = thirdUserObject.getString("art");
            thirdNickname.setText(thirdStringName);
        }   catch (Exception e) {
            e.printStackTrace();
        }

        firstRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oneRankIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                oneRankIntent.putExtra("name", firstStringName);
                oneRankIntent.putExtra("total", Integer.parseInt(firstStringTotal));
                oneRankIntent.putExtra("aqua", Integer.parseInt(firstStringAqua));
                oneRankIntent.putExtra("museum", Integer.parseInt(firstStringMuseum));
                oneRankIntent.putExtra("art", Integer.parseInt(firstStringArt));
                oneRankIntent.putExtra("myName",myName);
                oneRankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(oneRankIntent);
            }
        });
        secondRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondRankIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                secondRankIntent.putExtra("name", secondStringName);
                secondRankIntent.putExtra("total", Integer.parseInt(secondStringTotal));
                secondRankIntent.putExtra("aqua", Integer.parseInt(secondStringAqua));
                secondRankIntent.putExtra("museum", Integer.parseInt(secondStringMuseum));
                secondRankIntent.putExtra("art", Integer.parseInt(secondStringArt));
                secondRankIntent.putExtra("myName",myName);
                secondRankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(secondRankIntent);
            }
        });
        thirdRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thirdRankIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                thirdRankIntent.putExtra("name", thirdStringName);
                thirdRankIntent.putExtra("total", Integer.parseInt(thirdStringTotal));
                thirdRankIntent.putExtra("aqua", Integer.parseInt(thirdStringAqua));
                thirdRankIntent.putExtra("museum", Integer.parseInt(thirdStringMuseum));
                thirdRankIntent.putExtra("art", Integer.parseInt(thirdStringArt));
                thirdRankIntent.putExtra("myName",myName);
                thirdRankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(thirdRankIntent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }//end of onCreate

    /* OnTabSelectedListener의 콜백 함수
    - TabLayout의 탭 버튼을 사용자가 클릭했을 때 이벤트를 처리하기 위한 콜백함수로,
      탭 버튼과 ViewPager 화면 조정을 setCurrentItem() 메서드로 처리
*/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /* TabLayout과 연동하기 위한 ViewPager의 Adapter 클래스 선언 */
    class MyPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();//fragments ArrayList

        //탭 버튼 문자열 배열
        String title[]=new String[]{"수족관", "박물관", "미술관"};

        //Adapter 생성자
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
            //프래그먼트를 생성하여 ArrayList에 add
            fragments.add(new OneFragment());
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());
        }

        /* 실제 ViewPager에서 보여질 프래그먼트를 반환
           - 일반 Adapter(리스트뷰 등)의 getView()와 같은 역할
           - @param position : ViewPager에서 보여져야할 페이지 값(0부터)
           - @return : 보여질 fragment
        */
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        //ViewPager에 보여질 총 페이지 수
        @Override
        public int getCount() {
            return fragments.size();
        }

        //getPageTitle() 메서드에서 반환한 문자열이 TabLayout의 버튼 문자열로 사용됨
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
