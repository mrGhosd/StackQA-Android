package com.vsokoltsov.stackqa.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionDetailFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionDetailFragment extends Fragment{
//    public Question DETAIL_QUESTION;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Question detailQuestion;
    private View fragmentView;


    public QuestionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            detailQuestion = bundle.getParcelable("question");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_question_detail, container, false);
        if(detailQuestion != null){
            loadQuestionData(fragmentView);
            setFragmenApperance(fragmentView);
        }
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void setFragmenApperance(View view){

    }

    public void loadQuestionData(View view){
        String url = AppController.APP_HOST+"/api/v1/questions/"+detailQuestion.getID();
        JsonObjectRequest questionRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        successCallback(response, "questionDetail");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(questionRequest);
    }

    public void successCallback(JSONObject object, String requestID){
        try {
            detailQuestion = new Question(object);
            TextView textView = (TextView) fragmentView.findViewById(R.id.questionText);
            TextView rateView = (TextView) fragmentView.findViewById(R.id.questionRate);
            TextView createdAtView = (TextView) fragmentView.findViewById(R.id.questionCreatedAt);
            TextView titleView = (TextView) fragmentView.findViewById(R.id.questionTitle);
            TextView tagsView = (TextView) fragmentView.findViewById(R.id.questionTags);
            final ImageView categoryImage = (ImageView) fragmentView.findViewById(R.id.categoryImageView);

            titleView.setText(detailQuestion.getTitle());
            textView.setText(detailQuestion.getText());
            rateView.setText(String.valueOf(detailQuestion.getRate()));
            createdAtView.setText(detailQuestion.getCreatedAt());
            tagsView.setText(detailQuestion.getTags());

            Picasso.with(getActivity().getBaseContext()).load(AppController.APP_HOST + detailQuestion.getCategory().getImageUrl()).into(categoryImage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
