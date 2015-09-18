package com.vsokoltsov.stackqa.views;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.views.QuestionDetailFragment;
import com.vsokoltsov.stackqa.models.Question;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.views.answers.AnswerListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionDetail extends ActionBarActivity {
    public Question selectedQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedQuestion = (Question) getIntent().getExtras().getParcelable("question");
        try {
            setContentView(R.layout.activity_question_detail);
        } catch(Exception e){
            e.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(selectedQuestion.getTitle());

        if (savedInstanceState == null) {
            loadQuestionData();
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

        }

    }

    public void loadQuestionData(){
        String url = AppController.APP_HOST+"/api/v2/questions/"+selectedQuestion.getID();
        JsonObjectRequest questionRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        successCallback(response, "questionDetail");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(questionRequest);
    }

    public void successCallback(JSONObject response, String requestTag){
        Question question = new Question(response);
        Bundle arguments = new Bundle();
        arguments.putParcelable("question", question);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        loadMainQuestionFragment(arguments, fragmentTransaction);
        try {
            loadQuestionAnswersFragment(arguments, fragmentTransaction, response.getJSONArray("answers"));
        } catch(JSONException e){
            e.printStackTrace();
        }
        fragmentTransaction.commit();
    }

    public void loadMainQuestionFragment(Bundle arguments, FragmentTransaction fragmentTransaction){
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        fragment.setArguments(arguments);
        fragmentTransaction.add(R.id.detail_fragment, fragment);
    }

    public void loadQuestionAnswersFragment(Bundle arguments, FragmentTransaction fragmentTransaction, JSONArray answers){
        AnswerListFragment fragment = AnswerListFragment.newInstance(answers);
        fragmentTransaction.add(R.id.answers_list, fragment);
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
