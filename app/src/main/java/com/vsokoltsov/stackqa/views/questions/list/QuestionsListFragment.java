package com.vsokoltsov.stackqa.views.questions.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.RVAdapter;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.network.RequestCallbacks;
import com.vsokoltsov.stackqa.receiver.StartedService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class QuestionsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestCallbacks {

    private SwipeRefreshLayout swipeLayout;
    private CircularProgressView progressView;
    private ProgressBar mProgress;
    private Activity activity;
    private View listFragmentView;
    private List<Question> questionsList = new ArrayList<Question>();
    private static final String url = AppController.APP_HOST + "/api/v1/questions";
    public static QuestionsListAdapter adapter;
    private AuthManager manager = AuthManager.getInstance();
    private RVAdapter cardAdapter;
    public ListView list;
    private RecyclerView rv;
    private static List<Question> cachedQuestionsList = new ArrayList<Question>();
    private Question updatedQuestion;


    private Callbacks listCallbacks = questionsCallbacks;

    private static void setCachedQuestionsList(List<Question> list){
        cachedQuestionsList = list;
    }

    private static List<Question> getCachedQuestionsList(){
        return cachedQuestionsList;
    }

    public QuestionsListFragment() {
    }

    public ListView getList() {
        return this.list;
    }

    public QuestionsListAdapter getAdapter(){
        return this.adapter;
    }

    public void setAdapter(QuestionsListAdapter adapter){
       this.adapter = adapter;
    }

    public ArrayList<Question> getQuestionsFromList(){
        return (ArrayList<Question>) this.questionsList;
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(bundle != null){
            questionsList= bundle.getParcelableArrayList("questions");
            setCustomAdapter();
//            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
//            mProgress = getProgressBar();
//            if(mProgress != null){
//                mProgress.setVisibility(View.GONE);
//            }
        }

    }

    private void loadQuestionsList(){
//        mProgress = getProgressBar();
//        if(mProgress != null){
//            mProgress.setVisibility(View.VISIBLE);
//        }
        questionsList = getCachedQuestionsList();
        cardAdapter = new RVAdapter(questionsList, getActivity());
        rv.setAdapter(cardAdapter);
//        mProgress = getProgressBar();
        if(questionsList.size() <= 0) {
            Question q = new Question();
            q.getCollection();
        } else {
//            if(mProgress != null){
//                mProgress.setVisibility(View.GONE);
//            }
            cardAdapter.notifyDataSetChanged();
        }
    }

    private void setCustomAdapter() {
        if(getAdapter() == null) setAdapter(new QuestionsListAdapter(getActivity(), questionsList));
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
//        mProgress = getProgressBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listFragmentView = super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_question_list,
                container, false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);
        rv = (RecyclerView) rootView.findViewById(R.id.questionsList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
//        mProgress = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        swipeLayout.setOnRefreshListener(this);
        this.loadQuestionsList();
        return rootView;

    }

    public void errorCallback(VolleyError error, String requestID){
        swipeLayout.setRefreshing(false);
    }


//    private boolean canListViewScrollUp(RecyclerView listView) {
//        if (android.os.Build.VERSION.SDK_INT >= 14) {
//            // For ICS and above we can call canScrollVertically() to determine this
//            return ViewCompat.canScrollVertically(listView, -1);
//        } else {
//            // Pre-ICS we need to manually check the first visible item and the child view's top
//            // value
//            return listView.getChildCount() > 0 &&
//                    (listView.getFirstVisiblePosition() > 0
//                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
//        }
//    }

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
        if (event.response instanceof JSONObject) {
            switch (event.operationName){
                case "list":
                    parseQuestionsList(event.response);
                    break;
            }
        } else {
            switch (event.operationName){
                case "list":
                    handleQuestionListError(event.error);
                    break;
            }
        }

    }

    private void handleQuestionListError(VolleyError error){
        swipeLayout.setRefreshing(false);
        String message = null;
        switch(error.networkResponse.statusCode){
            default:
                message = "Server does not response. Try request later.";
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setTitle("Error")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        dialog.cancel();
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
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
        if (manager.getCurrentUser() == null) {
            startSignUpService();
        }
        setCachedQuestionsList(questionsList);
        cardAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }

    public void startSignUpService() {
        Intent service= new Intent(getActivity().getBaseContext(), StartedService.class);
        // potentially add data to the intent
        getActivity().getBaseContext().startService(service);
    }

    private ProgressBar getProgressBar(){
        return (ProgressBar) getActivity().findViewById(R.id.progress_bar);
    }
}
