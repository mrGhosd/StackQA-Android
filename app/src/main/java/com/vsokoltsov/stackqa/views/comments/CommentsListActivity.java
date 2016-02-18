package com.vsokoltsov.stackqa.views.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.Comment;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

import java.util.ArrayList;

/**
 * Created by vsokoltsov on 18.02.16.
 */
public class CommentsListActivity extends ActionBarActivity {
    private CommentsListFragment fragment;
    private ArrayList<Comment> commentList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_list_activity);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Answer answer = extras.getParcelable("answer");
            commentList = answer.getComments();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CommentsListFragment commentsFragment = CommentsListFragment.newInstance(commentList);
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

}
