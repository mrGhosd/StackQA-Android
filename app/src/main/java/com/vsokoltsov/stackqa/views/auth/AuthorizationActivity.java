package com.vsokoltsov.stackqa.views.auth;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.QuestionDetail;
import com.vsokoltsov.stackqa.views.QuestionsListActivity;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

public class AuthorizationActivity extends ActionBarActivity
        implements NavigationFragment.NavigationDrawerCallbacks{
    private FragmentTabHost mTabHost;
    private NavigationFragment mNavigationDrawerFragment;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        try {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                action = extras.getString("action");
            }

            DrawerLayout l = (DrawerLayout) findViewById(R.id.drawer_auth);
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            NavigationFragment nf = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mNavigationDrawerFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//
//            // Set up the drawer.
            mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_auth));

            mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

                @Override
                public void onTabChanged(String tabId) {
                    String title;
                    int currentTab = mTabHost.getCurrentTab();
                    switch(currentTab){
                        case 0:
                            title = "Sign in";
                            break;
                        case 1:
                            title = "Sign up";
                            break;
                        case 2:
                            title = "Restore password";
                            break;
                        default:
                            title = "Sign in";
                            break;
                    }
                    getSupportActionBar().setTitle(title);
                }
            });

            mTabHost.addTab( mTabHost.newTabSpec("tab1").setIndicator("Sign in", null), SignInFragment.class, null);
            mTabHost.addTab( mTabHost.newTabSpec("tab2").setIndicator("Sign up", null), SignUpFragment.class, null);
            mTabHost.addTab( mTabHost.newTabSpec("tab3").setIndicator("Restore password", null), RestorePasswordFragment.class, null);
            if(action.equals("sign_up")){
                mTabHost.setCurrentTab(1);
            } else {
                mTabHost.setCurrentTab(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Activity view;
        switch(position){
            case 2:
                Intent detailIntent = new Intent(this, QuestionsListActivity.class);
                startActivity(detailIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
