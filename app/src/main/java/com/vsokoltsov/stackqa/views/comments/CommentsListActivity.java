package com.vsokoltsov.stackqa.views.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.CommentsListRecycleViewAdapter;
import com.vsokoltsov.stackqa.messages.CommentMessage;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Comment;
import com.vsokoltsov.stackqa.models.CommentFactory;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 18.02.16.
 */
public class CommentsListActivity extends ActionBarActivity
        implements CommentsListRecycleViewAdapter.CommentViewHolder.CommentViewHolderCallbacks {
    private CommentsListFragment fragment;
    private ArrayList<Comment> commentList;
    private ImageButton createComment;
    private AuthManager authManager = AuthManager.getInstance();
    private LinearLayout answerTextLayout;
    private EditText commentText;
    private Answer answer;
    private CommentsListFragment commentsFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_list_activity);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commentText = (EditText) findViewById(R.id.answerUserText);
        answerTextLayout = (LinearLayout) findViewById(R.id.answerTextLayout);
        createComment = (ImageButton) findViewById(R.id.sendAnswer);

        if (authManager.getCurrentUser() != null) {
            answerTextLayout.setVisibility(View.VISIBLE);
        }
        else {
            answerTextLayout.setVisibility(View.GONE);
        }
        if (createComment != null && commentText != null) {
            createComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        createComment(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            answer = extras.getParcelable("answer");
            commentList = answer.getComments();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        commentsFragment = CommentsListFragment.newInstance(commentList);
        fragmentTransaction.add(R.id.comments_list, commentsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, QuestionDetail.class));
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createComment(View v) throws JSONException {
        if (commentText.getText().toString().trim().equals("")) {
            commentText.setError("Comment can't be blank");
        }
        else {
            String answerTextString = commentText.getText().toString();
            JSONObject commentParams = new JSONObject();
            commentParams.put("text", answerTextString);
            commentParams.put("user_id", authManager.getCurrentUser().getId());
            commentParams.put("question_id", answer.getQuestionID());
            commentParams.put("answer_id", answer.getID());
            JSONObject params = new JSONObject();
            params.put("comment", commentParams);
            CommentFactory.getInstance().createForAnswer(answer.getQuestionID(), answer.getID(), params);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(CommentMessage event) throws JSONException {
        if (event.response instanceof JSONObject) {
            switch (event.operationName){
                case "create":
                    successCommentCreationCallback(event.response);
                    break;
            }
        } else {
            switch (event.operationName){
                case "list":
                    break;
            }
        }

    }

    private void successCommentCreationCallback(JSONObject object) {
        commentText.setText("");
        Comment comment = new Comment(object);
        commentsFragment.setNewComment(comment);
    }

    @Override
    public void onOptionsClicked(Comment comment, View itemView, MenuItem menuItem) {

    }
}
