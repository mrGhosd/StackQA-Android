package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.util.PopupWithMenuIcons;
import com.vsokoltsov.stackqa.views.answers.AnswerFormActivity;

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
        AnswerViewHolder qvh = new AnswerViewHolder(v, this);
        return qvh;
    }


    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.answer = answers.get(position);
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

    public static class AnswerViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        CardView cv;
        LinearLayout ll;

        private TextView text;
        private TextView createdAt;
        private TextView commentsCount;
        private TextView rate;
        private ImageButton popupMenu;
        private Answer answer;
        private AnswersListRecycleViewAdapter mAdapter;

        AnswerViewHolder(View itemView, final AnswersListRecycleViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            ll = (LinearLayout) itemView.findViewById(R.id.answerRowItem);
            text = (TextView) itemView.findViewById(R.id.answerText);
            createdAt = (TextView) itemView.findViewById(R.id.answerCreatedAt);
            commentsCount = (TextView) itemView.findViewById(R.id.answerCommentsCount);
            rate = (TextView) itemView.findViewById(R.id.answerRateCount);
            popupMenu = (ImageButton) itemView.findViewById(R.id.popupMenu);
            popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    PopupWithMenuIcons popup = new PopupWithMenuIcons(context, view);

                    // This activity implements OnMenuItemClickListener
                    MenuInflater inflater = popup.getMenuInflater();
                    popup.inflate(R.menu.menu_answer_list);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch ((String) item.getTitle()) {
                                case "Edit":
                                    Intent detailIntent = new Intent(mAdapter.activity ,AnswerFormActivity.class);
                                    detailIntent.putExtra("answer", answer);
                                    mAdapter.activity.startActivity(detailIntent);
                                    mAdapter.activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
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

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }


}
