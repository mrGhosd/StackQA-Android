package com.vsokoltsov.stackqa.views.comments;

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
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.CommentsListRecycleViewAdapter;
import com.vsokoltsov.stackqa.models.Comment;
import com.vsokoltsov.stackqa.util.MaterialProgressBar;
import com.vsokoltsov.stackqa.util.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class CommentsListFragment extends Fragment {
    private View fragmentView;
    private ListView list;
    private List<Comment> commentsList = new ArrayList<Comment>();
    public CommentsListRecycleViewAdapter commentAdapter;
    public  CardView commentsListWrapper;
    public TextView emptyListView;
    public MaterialProgressBar progressBar;
    private RecyclerView rv;
    private LinearLayoutManager llm;

    public void setCommentList(JSONArray comments) {
        for(int i = 0; i < comments.length(); i++){
            try {
                Comment comment = new Comment(comments.getJSONObject(i));
                commentsList.add(comment);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        if (commentAdapter != null) {
            commentAdapter.notifyDataSetChanged();
        }
    }

    public static CommentsListFragment newInstance(JSONArray comments) {
        CommentsListFragment f = new CommentsListFragment();
        f.setCommentList(comments);
        return f;
    }

    public static CommentsListFragment newInstance(ArrayList<Comment> comments) {
        CommentsListFragment f = new CommentsListFragment();
        f.commentsList = comments;
        return f;
    }

    public void setNewComment(Comment comment) {
        int index = commentAdapter.comments.size();
        if (index == 0) {
            commentsListWrapper.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
        commentsList.add(0, comment);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        fragmentView =  inflater.inflate(R.layout.comments_list_fragment, container, false);
        commentsListWrapper = (CardView) fragmentView.findViewById(R.id.commentsListWrapper);
        emptyListView = (TextView) fragmentView.findViewById(R.id.empty_view);
        progressBar = (MaterialProgressBar) fragmentView.findViewById(R.id.progress_bar);
        rv = (RecyclerView) fragmentView.findViewById(R.id.commentsList);
        rv.setHasFixedSize(true);
        commentAdapter = new CommentsListRecycleViewAdapter(commentsList, getActivity());
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
            rv.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        }
        else {
            llm.setOrientation(orientation);
        }
        if (commentsList.size() == 0) {
            commentsListWrapper.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }
        else {
            commentsListWrapper.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
//        list = (ListView) fragmentView.findViewById(android.R.id.list);
//        adapter = new AnswersListAdapter(getActivity(), answerList);
//        setListAdapter(adapter);
//        adapter.notifyDataSetChanged();
        return fragmentView;
    }

}
