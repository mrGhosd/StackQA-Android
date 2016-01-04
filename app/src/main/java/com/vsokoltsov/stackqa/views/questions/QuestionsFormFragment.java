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
import com.vsokoltsov.stackqa.adapters.CategoriesTextFieldAdapter;
import com.vsokoltsov.stackqa.messages.CategoryMessage;
import com.vsokoltsov.stackqa.models.Category;
import com.vsokoltsov.stackqa.util.InstantAutoCompleteView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 03.01.16.
 */
public class QuestionsFormFragment extends Fragment {
    private View fragmentView;
    private Menu formMenu;
    private List<Category> categoryList = new ArrayList<Category>();
    private InstantAutoCompleteView completeTextView;
    private CategoriesTextFieldAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Category c = new Category();
        c.getCollection();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.question_form_fragment, container, false);
       completeTextView = (InstantAutoCompleteView) fragmentView.findViewById(R.id.questionCategory);
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
    public void onEvent(CategoryMessage event){
        if (event.response instanceof JSONObject) {
            switch (event.operationName){
                case "categories_collection":
                    parseCategoriesData(event.response);
                    break;
            }
        } else {
            switch (event.operationName){
                case "categories_collection":

                    break;
            }
        }

    }

    private void parseCategoriesData(JSONObject response) {
        JSONArray categoriesArr = null;
        try {
            categoriesArr = response.getJSONArray("categories");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < categoriesArr.length(); i++) {
            try {
                JSONObject obj = categoriesArr.getJSONObject(i);
                Category category = new Category(obj);
                categoryList.add(category);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        addCategoriesToCompleteTextField();
    }

    private void addCategoriesToCompleteTextField() {
        adapter = new CategoriesTextFieldAdapter(getActivity().getBaseContext(),
                R.layout.category_autocomplete_item, (ArrayList<Category>) categoryList);
        completeTextView.setAdapter(adapter);
    }
}
