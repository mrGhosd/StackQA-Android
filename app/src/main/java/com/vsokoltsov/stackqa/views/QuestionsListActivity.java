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


//        listView = (ListView) findViewById(R.id.listView);
//
//        //Заменяем стандартный адаптер на свой
//        adapter = new QuestionsListAdapter(this, questionsList);
//        listView.setAdapter(adapter);

//        JsonObjectRequest movieReq = new JsonObjectRequest(url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("TAG", response.toString());
//                        JSONArray questionsArr = null;
//                        try {
//                            questionsArr = response.getJSONArray("questions");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        // Parsing json
//                        for (int i = 0; i < questionsArr.length(); i++) {
//                            try {
//                                JSONObject obj = questionsArr.getJSONObject(i);
//                                Question question = new Question();
//                                question.setTitle(obj.getString("title"));
//                                question.setRate(obj.getInt("rate"));
//                                question.setCategory(obj.getJSONObject("category"));
//                                question.setCreatedAt(obj.getString("created_at"));
//                                question.setAnswersCount(obj.getInt("answers_count"));
//                                question.setCommentsCount(obj.getInt("comments_count"));
//                                question.setViews(obj.getInt("views"));
//                                // adding movie to movies array
//                                questionsList.add(question);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
//                        adapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("TAG", "Error: " + error.getMessage());
//
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(movieReq);

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
