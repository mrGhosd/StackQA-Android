package com.vsokoltsov.stackqa.views.answers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.AnswersListRecycleViewAdapter;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.util.MyLinearLayoutManager;
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
    private List<Answer> answerList = new ArrayList<Answer>();

    public ListView list;
    private View fragmentView;
    private RecyclerView rv;
    private MyLinearLayoutManager llm;
    private AnswersListRecycleViewAdapter answerAdapter;

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

    public static AnswerListFragment newInstance(ArrayList<Answer> answerList) {
        AnswerListFragment f = new AnswerListFragment();
        f.answerList = answerList;
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
//            setListViewHeightBasedOnChildren(list);
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
        rv = (RecyclerView) fragmentView.findViewById(R.id.answersList);
        rv.setHasFixedSize(true);
        answerAdapter = new AnswersListRecycleViewAdapter(answerList, getActivity());
        LinearLayoutManager llm = new org.solovyev.android.views.llm.LinearLayoutManager(getActivity().getApplicationContext(), 1, false);
        rv.setLayoutManager(llm);
        rv.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getBaseContext()));
        rv.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();
        return fragmentView;
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
            float answerTextValue = answerText.getMeasuredHeight();
            float answerTextHeight = answerText.getMeasuredHeight();
            float px = 660 * (listView.getResources().getDisplayMetrics().density);
            listItem.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight() + answerTextHeight + answerText.getMeasuredWidth()/5;
        }

        CardView detaiLCardView = (CardView) getActivity().findViewById(R.id.detailInforCardView);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        LinearLayout.LayoutParams frameParams = (LinearLayout.LayoutParams) detaiLCardView.getLayoutParams();
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        WindowManager manager = (WindowManager) listView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display d = manager.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        if (listAdapter.getCount() < 2 && totalHeight < 300) {
            totalHeight += 90;
        }
        params.height = totalHeight;
        frameParams.height = totalHeight;
        detaiLCardView.setLayoutParams(frameParams);
        listView.setLayoutParams(params);
    }
}
