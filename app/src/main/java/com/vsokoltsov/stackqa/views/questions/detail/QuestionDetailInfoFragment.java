package com.vsokoltsov.stackqa.views.questions.detail;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionDetailPagerAdapter;
import com.vsokoltsov.stackqa.adapters.QuestionDetailViewPager;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.Comment;
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
    QuestionDetailViewPager pager;
    QuestionDetailPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Answers","Comments"};
    int Numboftabs =2;
    LinearLayout ll;
    private ArrayList<Answer> answersList = new ArrayList<Answer>();
    private ArrayList<Comment> commentsList = new ArrayList<Comment>();

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
                setCommentListData(new JSONArray(extras.getString("comments")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter =  new QuestionDetailPagerAdapter(getActivity().getSupportFragmentManager(),
                Titles, Numboftabs, answersList, commentsList);

        // Assigning ViewPager View and setting the adapter
        pager = (QuestionDetailViewPager) ll.findViewById(R.id.questionDetailPager);
        pager.setAdapter(adapter);
        pager.setLayoutMode(View.MEASURED_SIZE_MASK);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) ll.findViewById(R.id.tabs);
        tabs.setBackground(new ColorDrawable(R.color.highlighted_text_material_light));
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        return ll;
    }

    private void setAnswerListData(JSONArray answers) throws JSONException {
        for (int i = 0; i < answers.length(); i++) {
            Answer answer = new Answer(answers.getJSONObject(i));
            answersList.add(answer);
        }
    }

    private void setCommentListData(JSONArray comments) throws JSONException {
        for (int i = 0; i < comments.length(); i++) {
            Comment comment = new Comment(comments.getJSONObject(i));
            commentsList.add(comment);
        }
    }
}
