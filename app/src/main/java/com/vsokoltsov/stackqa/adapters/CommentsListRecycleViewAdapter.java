package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 11.02.16.
 */
public class CommentsListRecycleViewAdapter extends RecyclerView.Adapter<CommentsListRecycleViewAdapter.CommentViewHolder> {

    public List<Comment> comments;
    private Activity activity;

    public CommentsListRecycleViewAdapter(List<Comment> comments, Activity activity){
        this.comments = comments;
        this.activity = activity;
    }



    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_row, parent, false);
        CommentViewHolder qvh = new CommentViewHolder(v, this);
        return qvh;
    }


    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.comment = comments.get(position);
        holder.setUserInfo(comments.get(position));
        holder.text.setText(comments.get(position).getText());
        holder.createdAt.setText(comments.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public Comment getComment(int position) {
        return comments.get(position);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        LinearLayout ll;
        private Resources res;

        private TextView text;
        private TextView createdAt;
        private TextView rate;
        private ImageButton popupMenu;
        private Comment comment;
        private CommentsListRecycleViewAdapter mAdapter;
        private LinearLayout formLayout;
        private LinearLayout answerViewMain;
        private EditText answerText;

        CommentViewHolder(final View itemView, final CommentsListRecycleViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
//            res = mAdapter.activity.getResources();
            ll = (LinearLayout) itemView.findViewById(R.id.commentRowItem);
            text = (TextView) itemView.findViewById(R.id.commentText);
            createdAt = (TextView) itemView.findViewById(R.id.commentCreatedAt);
//            commentsCount = (TextView) itemView.findViewById(R.id.answerCommentsCount);
//            rate = (TextView) itemView.findViewById(R.id.answerRateCount);
//            final LinearLayout popupMenuWrapper = (LinearLayout) itemView.findViewById(R.id.popupMenuWrapper);
//            popupMenu = (ImageButton) itemView.findViewById(R.id.popupMenu);
//
//            if (AuthManager.getInstance().getCurrentUser() == null) {
//                popupMenuWrapper.setVisibility(View.GONE);
//            }
//            else {
//                popupMenuWrapper.setVisibility(View.VISIBLE);
//                popupMenu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Context context = view.getContext();
//                        PopupWithMenuIcons popup = new PopupWithMenuIcons(context, view);
//                        // This activity implements OnMenuItemClickListener
//                        MenuInflater inflater = popup.getMenuInflater();
//                        popup.inflate(R.menu.menu_answer_list);
//                        MenuItem editItem = popup.getMenu().getItem(0);
//                        MenuItem deleteItem = popup.getMenu().getItem(1);
//                        MenuItem complainItem = popup.getMenu().getItem(2);
//                        if (AuthManager.getInstance().getCurrentUser().getId() != answer.getUser().getId()) {
//                            editItem.setVisible(false);
//                            deleteItem.setVisible(false);
//                        }
//                        formLayout = (LinearLayout) itemView.findViewById(R.id.answerFormItem);
//                        answerViewMain = (LinearLayout) itemView.findViewById(R.id.answerViewMain);
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                callbacks.onOptionsClicked(answer, itemView, item);
//                                return true;
//                            }
//                        });
//                        popup.show();
//                    }
//                });
//
//            }
        }

        public void setUserInfo(final Comment comment){
            final CircleImageView userImage = (CircleImageView) ll.findViewById(R.id.authorAvatar);

            String url = AppController.APP_HOST + comment.getUser().getAvatarUrl();
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