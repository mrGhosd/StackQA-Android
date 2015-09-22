package com.vsokoltsov.stackqa.views;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.ListFragment;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.models.QuestionsList;
import com.vsokoltsov.stackqa.models.ServerConnection;
import com.vsokoltsov.stackqa.models.ServerConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class QuestionsListFragment extends ListFragment {
    private Activity activity;
    private List<Question> questionsList = new ArrayList<Question>();
    private static final String url = AppController.APP_HOST + "/api/v1/questions";
    public QuestionsListAdapter adapter;
    public ListView list;

    private static List<Question> cachedQuestionsList = new ArrayList<Question>();


    private Callbacks listCallbacks = questionsCallbacks;

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

    private static void setCachedQuestionsList(List<Question> list){
        cachedQuestionsList = list;
    }

    private static List<Question> getCachedQuestionsList(){
        return cachedQuestionsList;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) getListView();
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
        questionsList = getCachedQuestionsList();
        adapter = new QuestionsListAdapter(getActivity(), questionsList);
        setListAdapter(adapter);
        if(questionsList.size() <= 0) {
            JsonObjectRequest movieReq = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            successCallback(response, "questionsList");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                }
            });
            AppController.getInstance().addToRequestQueue(movieReq);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

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

    public void successCallback(JSONObject object, String requestID){
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
    }

    public void errorCallback(VolleyError error, String requestID){

    }

}
