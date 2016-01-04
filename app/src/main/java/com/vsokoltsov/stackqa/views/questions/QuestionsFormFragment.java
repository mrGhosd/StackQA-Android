package com.vsokoltsov.stackqa.views.questions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vsokoltsov.stackqa.R;

/**
 * Created by vsokoltsov on 03.01.16.
 */
public class QuestionsFormFragment extends Fragment {
    private View fragmentView;
    private Menu formMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.question_form_fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        formMenu = menu;
        inflater.inflate(R.menu.menu_question_form, formMenu);
        setFragmentButtons(formMenu);

    }

    private void setFragmentButtons(Menu menu) {
        MenuItem cancelItem = menu.findItem(R.id.cancelForm);
        MenuItem saveItem = menu.findItem(R.id.saveForm);

        cancelItem.setVisible(true);
        saveItem.setVisible(true);
        cancelItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        saveItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
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
}
