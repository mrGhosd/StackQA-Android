package com.vsokoltsov.stackqa.views.questions.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.OnLoadMoreListener;
import com.vsokoltsov.stackqa.adapters.RVAdapter;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.models.QuestionFactory;
import com.vsokoltsov.stackqa.network.RequestCallbacks;
import com.vsokoltsov.stackqa.receiver.StartedService;
import com.vsokoltsov.stackqa.util.MaterialProgressBar;

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
public class QuestionsListFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener, RequestCallbacks, SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    private static MaterialProgressBar progressBar;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar mProgress;
    private Activity activity;
    private View listFragmentView;
    private List<Question> questionsList = new ArrayList<Question>();
    private static final String url = AppController.APP_HOST + "/api/v1/questions";
    private AuthManager manager = AuthManager.getInstance();
    private RVAdapter cardAdapter;
    public ListView list;
    private RecyclerView rv;
    private static List<Question> cachedQuestionsList = new ArrayList<Question>();
    private Question updatedQuestion;
    private int pageNumber = 1;
    private TextView emptyList;
    private SearchView mSearchView;
    private Menu mainMenu;


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

    public ArrayList<Question> getQuestionsFromList(){
        return (ArrayList<Question>) this.questionsList;
    }

    @Override
    public void onRefresh() {
        cachedQuestionsList = new ArrayList<Question>();
        questionsList = new ArrayList<Question>();
        try {
            this.loadQuestionsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        setHasOptionsMenu(true);
        getActivity().setTitle(getResources().getString(R.string.questions));
        Bundle bundle = getArguments();
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(bundle != null){
            questionsList= bundle.getParcelableArrayList("questions");
        }

    }

    private void setVisibilityToProgressBar(int view) {
        if (progressBar != null) progressBar.setVisibility(view);
    }

    private void loadQuestionsList() throws JSONException {
        setVisibilityToProgressBar(View.VISIBLE);
        questionsList = getCachedQuestionsList();
        cardAdapter = new RVAdapter(questionsList, getActivity(), rv, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNumber++;
                QuestionFactory.getInstance().getCollection(pageNumber);
            }
        });
        rv.setAdapter(cardAdapter);
        if(questionsList.size() <= 0) {
            QuestionFactory.getInstance().getCollection(pageNumber);
        } else {
            setVisibilityToProgressBar(View.GONE);
            cardAdapter.notifyDataSetChanged();
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
        progressBar = (MaterialProgressBar) getActivity().findViewById(R.id.progress_bar);
        View rootView = inflater.inflate(R.layout.fragment_question_list,
                container, false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);
        rv = (RecyclerView) rootView.findViewById(R.id.questionsList);
        rv.setHasFixedSize(true);
        emptyList = (TextView) rootView.findViewById(R.id.empty_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        swipeLayout.setOnRefreshListener(this);
        try {
            this.loadQuestionsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;

    }

    public void errorCallback(VolleyError error, String requestID){
        swipeLayout.setRefreshing(false);
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
        cardAdapter.removeItem(null);
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

        setVisibilityToProgressBar(View.GONE);
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

        setVisibilityToProgressBar(View.GONE);
        setCachedQuestionsList(questionsList);
        cardAdapter.removeItem(null);
        cardAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        setVisibiltyOfList();
    }

    private void setVisibiltyOfList() {
        if (questionsList.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
            rv.setVisibility(View.INVISIBLE);
        }
        else {
            rv.setVisibility(View.VISIBLE);
            emptyList.setVisibility(View.GONE);
        }
    }

    public void startSignUpService() {
        Intent service= new Intent(getActivity().getBaseContext(), StartedService.class);
        // potentially add data to the intent
        getActivity().getBaseContext().startService(service);
    }

    private ProgressBar getProgressBar(){
        return (ProgressBar) getActivity().findViewById(R.id.progress_bar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem searchItem = menu.findItem(R.id.search_quesitions);
        if (searchItem != null) {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        cardAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onClose() {
        return true;
    }

    private void resetSearchInput() {
        questionsList = getCachedQuestionsList();
        cardAdapter.notifyDataSetChanged();
    }

    private List<Question> filter(List<Question> models, String query) {
        query = query.toLowerCase();

        final List<Question> filteredModelList = new ArrayList<>();
        for (Question model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void setupSearchView(MenuItem searchItem) {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
//        if (isTablet) {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            if (searchManager != null) {
                List searchables = searchManager.getSearchablesInGlobalSearch();

                SearchableInfo info = searchManager.getSearchableInfo(getActivity().getComponentName());
                for (int i = 0; i < searchables.size(); i++) {
                    if (searchables.get(i) != null) {
                        info = (SearchableInfo) searchables.get(i);
                    }
                }
            }
            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            QuestionsListFragment frg = (QuestionsListFragment) fragmentManager.findFragmentById(R.id.container);
            final ListView questionsList = frg.getList();
            final RecyclerView questionListView = rv;
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }
//    }


}
