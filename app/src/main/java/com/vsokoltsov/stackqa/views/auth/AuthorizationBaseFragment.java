package com.vsokoltsov.stackqa.views.auth;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.adapters.ViewPagerAdapter;
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.util.SlidingTabLayout;
import com.vsokoltsov.stackqa.views.questions.list.QuestionsListFragment;
import com.vsokoltsov.stackqa.views.navigation.NavigationFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 19.12.15.
 */
public class AuthorizationBaseFragment extends Fragment {
    private LinearLayout ll;
    private FragmentActivity fa;
    private FragmentTabHost mTabHost;
    private String action;
    private NavigationFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[]={"Sign in","Sign up"};
    private int Numboftabs =2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fa = super.getActivity();
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle extras = getArguments();
        if(extras != null) {
            action = (String) extras.getString("action");
        }
        ll = (LinearLayout) inflater.inflate(R.layout.authorization_fragment, container, false);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) ll.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        if (action.equals("sign_in")) {
            pager.setCurrentItem(0);
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(Titles[0]);
        }
        else if (action.equals("sign_up")) {
            pager.setCurrentItem(1);
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(Titles[1]);
        }

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) ll.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setBackground(new ColorDrawable(R.color.highlighted_text_material_light));
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String title = (String) Titles[position];
                ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(title);
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
        return ll;
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
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuestionsListFragment questionList = new QuestionsListFragment();
        fragmentTransaction.replace(R.id.container, questionList);
        fragmentTransaction.commit();
    }
}
