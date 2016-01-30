package com.vsokoltsov.stackqa.views.questions.list;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.RVAdapter;
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.auth.AuthorizationActivity;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;
import com.vsokoltsov.stackqa.views.questions.form.QuestionsFormActivity;
import com.vsokoltsov.stackqa.views.questions.form.QuestionsFormFragment;
import com.vsokoltsov.stackqa.views.questions.detail.QuestionDetail;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class QuestionsListActivity extends ActionBarActivity implements QuestionsListFragment.Callbacks,
        NavigationFragment.NavigationDrawerCallbacks, RVAdapter.QuestionsViewHolder.QuestionViewHolderCallbacks {
    private DrawerLayout drawerLayout;
    private NavigationFragment mNavigationDrawerFragment;
    private SearchView mSearchView;
    private TextView mStatusView;
    private ArrayList<Question> questionsList;
    private AuthManager manager = AuthManager.getInstance();
    private SharedPreferences pref;
    private Menu mainMenu;
    private QuestionsListFragment questionListFragment;
    private Context context;
    private Question updatedQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        this.context = getBaseContext();

        pref = (SharedPreferences) getSharedPreferences("stackqa", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mActionBarToolbar);
        Bundle extras = getIntent().getExtras();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            initBaseConfigForPhone();
        }
        else {
            initBaseConfigForTablet();
        }
    }

    private void initBaseConfigForPhone() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);

        Fragment frg = fragmentManager.findFragmentById(R.id.container);
        if (frg == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            questionListFragment = new QuestionsListFragment();
            fragmentTransaction.add(R.id.container, questionListFragment);
            fragmentTransaction.commit();
        }
    }

    private void initBaseConfigForTablet() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setDrawerLayout(null);
        Fragment frg = fragmentManager.findFragmentById(R.id.container);
        if (frg == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            questionListFragment = new QuestionsListFragment();
            fragmentTransaction.add(R.id.container, questionListFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mainMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_achieve, mainMenu);
        MenuItem searchItem = mainMenu.findItem(R.id.search_quesitions);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        showCreateQuestionIcon();
        return true;
    }




    private void setupSearchView(MenuItem searchItem) {
        if (drawerLayout != null) {
            if (isAlwaysExpanded()) {
                mSearchView.setIconifiedByDefault(false);
            } else {
                searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                        | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            }

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            if (searchManager != null) {
                List searchables = searchManager.getSearchablesInGlobalSearch();

                SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
                for (int i = 0; i < searchables.size(); i++) {
                    if (searchables.get(i) != null) {
                        info = (SearchableInfo) searchables.get(i);
                    }
                }
            }
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            QuestionsListFragment frg = (QuestionsListFragment) fragmentManager.findFragmentById(R.id.container);
            final ListView questionsList = frg.getList();
            questionsList.setTextFilterEnabled(true);
            ArrayList<Question> defaultQuestionsList = frg.getQuestionsFromList();
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
    }

    public boolean onClose() {
        mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void detailQuestionView(View v){
    }

    @Override
    public void onItemSelected(Question question) {
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra("question", question);
        if (drawerLayout == null){
            questionsList = questionListFragment.getQuestionsFromList();
            detailIntent.putParcelableArrayListExtra("questions", questionsList);
        }
        startActivity(detailIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if (drawerLayout != null) {
            Activity view;
            switch (position) {
                case 0:
                    Intent detailIntent = new Intent(this, AuthorizationActivity.class);
                    detailIntent.putExtra("action", "sign_in");
                    startActivity(detailIntent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    break;
                case 1:
                    Intent regIntent = new Intent(this, AuthorizationActivity.class);
                    regIntent.putExtra("action", "sign_up");
                    startActivity(regIntent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    break;
            }
        }
    }

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
    public void onEvent(UserMessage event){
        switch (event.operationName){
            case "currentUserSignedIn":
                showCreateQuestionIcon();
                break;
            case "signOut":
                showCreateQuestionIcon();
                break;
        }
    }

    private void showCreateQuestionIcon() {
        final Activity ac = this;
        MenuItem addItem = mainMenu.findItem(R.id.add_question);
        if(manager.getCurrentUser() == null) {
            addItem.setVisible(false);
        }
        else {
            addItem.setVisible(true);
            addItem .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    boolean isTablet = getResources().getBoolean(R.bool.isTablet);
                    if (isTablet) {
                        Bundle arguments = new Bundle();
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        QuestionsFormFragment formFragment = new QuestionsFormFragment();
                        fragmentTransaction.replace(R.id.container, formFragment);
                        fragmentTransaction.commit();
                    }
                    else {
                        Intent regIntent = new Intent(ac, QuestionsFormActivity.class);
                        startActivity(regIntent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onItemClicked(Question question) {
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra("question", question);

        startActivity(detailIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
