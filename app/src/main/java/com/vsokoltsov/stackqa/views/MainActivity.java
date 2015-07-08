package com.vsokoltsov.stackqa.views;

import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.vsokoltsov.stackqa.controllers.AppController;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public JSONObject questions;
    private static final String url = "http://178.62.198.57/api/v1/questions";
    private List<Question> questionsList = new ArrayList<Question>();
    private ListView listView;
    private QuestionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.listView);

        //Заменяем стандартный адаптер на свой
        adapter = new QuestionsListAdapter(this, questionsList);
        listView.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);
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
                                Question question = new Question();
                                question.setTitle(obj.getString("title"));
                                question.setRate(obj.getInt("rate"));


                                // adding movie to movies array
                                questionsList.add(question);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://178.62.198.57/api/v1/questions";

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            questions = response;
                            JSONArray questionsArr = response.getJSONArray("questions");
                            parseQuestionsData(questionsArr);

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void parseQuestionsData(JSONArray questions) throws JSONException{
//        TableLayout tl = (TableLayout) findViewById(R.id.);
//        for(int i = 0; i < questions.length(); i++){
//            JSONObject o = questions.getJSONObject(i);
//            TableRow row= new TableRow(this);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);
//            TextView rate = new TextView(this);
//            TextView tv = new TextView(this);
//            String title = (String)o.get("title");
////            rate.setText((String)o.get("rate"));
//            tv.setText(title);
////            row.addView(rate);
//            row.addView(tv);
//            tl.addView(row,i);
//
//        }
    }
}
