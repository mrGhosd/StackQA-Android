package com.vsokoltsov.stackqa.views.questions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

/**
 * Created by vsokoltsov on 03.01.16.
 */
public class QuestionsFormActivity extends ActionBarActivity implements NavigationFragment.NavigationDrawerCallbacks{
    private DrawerLayout drawerLayout;
    private NavigationFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_form_activity);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.form_drawer);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
