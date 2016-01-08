package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        TextView rate = (TextView) convertView.findViewById(R.id.answerRateCount);

        // getting movie data for the row
        Answer answer = answerList.get(position);

        setUserInfo(convertView, answer);
        text.setText(answer.getText());
        createdAt.setText(answer.getCreatedAt());
        commentsCount.setText(String.valueOf(answer.getCommentsCount()));
        rate.setText(String.valueOf(answer.getRate()));
        return convertView;
    }

    public void setUserInfo(View fragmentView, final Answer answer){
        final CircleImageView userImage = (CircleImageView) fragmentView.findViewById(R.id.authorAvatar);

        String url = AppController.APP_HOST + answer.getUser().getAvatarUrl();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                userImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
