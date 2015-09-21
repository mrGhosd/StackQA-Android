package com.vsokoltsov.stackqa.views.answers;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.views.QuestionDetail;
import com.vsokoltsov.stackqa.views.QuestionsListActivity;

public class AnswerForm extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(selectedQuestion.getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    NavUtils.navigateUpTo(this, new Intent(this, QuestionDetail.class));
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    return true;
                } catch(Exception e){
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
