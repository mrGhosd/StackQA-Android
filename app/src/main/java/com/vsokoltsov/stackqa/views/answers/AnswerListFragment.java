package com.vsokoltsov.stackqa.views.answers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.AnswersListAdapter;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p
 * interface.
 */
public class AnswerListFragment extends ListFragment {
    private Activity activity;
    private QuestionDetail mainActivity;
    private List<Answer> answerList = new ArrayList<Answer>();
    public AnswersListAdapter adapter;
    public ListView list;
    private View fragmentView;

    public void setAnswerList(JSONArray answers){
        for(int i = 0; i < answers.length(); i++){
            try {
                Answer answer = new Answer(answers.getJSONObject(i));
                answerList.add(answer);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        try{
            this.mainActivity = (QuestionDetail) getActivity();
            setListViewHeightBasedOnChildren(list);
//            this.mainActivity.setLayoutHeight(3000);
//            Activity mainAcitivty = qView.getParent();
//            View relativeLayput = activity.findViewById(R.id.questionViewMainLayout);
//            int scViewheight = qD.getHeight();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
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
        list = (ListView) fragmentView.findViewById(android.R.id.list);
        adapter = new AnswersListAdapter(getActivity(), answerList);
        setListAdapter(adapter);
        return fragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Do your stuff..
    }



    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            TextView answerText = (TextView) listItem.findViewById(R.id.answerText);
            listItem.measure(0, 0);
            float answerTextHeight = answerText.getTextSize();
            float px = 550 * (listView.getResources().getDisplayMetrics().density);
            listItem.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight() + answerTextHeight + listItem.getHeight() + 30;
        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        WindowManager manager = (WindowManager) listView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display d = manager.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        int height = size.x;
        params.height = totalHeight;
//        listView.setLayoutParams(params);
    }
}
