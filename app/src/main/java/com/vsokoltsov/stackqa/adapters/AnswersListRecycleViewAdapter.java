package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 10.01.16.
 */
public class AnswersListRecycleViewAdapter extends RecyclerView.Adapter<AnswersListRecycleViewAdapter.AnswerViewHolder> {
    public List<Answer> answers;
    private Activity activity;

    public AnswersListRecycleViewAdapter(List<Answer> answers, Activity activity){
        this.answers = answers;
        this.activity = activity;
    }



    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_row, parent, false);
        AnswerViewHolder qvh = new AnswerViewHolder(v);
        return qvh;
    }


    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.setUserInfo(answers.get(position));
        holder.text.setText(answers.get(position).getText());
        holder.rate.setText(String.valueOf(answers.get(position).getRate()));
        holder.createdAt.setText(answers.get(position).getCreatedAt());
        holder.commentsCount.setText(String.valueOf(answers.get(position).getCommentsCount()));

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public Answer getAnswer(int position) {
        return answers.get(position);
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        LinearLayout ll;

        private TextView text;
        private TextView createdAt;
        private TextView commentsCount;
        private TextView rate;

        AnswerViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.answerRowItem);
            text = (TextView) itemView.findViewById(R.id.answerText);
            createdAt = (TextView) itemView.findViewById(R.id.answerCreatedAt);
            commentsCount = (TextView) itemView.findViewById(R.id.answerCommentsCount);
            rate = (TextView) itemView.findViewById(R.id.answerRateCount);
        }


        @Override
        public void onClick(View v) {
////            Question question = this.adapter.questions.get(getPosition());
////            Intent detailIntent = new Intent(views.getContext(), QuestionDetail.class);
////            detailIntent.putExtra("question", question);
//
//            v.getContext().startActivity(detailIntent);
//            this.adapter.activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//            Question question = questions.get()
        }
        public void setUserInfo(final Answer answer){
            final CircleImageView userImage = (CircleImageView) ll.findViewById(R.id.authorAvatar);

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


}
