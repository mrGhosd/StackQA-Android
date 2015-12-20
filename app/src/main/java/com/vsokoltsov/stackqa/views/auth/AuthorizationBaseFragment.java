package com.vsokoltsov.stackqa.views.auth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.messages.UserMessage;
import com.vsokoltsov.stackqa.views.QuestionsListFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by vsokoltsov on 19.12.15.
 */
public class AuthorizationBaseFragment extends Fragment {
    private LinearLayout ll;
    private FragmentActivity fa;
    private FragmentTabHost mTabHost;
    private String action;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fa = super.getActivity();
        ll = (LinearLayout) inflater.inflate(R.layout.authorization_fragment, container, false);

        Bundle extras = getArguments();
        if(extras != null) {
            action = extras.getString("action");
        }

        mTabHost = (FragmentTabHost) ll.findViewById(android.R.id.tabhost);
        mTabHost.setup(fa, getChildFragmentManager(), android.R.id.tabcontent);


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
                fa.setTitle(title);
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
