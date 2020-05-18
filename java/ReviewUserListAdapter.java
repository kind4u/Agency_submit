package com.example.admin.agency;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewUserListAdapter extends BaseAdapter {

    private Context context;
    private List<ReviewUser> userList;
    private final Activity parentActivity;
    private List<ReviewUser> saveList;

    public ReviewUserListAdapter(Context context, List<ReviewUser> userList, Activity parentActivity, List<ReviewUser> saveList){
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.review_user, null);
        final TextView reviewID = (TextView) v.findViewById(R.id.reviewID);
        final TextView title = (TextView) v.findViewById(R.id.title);
        final TextView reviewerName = (TextView) v.findViewById(R.id.reviewerName);
        final TextView contents = (TextView) v.findViewById(R.id.contents);

        reviewID.setText(userList.get(i).getReviewID());
        title.setText(userList.get(i).getTitle());
        reviewerName.setText(userList.get(i).getReviewerName());
        contents.setText(userList.get(i).getContents());

        v.setTag(userList.get(i).getReviewID());

        return v;
    }
}
