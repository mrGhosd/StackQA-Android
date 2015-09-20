package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.Question;

import java.util.List;

/**
 * Created by vsokoltsov on 19.09.15.
 */
public class AnswersListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Answer> answerList;

    public AnswersListAdapter(Activity activity, List<Answer> answerList) {
        this.activity = activity;
        this.answerList = answerList;
    }

    @Override
    public int getCount() {
        return answerList.size();
    }

    @Override
    public Object getItem(int location) {
        return answerList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.answer_list_row, null);

        //Перечисляем элементы в layout-е строки списка вопросов

        TextView text = (TextView) convertView.findViewById(R.id.answerText);
        TextView createdAt = (TextView) convertView.findViewById(R.id.answerCreatedAt);
        TextView commentsCount = (TextView) convertView.findViewById(R.id.answerCommentsCount);

        // getting movie data for the row
        Answer answer = answerList.get(position);

        setUserInfo(convertView, answer);
        text.setText(answer.getText());
        createdAt.setText(answer.getCreatedAt());
        commentsCount.setText(String.valueOf(answer.getCommentsCount()));
        return convertView;
    }

    public void setUserInfo(View fragmentView, final Answer answer){
        final ImageView userImage = (ImageView) fragmentView.findViewById(R.id.authorAvatar);
        final TextView author = (TextView) fragmentView.findViewById(R.id.answerAuthor);

        String url = AppController.APP_HOST + answer.getUser().getAvatarUrl();
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                userImage.setImageBitmap(response);
                author.setText(answer.getUser().getCorrectNaming());
            }
        }, 0, 0, null, null);
        AppController.getInstance().addToRequestQueue(ir);
    }
}
