package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.QuestionDetail;

import java.util.List;

import static android.widget.LinearLayout.*;

/**
 * Created by vsokoltsov on 02.01.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.QuestionsViewHolder> {
    public List<Question> questions;
    private Activity activity;

    public RVAdapter(List<Question> questions, Activity activity){
        this.questions = questions;
        this.activity = activity;
    }



    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list_row, parent, false);
        QuestionsViewHolder qvh = new QuestionsViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.questionId = questions.get(position).getID();
        holder.adapter = this;
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

    public Question getQuestion(int position) {
        return questions.get(position);
    }

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;

        private int questionId;
        private RVAdapter adapter;
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
            cv.setOnClickListener(this);
            title =  (TextView) itemView.findViewById(R.id.title);
            rate = (TextView) itemView.findViewById(R.id.rate);
            category = (TextView) itemView.findViewById(R.id.category);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
            answersCount = (TextView) itemView.findViewById(R.id.answersCount);
            commentsCount = (TextView) itemView.findViewById(R.id.commentsCount);
            views = (TextView) itemView.findViewById(R.id.viewsCount);
        }


        @Override
        public void onClick(View v) {
            Question question = this.adapter.questions.get(getPosition());
            Intent detailIntent = new Intent(views.getContext(), QuestionDetail.class);
            detailIntent.putExtra("question", question);

            v.getContext().startActivity(detailIntent);
            this.adapter.activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//            Question question = questions.get()
        }
    }
}
