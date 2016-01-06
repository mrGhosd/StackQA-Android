package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.07.15.
 */
public class QuestionsListAdapter extends BaseAdapter implements Filterable {
    private final Activity activity;
    private LayoutInflater inflater;
    private List<Question> questionsList;
    private QuestionFilter questionFilter;
    private List<Question> cachedList;
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public QuestionsListAdapter(Activity activity, List<Question> questionsList) {
        this.activity = activity;
        this.questionsList = questionsList;
        this.cachedList = questionsList;
    }

    public List<Question> getQuestionsList(){
        return questionsList;
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
        AuthManager authManager = AuthManager.getInstance();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.questions_list_row, null);

        //Перечисляем элементы в layout-е строки списка вопросов
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rate = (TextView) convertView.findViewById(R.id.rate);
        TextView category = (TextView) convertView.findViewById(R.id.category);
        TextView createdAt = (TextView) convertView.findViewById(R.id.createdAt);
        TextView answersCount = (TextView) convertView.findViewById(R.id.answersCount);
        TextView commentsCount = (TextView) convertView.findViewById(R.id.commentsCount);
        TextView views = (TextView) convertView.findViewById(R.id.viewsCount);

        // getting movie data for the row
        Question q = questionsList.get(position);

        title.setText(q.getTitle());
        rate.setText(String.valueOf(q.getRate()));
        category.setText(q.getCategory().getTitle());
        createdAt.setText(q.getCreatedAt());
        answersCount.setText(String.valueOf(q.getAnswersCount()));
        commentsCount.setText(String.valueOf(q.getCommentsCount()));
        views.setText(String.valueOf(q.getViews()));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (questionFilter == null) {
            questionFilter = new QuestionFilter();
        }

        return questionFilter;
    }


    private class QuestionFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Question> tempList = new ArrayList<Question>();

                // search content in friend list
                for (Question question : questionsList) {
                    if (question.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(question);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = cachedList.size();
                filterResults.values = cachedList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            questionsList = (ArrayList<Question>) results.values;
            notifyDataSetChanged();
        }
    }
}
