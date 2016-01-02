package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;

import java.util.List;

/**
 * Created by vsokoltsov on 02.01.16.
 */
public class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerRecyclerViewAdapter.AnswersViewHolder> {
    public List<Answer> answers;
    private Activity activity;

    public AnswerRecyclerViewAdapter(List<Answer> answers, Activity activity) {
        this.answers = answers;
        this.activity = activity;
    }

    @Override
    public AnswerRecyclerViewAdapter.AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_row, parent, false);
        AnswersViewHolder avh = new AnswersViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(final AnswersViewHolder holder, int position) {
        final Answer answer = answers.get(position);

        String url = AppController.APP_HOST + answer.getUser().getAvatarUrl();
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.userAvatar.setImageBitmap(response);
                holder.userName.setText(answer.getUser().getCorrectNaming());
            }
        }, 0, 0, null, null);
        AppController.getInstance().addToRequestQueue(ir);

        holder.text.setText(answer.getText());
        holder.createdAt.setText(answer.getCreatedAt());
        holder.commentsCount.setText(String.valueOf(answer.getCommentsCount()));
        holder.rate.setText(String.valueOf(answer.getRate()));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class AnswersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout ll;
        private TextView text;
        private TextView createdAt;
        private TextView commentsCount;
        private TextView rate;
        private ImageView userAvatar;
        private TextView userName;



        public AnswersViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout)itemView.findViewById(R.id.answerRowItem);
            text = (TextView) itemView.findViewById(R.id.answerText);
            createdAt = (TextView) itemView.findViewById(R.id.answerCreatedAt);
            commentsCount = (TextView) itemView.findViewById(R.id.answerCommentsCount);
            rate = (TextView) itemView.findViewById(R.id.answerRateCount);
            userAvatar = (ImageView) itemView.findViewById(R.id.authorAvatar);
            userName = (TextView) itemView.findViewById(R.id.answerAuthor);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
