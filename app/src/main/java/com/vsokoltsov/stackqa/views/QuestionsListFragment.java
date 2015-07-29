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
import android.app.ListFragment;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.models.QuestionsList;

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
    private static final String url = "http://178.62.198.57/api/v1/questions";
    public QuestionsListAdapter adapter;
    public ListView list;

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    private Callbacks listCallbacks = questionsCallbacks;

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
        public void onItemSelected(String id) {
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new QuestionsListAdapter(getActivity(), questionsList);
        setListAdapter(adapter);
        JsonObjectRequest movieReq = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        JSONArray questionsArr = null;
                        try {
                            questionsArr = response.getJSONArray("questions");
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
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(movieReq);
    }
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        listCallbacks.onItemSelected(String.valueOf(questionsList.get(position).getID()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

}
