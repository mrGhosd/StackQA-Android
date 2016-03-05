package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.util.PopupWithMenuIcons;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 10.01.16.
 */
public class AnswersListRecycleViewAdapter extends RecyclerView.Adapter<AnswersListRecycleViewAdapter.AnswerViewHolder>{
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
        Answer answer = answers.get(position);
        holder.answer = answer;
        holder.callbacks = (AnswerViewHolderCallbacks) activity;
        holder.text.setText(answer.getText());
        holder.rate.setText(String.valueOf(answer.getRate()));
        holder.createdAt.setText(answer.getCreatedAt());
        holder.commentsCount.setText(String.valueOf(answer.getCommentsCount()));
        if (answer.isHelpfull()) {
            holder.isHelpfullAnswer.setVisibility(View.VISIBLE);
        }
        else {
            holder.isHelpfullAnswer.setVisibility(View.GONE);
        }
        holder.setUserInfo(answer);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public Answer getAnswer(int position) {
        return answers.get(position);
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        LinearLayout ll;
        private Resources res;

        private TextView text;
        private TextView createdAt;
        private TextView commentsCount;
        private TextView rate;
        private ImageButton popupMenu;
        private Answer answer;
        private AnswersListRecycleViewAdapter mAdapter;
        private LinearLayout formLayout;
        private LinearLayout answerViewMain;
        private EditText answerText;
        private AnswerViewHolderCallbacks callbacks;
        private LinearLayout answerCommentWrapper;
        private ImageView isHelpfullAnswer;

        AnswerViewHolder(final View itemView, final AnswersListRecycleViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            res = mAdapter.activity.getResources();
            ll = (LinearLayout) itemView.findViewById(R.id.answerRowItem);
            text = (TextView) itemView.findViewById(R.id.answerText);
            createdAt = (TextView) itemView.findViewById(R.id.answerCreatedAt);
            commentsCount = (TextView) itemView.findViewById(R.id.answerCommentsCount);
            rate = (TextView) itemView.findViewById(R.id.answerRateCount);
            final LinearLayout popupMenuWrapper = (LinearLayout) itemView.findViewById(R.id.popupMenuWrapper);
            popupMenu = (ImageButton) itemView.findViewById(R.id.popupMenu);
            answerCommentWrapper = (LinearLayout) itemView.findViewById(R.id.answerCommentWrapper);
            isHelpfullAnswer = (ImageView) itemView.findViewById(R.id.checkedMark);

            if (AuthManager.getInstance().getCurrentUser() == null) {
                popupMenuWrapper.setVisibility(View.GONE);
            }
            else {
                popupMenuWrapper.setVisibility(View.VISIBLE);
                popupMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        PopupWithMenuIcons popup = new PopupWithMenuIcons(context, view);
                        // This activity implements OnMenuItemClickListener
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.inflate(R.menu.menu_answer_list);
                        MenuItem helpfullItem = popup.getMenu().getItem(0);
                        MenuItem editItem = popup.getMenu().getItem(1);
                        MenuItem deleteItem = popup.getMenu().getItem(2);
                        MenuItem complainItem = popup.getMenu().getItem(3);
                        boolean isCurrentUserAuthor = AuthManager.getInstance().getCurrentUser().getId() != answer.getUser().getId();
                        if (isCurrentUserAuthor) {
                            editItem.setVisible(false);
                            deleteItem.setVisible(false);
                        }

                        if (isCurrentUserAuthor) {
                            helpfullItem.setVisible(false);
                        }

                        formLayout = (LinearLayout) itemView.findViewById(R.id.answerFormItem);
                        answerViewMain = (LinearLayout) itemView.findViewById(R.id.answerViewMain);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                callbacks.onOptionsClicked(answer, itemView, item);
                                return true;
                            }
                        });
                        popup.show();
                    }
                });

                answerCommentWrapper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callbacks.onCommentsClicked(answer);
                    }
                });

            }
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
