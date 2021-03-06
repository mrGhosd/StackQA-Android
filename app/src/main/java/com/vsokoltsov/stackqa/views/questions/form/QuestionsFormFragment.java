package com.vsokoltsov.stackqa.views.questions.form;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.CategoriesTextFieldAdapter;
import com.vsokoltsov.stackqa.messages.CategoryMessage;
import com.vsokoltsov.stackqa.messages.QuestionMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Category;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.models.QuestionFactory;
import com.vsokoltsov.stackqa.util.InstantAutoCompleteView;
import com.vsokoltsov.stackqa.util.MaterialProgressBar;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListActivity;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListFragment;

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
    public  static MaterialProgressBar progressBar;
    private View fragmentView;
    private Menu formMenu;
    private List<Category> categoryList = new ArrayList<Category>();
    private CategoriesTextFieldAdapter adapter;
    private EditText questionTitle;
    private InstantAutoCompleteView questionCategory;
    private EditText questionText;
    private Question question;
    private int questionID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Category c = new Category();
        c.getCollection();
        Bundle bundle = getArguments();
        if(bundle != null){
            question = bundle.getParcelable("question");
        }

        String questionFormTitle;
        if (question == null) {
            questionFormTitle = getResources().getString(R.string.question_form_title_create);
        }
        else {
            questionFormTitle = getResources().getString(R.string.question_form_title_edit);
        }

        getActivity().setTitle(questionFormTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.question_form_fragment, container, false);
        progressBar = (MaterialProgressBar) fragmentView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        questionCategory = (InstantAutoCompleteView) fragmentView.findViewById(R.id.questionCategory);
        questionTitle = (EditText) fragmentView.findViewById(R.id.questionTitle);
        questionText = (EditText) fragmentView.findViewById(R.id.questionText);
        enableInputs(false);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        formMenu = menu;
        MenuItem createQuestion = menu.findItem(R.id.add_question);
        if (createQuestion != null) {
            formMenu.removeItem(R.id.add_question);
        }
        inflater.inflate(R.menu.menu_question_form, formMenu);
        setFragmentButtons(formMenu);

    }

    private void enableInputs(Boolean value) {
        questionTitle.setEnabled(value);
        questionCategory.setEnabled(value);
        questionText.setEnabled(value);
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
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    saveQuestion();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        cancelItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                if (isTablet) {
                    if (question != null) {
                        Intent questionsIntent = new Intent(getActivity(), QuestionDetail.class);
                        startActivity(questionsIntent);
                        getActivity().overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
                    }
                    else {
                        formMenu.removeItem(R.id.saveForm);
                        formMenu.removeItem(R.id.cancelForm);
                        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        QuestionsListFragment fragment = new QuestionsListFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.commit();
                    }
                }
                else {
                    Intent questionsIntent = new Intent(getActivity(), QuestionsListActivity.class);
                    startActivity(questionsIntent);
                    getActivity().overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
                }
                return false;
            }
        });
    }

    private void saveQuestion() throws JSONException {
        String title = questionTitle.getText().toString();
        String text = questionText.getText().toString();
        int categoryId = getCategoryId(questionCategory.getText().toString());
        if (categoryId != 0) {
            JSONObject questionParams = new JSONObject();
            questionParams.put("title", title);
            questionParams.put("text", text);
            questionParams.put("category_id", categoryId);
            int userId = AuthManager.getInstance().getCurrentUser().getId();
            questionParams.put("user_id", userId);
            JSONObject params = new JSONObject();
            params.put("question", questionParams);
            if (question != null) {
                QuestionFactory.getInstance().update(question.getID(), params);
            }
            else {
                QuestionFactory.getInstance().create(params);
            }
        }
    }

    private int getCategoryId(String categoryTitle) {
        Category selectedCategory = null;
        for (Category category : categoryList) {
            if(category.getTitle().equals(categoryTitle)) {
                selectedCategory = category;
                break;
            }
        }
        try {
            return selectedCategory.getId();
        }
        catch (Exception e) {
            e.printStackTrace();
            questionCategory.setError("There is no such category");
            return 0;
        }
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

    public void onEvent(QuestionMessage event) throws JSONException {
        if (event.response instanceof JSONObject) {
            switch (event.operationName){
                case "detail":
                    parseDetailQuestion(event.response);
                    break;
                case "create":
                    parseSuccessQuestionCreation(event.response);
                    break;
                case "update":
                    parseSuccessQuestionUpdate(event.response);
                    break;
            }
        } else {
            switch (event.operationName){
                case "create":
                    parseFailureQuestionCreation(event.error);
                    break;
            }
        }
    }

    private void parseDetailQuestion(JSONObject response) {
        question = new Question(response);
        String categoryTitle = question.getCategory().getTitle();
        questionCategory.setText(categoryTitle);
        questionTitle.setText(question.getTitle());
        questionText.setText(question.getText());
        progressBar.setVisibility(View.GONE);
        enableInputs(true);
    }

    private void parseSuccessQuestionCreation(JSONObject response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Сообщение")
                .setMessage("Ваш вопрос был успешно создан")
                .setIcon(R.drawable.acepticon)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent questionsIntent = new Intent(getActivity(), QuestionsListActivity.class);
                                startActivity(questionsIntent);
                                getActivity().overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void parseSuccessQuestionUpdate(JSONObject response) {
        final Question updateQuestion = new Question(response);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Сообщение")
                .setMessage("Ваш вопрос был успешно обновлен")
                .setIcon(R.drawable.acepticon)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent questionsIntent = new Intent(getActivity(), QuestionDetail.class);
                                questionsIntent.putExtra("question", updateQuestion);
                                startActivity(questionsIntent);
                                getActivity().overridePendingTransition(R.anim.push_out_left, R.anim.pull_in_right);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void parseFailureQuestionCreation(VolleyError error) throws JSONException {
        JSONObject json;
        JSONArray errorsArray;

        NetworkResponse response = error.networkResponse;
        String serverResponse = new String(response.data);
        json = new JSONObject(serverResponse);
        if (response.statusCode == 422) {
            if (json.has("title")) {
                errorsArray = json.getJSONArray("title");
                String titleError = (String) errorsArray.get(0);
                questionTitle.setError(titleError);
            }

            if (json.has("text")) {
                errorsArray = json.getJSONArray("text");
                String textError = (String) errorsArray.get(0);
                questionText.setError(textError);
            }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Ошибка")
                    .setMessage("При создании вопроса произошла ошибка")
                    .setIcon(R.drawable.cancelicon)
                    .setCancelable(false)
                    .setNegativeButton("Закрыть",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
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
        if (question != null) {
            QuestionFactory.getInstance().get(question.getID());
        }
        if (question == null) {
            progressBar.setVisibility(View.GONE);
            enableInputs(true);
        }
    }

    private void addCategoriesToCompleteTextField() {
        adapter = new CategoriesTextFieldAdapter(getActivity().getBaseContext(),
                R.layout.category_autocomplete_item, (ArrayList<Category>) categoryList);
        questionCategory.setAdapter(adapter);
    }
}
