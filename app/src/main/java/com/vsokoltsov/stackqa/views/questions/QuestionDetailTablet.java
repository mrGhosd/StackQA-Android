package com.vsokoltsov.stackqa.views.questions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.QuestionDetailFragment;
import com.vsokoltsov.stackqa.views.QuestionsListActivity;
import com.vsokoltsov.stackqa.views.answers.AnswerForm;
import com.vsokoltsov.stackqa.views.answers.AnswerListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vsokoltsov on 20.12.15.
 */
public class QuestionDetailTablet extends ActionBarActivity {
    public static Question selectedQuestion;
    private ScrollView layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                selectedQuestion = (Question) extras.getParcelable("question");
            }
            setContentView(R.layout.activity_question_detail);
//            setViewLayout((ScrollView) findViewById(R.id.questionViewMainLayout));
//            EditText answerText = (EditText) findViewById(R.id.answerUserText);
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//            setSuccesButtonHandler(answerText);
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(selectedQuestion.getTitle());
//
//        if (savedInstanceState == null) {
//            if(selectedQuestion != null) {
//                loadQuestionData();
//            }
//            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//
//        }

    }

    public void setLayoutHeight(int height){
        try {
            ViewGroup.LayoutParams params = this.layout.getLayoutParams();
            params.height += height;
            this.layout.setLayoutParams(params);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getLayoutHeight(){
        return this.layout.getLayoutParams().height;
    }

    public void setViewLayout(ScrollView layout){
        this.layout = layout;
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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, QuestionsListActivity.class));
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
            case R.id.add_answer:
                showAnswerForm();
                return true;
            case R.id.add_comment:
                showCommentForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAnswerForm(){
        try {
            Intent detailIntent = new Intent(this, AnswerForm.class);
//            detailIntent.putExtra("question", selectedQuestion);
            startActivity(detailIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showCommentForm(){

    }

    private void setSuccesButtonHandler(EditText answerText){
        answerText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });
    }
}
