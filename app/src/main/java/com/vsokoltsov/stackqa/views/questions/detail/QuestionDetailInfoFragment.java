package com.vsokoltsov.stackqa.views.questions.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionDetailPagerAdapter;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.util.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class QuestionDetailInfoFragment extends Fragment {
    public Question detailQuestion;
    private View fragmentView;
    ViewPager pager;
    QuestionDetailPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Answers","Comments"};
    int Numboftabs =2;
    LinearLayout ll;
    private ArrayList<Answer> answersList = new ArrayList<Answer>();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
//        fragmentView =  inflater.inflate(R.layout.question_detail_info_fragment, container, false);
        ll = (LinearLayout) inflater.inflate(R.layout.question_detail_info_fragment, container, false);
        // Creating The Toolbar and setting it as the Toolbar for the activity

        Bundle extras = getArguments();
        if(extras != null) {
            try {
                setAnswerListData(new JSONArray(extras.getString("answers")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ll;
    }

    private void setAnswerListData(JSONArray answers) throws JSONException {
        for (int i = 0; i < answers.length(); i++) {
            Answer answer = new Answer(answers.getJSONObject(i));
            answersList.add(answer);
        }
    }
}
