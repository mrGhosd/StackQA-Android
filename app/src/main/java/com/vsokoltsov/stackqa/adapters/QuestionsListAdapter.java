package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by vsokoltsov on 06.07.15.
 */
public class QuestionsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Question> questionsList;
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public QuestionsListAdapter(Activity activity, List<Question> questionsList) {
        this.activity = activity;
        this.questionsList = questionsList;
    }

    @Override
    public int getCount() {
        return questionsList.size();
    }

    @Override
    public Object getItem(int location) {
        return questionsList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.questions_list_row, null);

//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView;
//                .findViewById(R.id.thumbnail);

        //Перечисляем элементы в layout-е строки списка вопросов
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rate = (TextView) convertView.findViewById(R.id.rate);
        TextView category = (TextView) convertView.findViewById(R.id.category);

        // getting movie data for the row
        Question q = questionsList.get(position);

        // thumbnail image
//        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(q.getTitle());
        rate.setText(String.valueOf(q.getRate()));
        category.setText(q.getCategory().getTitle());

        // rating
//        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
//        String genreStr = "";
//        for (String str : m.getGenre()) {
//            genreStr += str + ", ";
//        }
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
//                genreStr.length() - 2) : genreStr;
//        genre.setText(genreStr);
//
//        // release year
//        year.setText(String.valueOf(m.getYear()));

        return convertView;
    }
}
