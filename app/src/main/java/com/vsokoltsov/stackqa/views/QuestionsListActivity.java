package com.vsokoltsov.stackqa.views;

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

import java.util.ArrayList;
import java.util.List;
import android.view.View;

public class QuestionsListActivity extends FragmentActivity implements QuestionsListFragment.Callbacks{

    public JSONObject questions;
    private static final String url = "http://178.62.198.57/api/v1/questions";
    private List<Question> questionsList = new ArrayList<Question>();
    private ListView listView;
    private QuestionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().add(R.id.list, new QuestionsListFragment());
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.list, new Questions)
//                    .commit();
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
        Intent intent = new Intent(QuestionsListActivity.this, QuestionDetail.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(String id) {
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra(QuestionDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }
}
