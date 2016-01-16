package com.vsokoltsov.stackqa.views.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.ViewPagerAdapter;
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.util.SlidingTabLayout;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListActivity;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

import de.greenrobot.event.EventBus;

public class AuthorizationActivity extends ActionBarActivity
        implements NavigationFragment.NavigationDrawerCallbacks{
    private FragmentTabHost mTabHost;
    private NavigationFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private String action;
    private String userEmail;
    private String userPassword;

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Sign in","Sign up"};
    int Numboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_auth);
        // Creating The Toolbar and setting it as the Toolbar for the activity

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            action = (String) extras.getString("action");
        }
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        if (action.equals("sign_in")) {
            pager.setCurrentItem(0);
            getSupportActionBar().setTitle(Titles[0]);
        }
        else if (action.equals("sign_up")) {
            pager.setCurrentItem(1);
            getSupportActionBar().setTitle(Titles[1]);
        }

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setBackground(new ColorDrawable(R.color.highlighted_text_material_light));
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String title = (String) Titles[position];
                getSupportActionBar().setTitle(title);
            }
        });

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
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

    // This method will be called when a MessageEvent is posted

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
}
