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

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.QuestionDetail;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

public class AuthorizationActivity extends ActionBarActivity
        implements NavigationFragment.NavigationDrawerCallbacks{
    private FragmentTabHost mTabHost;
    private NavigationFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        try {
            DrawerLayout l = (DrawerLayout) findViewById(R.id.drawer_auth);
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            NavigationFragment nf = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mNavigationDrawerFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//
//            // Set up the drawer.
            mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_auth));

            mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

            mTabHost.addTab( mTabHost.newTabSpec("tab1").setIndicator("Tab 1", null), SignInFragment.class, null);
            mTabHost.addTab( mTabHost.newTabSpec("tab2").setIndicator("Tab 2", null), SignInFragment.class, null);
            mTabHost.addTab( mTabHost.newTabSpec("tab3").setIndicator("Tab 3", null), SignInFragment.class, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Activity view;
        switch(position){
            case 0:
//                Intent detailIntent = new Intent(this, AuthorizationActivity.class);
//                startActivity(detailIntent);
//                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
