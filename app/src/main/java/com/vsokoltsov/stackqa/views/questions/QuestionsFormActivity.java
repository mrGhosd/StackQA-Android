package com.vsokoltsov.stackqa.views.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

/**
 * Created by vsokoltsov on 03.01.16.
 */
public class QuestionsFormActivity extends ActionBarActivity implements NavigationFragment.NavigationDrawerCallbacks{
    private DrawerLayout drawerLayout;
    private NavigationFragment mNavigationDrawerFragment;
    private QuestionsFormFragment questionsFormFragment;
    private Menu formMenu;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_form_activity);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            question = (Question) extras.getParcelable("question");
        }

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.form_drawer);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);

        Fragment frg = fragmentManager.findFragmentById(R.id.form_fragment);
        if (frg == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("question", question);
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            questionsFormFragment = new QuestionsFormFragment();
            questionsFormFragment.setArguments(arguments);
            fragmentTransaction.add(R.id.form_fragment, questionsFormFragment);
            fragmentTransaction.commit();
        }
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
