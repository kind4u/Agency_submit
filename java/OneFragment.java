package com.example.admin.agency;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OneFragment extends Fragment {
    TextView firstAquaName;
    TextView secondAquaName;
    TextView thirdAquaName;
    TextView forthAquaName;

    String firstStringName, firstStringTotal, firstStringAqua, firstStringMuseum, firstStringArt;
    String secondStringName, secondStringTotal, secondStringAqua, secondStringMuseum, secondStringArt;
    String thirdStringName, thirdStringTotal, thirdStringAqua, thirdStringMuseum, thirdStringArt;
    String forthStringName, forthStringTotal, forthStringAqua, forthStringMuseum, forthStringArt;

    public OneFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                                      @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);

        ViewGroup containerOne = (ViewGroup) view.findViewById(R.id.contanerOne);
        ViewGroup containerTwo = (ViewGroup) view.findViewById(R.id.contanerTwo);
        ViewGroup containerThree = (ViewGroup) view.findViewById(R.id.contanerThree);
        ViewGroup containerFour = (ViewGroup) view.findViewById(R.id.contanerFour);
        firstAquaName = (TextView) view.findViewById(R.id.firstAquaName);
        secondAquaName = (TextView) view.findViewById(R.id.secondAquaName);
        thirdAquaName = (TextView) view.findViewById(R.id.thirdAquaName);
        forthAquaName = (TextView) view.findViewById(R.id.forthAquaName);

        new AquaRankTask().execute();

        containerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOne = new Intent((RankingActivity)getActivity(), UserInfoActivity.class);
                intentOne.putExtra("name", firstStringName);
                intentOne.putExtra("total", Integer.parseInt(firstStringTotal));
                intentOne.putExtra("aqua", Integer.parseInt(firstStringAqua));
                intentOne.putExtra("museum", Integer.parseInt(firstStringMuseum));
                intentOne.putExtra("art", Integer.parseInt(firstStringArt));
                intentOne.putExtra("myName",((RankingActivity)getActivity()).myName);
                intentOne.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentOne);
            }
        });
        containerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTwo = new Intent(getActivity(), UserInfoActivity.class);
                intentTwo.putExtra("name", secondStringName);
                intentTwo.putExtra("total", Integer.parseInt(secondStringTotal));
                intentTwo.putExtra("aqua", Integer.parseInt(secondStringAqua));
                intentTwo.putExtra("museum", Integer.parseInt(secondStringMuseum));
                intentTwo.putExtra("art", Integer.parseInt(secondStringArt));
                intentTwo.putExtra("myName",((RankingActivity)getActivity()).myName);
                intentTwo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentTwo);
            }
        });
        containerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThree = new Intent(getActivity(), UserInfoActivity.class);
                intentThree.putExtra("name", thirdStringName);
                intentThree.putExtra("total", Integer.parseInt(thirdStringTotal));
                intentThree.putExtra("aqua", Integer.parseInt(thirdStringAqua));
                intentThree.putExtra("museum", Integer.parseInt(thirdStringMuseum));
                intentThree.putExtra("art", Integer.parseInt(thirdStringArt));
                intentThree.putExtra("myName",((RankingActivity)getActivity()).myName);
                intentThree.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentThree);
            }
        });
        containerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFour = new Intent(getActivity(), UserInfoActivity.class);
                intentFour.putExtra("name", forthStringName);
                intentFour.putExtra("total", Integer.parseInt(forthStringTotal));
                intentFour.putExtra("aqua", Integer.parseInt(forthStringAqua));
                intentFour.putExtra("museum", Integer.parseInt(forthStringMuseum));
                intentFour.putExtra("art", Integer.parseInt(forthStringArt));
                intentFour.putExtra("myName",((RankingActivity)getActivity()).myName);
                intentFour.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentFour);
            }
        });
        return view;
    }

    class AquaRankTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://1.255.57.7/AquaRank.php";
        }

        @Override
        protected void onPostExecute(String aquaResult) {
            try {
                JSONObject jsonObject2 = new JSONObject(aquaResult);
                JSONArray jsonArray2 = jsonObject2.getJSONArray("response");
                JSONObject firstUserObject = jsonArray2.getJSONObject(0);
                firstStringName = firstUserObject.getString("userName");
                firstStringTotal = firstUserObject.getString("total");
                firstStringAqua = firstUserObject.getString("aqua");
                firstStringMuseum = firstUserObject.getString("museum");
                firstStringArt = firstUserObject.getString("art");
                firstAquaName.setText(firstStringName);
                JSONObject secondUserObject = jsonArray2.getJSONObject(1);
                secondStringName = secondUserObject.getString("userName");
                secondStringTotal = secondUserObject.getString("total");
                secondStringAqua = secondUserObject.getString("aqua");
                secondStringMuseum = secondUserObject.getString("museum");
                secondStringArt = secondUserObject.getString("art");
                secondAquaName.setText(secondStringName);
                JSONObject thirdUserObject = jsonArray2.getJSONObject(2);
                thirdStringName = thirdUserObject.getString("userName");
                thirdStringTotal = thirdUserObject.getString("total");
                thirdStringAqua = thirdUserObject.getString("aqua");
                thirdStringMuseum = thirdUserObject.getString("museum");
                thirdStringArt = thirdUserObject.getString("art");
                thirdAquaName.setText(thirdStringName);
                JSONObject forthUserObject = jsonArray2.getJSONObject(3);
                forthStringName = forthUserObject.getString("userName");
                forthStringTotal = forthUserObject.getString("total");
                forthStringAqua = forthUserObject.getString("aqua");
                forthStringMuseum = forthUserObject.getString("museum");
                forthStringArt = forthUserObject.getString("art");
                forthAquaName.setText(forthStringName);
            }   catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url2 = new URL(target);
                HttpURLConnection httpURLConnect2 = (HttpURLConnection) url2.openConnection();
                InputStream inputStream2 = httpURLConnect2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                String temp2;
                StringBuilder stringBuilder2 = new StringBuilder();
                while((temp2 = bufferedReader2.readLine()) != null)
                {
                    stringBuilder2.append(temp2 + "\n");
                }
                bufferedReader2.close();
                inputStream2.close();
                httpURLConnect2.disconnect();
                return stringBuilder2.toString().trim();
            }   catch(Exception e)  {
                e.printStackTrace();
            }
            return null;
        }
    }
}
