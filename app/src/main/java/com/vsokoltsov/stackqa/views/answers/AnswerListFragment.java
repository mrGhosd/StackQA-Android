package com.vsokoltsov.stackqa.views.answers;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.AnswersListRecycleViewAdapter;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.util.MaterialProgressBar;
import com.vsokoltsov.stackqa.util.SimpleDividerItemDecoration;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p
 * interface.
 */
public class AnswerListFragment extends Fragment {
    private Activity activity;
    private QuestionDetail mainActivity;
    public List<Answer> answerList = new ArrayList<Answer>();

    public ListView list;
    private View fragmentView;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    public AnswersListRecycleViewAdapter answerAdapter;
    private TextView emptyView;
    private CardView answersListWrapper;
    private TextView emptyListView;
    public MaterialProgressBar progressBar;

    public void setAnswerList(JSONArray answers){
        for(int i = 0; i < answers.length(); i++){
            try {
                Answer answer = new Answer(answers.getJSONObject(i));
                answerList.add(answer);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        if (answerAdapter != null) {
            answerAdapter.notifyDataSetChanged();
        }
    }

    public MaterialProgressBar getProgressBar() {
        return progressBar;
    }

    public RecyclerView getRv() {
        return rv;
    }

    public int getAnswersListSize() {
        return this.answerList.size();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public static AnswerListFragment newInstance(JSONArray answers) {
        AnswerListFragment f = new AnswerListFragment();
        f.setAnswerList(answers);
        return f;
    }

    public static AnswerListFragment newInstance(ArrayList<Answer> answerList) {
        AnswerListFragment f = new AnswerListFragment();
        f.answerList = answerList;
        return f;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void setNewAnswer(Answer answer) {
        int index = answerAdapter.answers.size();
        if (index == 0) {
            answersListWrapper.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
        answerList.add(0, answer);
        answerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (QuestionDetail) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView =  inflater.inflate(R.layout.fragment_answer_list, container, false);
        answersListWrapper = (CardView) fragmentView.findViewById(R.id.answersListWrapper);
        emptyListView = (TextView) fragmentView.findViewById(R.id.empty_view);
        progressBar = (MaterialProgressBar) fragmentView.findViewById(R.id.progress_bar);
        rv = (RecyclerView) fragmentView.findViewById(R.id.answersList);
        rv.setHasFixedSize(true);
        answerAdapter = new AnswersListRecycleViewAdapter(answerList, getActivity());
        int orientation;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = LinearLayout.HORIZONTAL;
        } else {
            orientation = LinearLayout.VERTICAL;
        }
        if (llm == null) {
            llm =
                    new org.solovyev.android.views.llm.LinearLayoutManager(getActivity().getApplicationContext(),
                            1,
                            true);
            rv.setLayoutManager(llm);
            rv.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getBaseContext()));
            rv.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();
        }
        else {
            llm.setOrientation(orientation);
        }
        if (answerList.size() == 0) {
            answersListWrapper.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }
        else {
            answersListWrapper.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
        return fragmentView;
    }



    public void setNewAnswerItem(Answer answer) {
        ScrollView sc = (ScrollView) getActivity().findViewById(R.id.questionViewMainLayout);

        int index = answerAdapter.answers.size();
        answerAdapter.answers.add(index, answer);

        if (index != 0) {
            rv.setAlpha((float) 1.0);
            progressBar.setVisibility(View.GONE);
        }
        else {
            answersListWrapper.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
        answerAdapter.notifyDataSetChanged();
        sc.scrollTo(0, 0);
    }
}
