package com.vsokoltsov.stackqa.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v4.app.ListFragment;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.messages.SuccessRequestMessage;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.network.ApiRequest;
import com.vsokoltsov.stackqa.network.RequestCallbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;

import de.greenrobot.event.EventBus;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class QuestionsListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener, RequestCallbacks {

    private SwipeRefreshLayout swipeLayout;
    private CircularProgressView progressView;
    private ProgressBar mProgress;
    private Activity activity;
    private View listFragmentView;
    private List<Question> questionsList = new ArrayList<Question>();
    private static final String url = AppController.APP_HOST + "/api/v1/questions";
    public QuestionsListAdapter adapter;
    public ListView list;

    private static List<Question> cachedQuestionsList = new ArrayList<Question>();


    private Callbacks listCallbacks = questionsCallbacks;

    private static void setCachedQuestionsList(List<Question> list){
        cachedQuestionsList = list;
    }

    private static List<Question> getCachedQuestionsList(){
        return cachedQuestionsList;
    }

    public QuestionsListFragment() {
    }

    @Override
    public void onRefresh() {
        cachedQuestionsList = new ArrayList<Question>();
        questionsList = new ArrayList<Question>();
        this.loadQuestionsList();
    }

    @Override
    public void successCallback(String requestName, JSONObject object) {

    }

    @Override
    public void failureCallback(String requestName, VolleyError error) {

    }

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Question question);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        listCallbacks = (Callbacks) activity;
    }

    private static Callbacks questionsCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Question question) {
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) getListView();
        mProgress = (ProgressBar) view.findViewById(R.id.progress_bar);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listCallbacks.onItemSelected(questionsList.get(position));
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.loadQuestionsList();
    }

    private void loadQuestionsList(){
        questionsList = getCachedQuestionsList();
        adapter = new QuestionsListAdapter(getActivity(), questionsList);
        setListAdapter(adapter);
        if(questionsList.size() <= 0) {
            if(mProgress != null){
                mProgress.setVisibility(View.VISIBLE);
            }
            Question.getCollection();
        } else {
            if(mProgress != null){
                mProgress.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeLayout.setOnRefreshListener(listener);
    }

    public boolean isRefreshing() {
        return swipeLayout.isRefreshing();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listFragmentView = super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_question_list,
                container, false);
        mProgress = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        swipeLayout = new ListFragmentSwipeRefreshLayout(container.getContext());
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.addView(listFragmentView);
        return swipeLayout;

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

    public void errorCallback(VolleyError error, String requestID){
        swipeLayout.setRefreshing(false);
    }


    private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {

        public ListFragmentSwipeRefreshLayout(Context context) {
            super(context);
        }

        /**
         * As mentioned above, we need to override this method to properly signal when a
         * 'swipe-to-refresh' is possible.
         *
         * @return true if the {@link android.widget.ListView} is visible and can scroll up.
         */
        @Override
        public boolean canChildScrollUp() {
            final ListView listView = getListView();
            if (listView.getVisibility() == View.VISIBLE) {
                return canListViewScrollUp(listView);
            } else {
                return false;
            }
        }

    }

    private boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            // For ICS and above we can call canScrollVertically() to determine this
            return ViewCompat.canScrollVertically(listView, -1);
        } else {
            // Pre-ICS we need to manually check the first visible item and the child view's top
            // value
            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(QuestionMessage event){
        switch (event.operationName){
            case "list":
                parseQuestionsList(event.response);
                break;
        }
    }

    private void parseQuestionsList(JSONObject object){
        JSONArray questionsArr = null;
        try {
            questionsArr = object.getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parsing json
        for (int i = 0; i < questionsArr.length(); i++) {
            try {
                JSONObject obj = questionsArr.getJSONObject(i);
                Question question = new Question(obj);
                questionsList.add(question);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        setCachedQuestionsList(questionsList);
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        mProgress = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        if(mProgress != null){
            mProgress.setVisibility(View.INVISIBLE);
        }

    }
}
