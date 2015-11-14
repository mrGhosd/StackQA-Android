package com.vsokoltsov.stackqa.views.auth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.vsokoltsov.stackqa.R;

/**
 * Created by vsokoltsov on 13.11.15.
 */
public class AuthorizationActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.authorization);
            android.support.v7.app.ActionBar actionbar = getSupportActionBar();
            //Tell the ActionBar we want to use Tabs.
            actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            //initiating both tabs and set text to it.
            ActionBar.Tab signInTab = actionbar.newTab().setText("Sign in");
            ActionBar.Tab signUpTab = actionbar.newTab().setText("Sign up");

            //create the two fragments we want to use for display content
            android.support.v4.app.Fragment signInFragment = (android.support.v4.app.Fragment) new SignInFragment();
            android.support.v4.app.Fragment signUpFragment = (android.support.v4.app.Fragment) new SignUpFragment();

            //set the Tab listener. Now we can listen for clicks.
            signInTab.setTabListener((ActionBar.TabListener) new AuthTabListener(signInFragment));
            signUpTab.setTabListener((ActionBar.TabListener) new AuthTabListener(signUpFragment));

            //add the two tabs to the actionbar
            actionbar.addTab(signInTab);
            actionbar.addTab(signUpTab);
//            FragmentTabHost tabs = (FragmentTabHost) findViewById(R.id.tabHost);
//            tabs.setup(this, getSupportFragmentManager(), R.id.realTabContent);

            // Calculator
//            tabs.addTab(tabs.newTabSpec("sign_in").setIndicator("Sign in"),
//                    SignInFragment.class, null);
//            tabs.addTab(tabs.newTabSpec("sign_up").setIndicator("Sign up"),
//                    SignUpFragment.class, null);
//            tabs.addTab(tabs.newTabSpec("restore_password").setIndicator("Restore password"),
//                    RestorePasswordFragment.class, null);
//            TabHost.TabSpec calculatorTab = tabs.newTabSpec("Sign in");
//            Intent intent = new Intent().setClass(this, SignInFragment.class);
//            calculatorTab.setIndicator("Sign in");
//            calculatorTab.setContent;
//            tabs.addTab(calculatorTab);
////
////
//            TabHost.TabSpec signUpTab = tabs.newTabSpec("Sign up");
//            Intent signUpIntent = new Intent().setClass(this, SignUpFragment.class);
//            signUpTab.setIndicator("Sign up");
//            signUpTab.setContent(signUpIntent);
//            tabs.addTab(signUpTab);
////
//            TabHost.TabSpec restorePasswordTab = tabs.newTabSpec("Restore password");
//            Intent restorePasswordIntent = new Intent().setClass(this, RestorePasswordFragment.class);
//            restorePasswordTab.setIndicator("Restore Password");
//            restorePasswordTab.setContent(restorePasswordIntent);
//            tabs.addTab(restorePasswordTab);

            // Home
//            TabHost.TabSpec homeTab = tabs.newTabSpec("home");
//            homeTab.setIndicator("Home");
//            tabs.addTab(homeTab);
//
//            // Home
//            TabHost.TabSpec faqTab = tabs.newTabSpec("faq");
//            faqTab.setIndicator("Faq");
//            tabs.addTab(faqTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
