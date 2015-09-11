package com.vsokoltsov.stackqa.views;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.vsokoltsov.stackqa.views.QuestionDetailFragment;
import com.vsokoltsov.stackqa.models.Question;

import com.vsokoltsov.stackqa.R;

public class QuestionDetail extends ActionBarActivity {
    public Question selectedQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedQuestion = (Question) getIntent().getExtras().getParcelable("question");
        setContentView(R.layout.activity_question_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(selectedQuestion.getTitle());

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable("question", selectedQuestion);
            QuestionDetailFragment fragment = new QuestionDetailFragment();
            fragment.setArguments(arguments);
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.detail_fragment, fragment);
            fragmentTransaction.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, QuestionsListActivity.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
