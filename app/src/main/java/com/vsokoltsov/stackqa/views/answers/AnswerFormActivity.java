package com.vsokoltsov.stackqa.views.answers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

/**
 * Created by vsokoltsov on 13.01.16.
 */
public class AnswerFormActivity extends ActionBarActivity implements View.OnClickListener {
    private Answer answer;
    private EditText answerText;
    private Button saveButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_form_activity);
        final Activity currentActivity = this;
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        answerText = (EditText) findViewById(R.id.formText);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton.setOnClickListener((View.OnClickListener) this);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            answer = (Answer) extras.getParcelable("answer");
        }

        answerText.setText(answer.getText());
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

    @Override
    public void onClick(View view) {
        String formText = answerText.getText().toString();
        answer.setText(formText);
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra("edited_answer", answer);
        startActivity(detailIntent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
