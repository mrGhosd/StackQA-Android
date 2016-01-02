package com.vsokoltsov.stackqa.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;

import java.util.List;

/**
 * Created by vsokoltsov on 02.01.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.QuestionsViewHolder> {
    List<Question> questions;

    public RVAdapter(List<Question> questions){
        this.questions = questions;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list_row, parent, false);
        QuestionsViewHolder qvh = new QuestionsViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.title.setText(questions.get(position).getTitle());
        holder.rate.setText(String.valueOf(questions.get(position).getRate()));
        holder.category.setText(questions.get(position).getCategory().getTitle());
        holder.createdAt.setText(questions.get(position).getCreatedAt());
        holder.answersCount.setText(String.valueOf(questions.get(position).getAnswersCount()));
        holder.commentsCount.setText(String.valueOf(questions.get(position).getCommentsCount()));
        holder.views.setText(String.valueOf(questions.get(position).getViews()));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        private TextView title;
        private TextView rate;
        private TextView category;
        private TextView createdAt;
        private TextView answersCount;
        private TextView commentsCount;
        private TextView views;

        QuestionsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title =  (TextView) itemView.findViewById(R.id.title);
            rate = (TextView) itemView.findViewById(R.id.rate);
            category = (TextView) itemView.findViewById(R.id.category);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
            answersCount = (TextView) itemView.findViewById(R.id.answersCount);
            commentsCount = (TextView) itemView.findViewById(R.id.commentsCount);
            views = (TextView) itemView.findViewById(R.id.viewsCount);
        }
    }
}
