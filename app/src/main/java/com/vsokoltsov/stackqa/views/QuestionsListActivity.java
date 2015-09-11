package com.vsokoltsov.stackqa.views;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.models.Question;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.view.View;

public class QuestionsListActivity extends FragmentActivity implements QuestionsListFragment.Callbacks{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void detailQuestionView(View v){
    }

    @Override
    public void onItemSelected(Question question) {
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra("question", question);
        startActivity(detailIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
