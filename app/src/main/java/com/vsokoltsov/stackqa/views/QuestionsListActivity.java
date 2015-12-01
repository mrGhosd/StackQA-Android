package com.vsokoltsov.stackqa.views;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.QuestionsListAdapter;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.auth.AuthorizationActivity;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListActivity extends ActionBarActivity implements QuestionsListFragment.Callbacks,
        NavigationFragment.NavigationDrawerCallbacks {
    private NavigationFragment mNavigationDrawerFragment;
    private SearchView mSearchView;
    private TextView mStatusView;
    private ArrayList<Question> defaultQuestionsList;
    private AuthManager manager = AuthManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mNavigationDrawerFragment = (NavigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frg = fragmentManager.findFragmentById(R.id.container);
        if(frg == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            QuestionsListFragment fragment = new QuestionsListFragment();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
        }
        mStatusView = (TextView) findViewById(R.id.status_text);
        if (savedInstanceState == null) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_achieve, menu);
        MenuItem searchItem = menu.findItem(R.id.search_quesitions);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        setupSearchView(searchItem);
        return true;
    }




    private void setupSearchView(MenuItem searchItem) {

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
        final QuestionsListAdapter adapter = frg.getAdapter();
        ArrayList<Question> defaultQuestionsList = frg.getQuestionsFromList();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.sign_up) {
//            return true;
//        }
//        if(id == R.id.sign_in){
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void detailQuestionView(View v){
    }

    @Override
    public void onItemSelected(Question question) {
        Intent detailIntent = new Intent(this, QuestionDetail.class);
        detailIntent.putExtra("question", question);
        startActivity(detailIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Activity view;
        switch(position){
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

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
//                mTitle = getString(R.string.title_section1);
                break;
            case 2:
//                mTitle = getString(R.string.title_section2);
                break;
            case 3:
//                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Questions");
    }



}
