package com.vsokoltsov.stackqa.views.comments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vsokoltsov.stackqa.R;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class CommentsListFragment extends Fragment {
    private View fragmentView;
    private ListView list;
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
//        list = (ListView) fragmentView.findViewById(android.R.id.list);
//        adapter = new AnswersListAdapter(getActivity(), answerList);
//        setListAdapter(adapter);
//        adapter.notifyDataSetChanged();
        return fragmentView;
    }

}
