package com.vsokoltsov.stackqa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.Comment;
import com.vsokoltsov.stackqa.views.answers.AnswerListFragment;
import com.vsokoltsov.stackqa.views.comments.CommentsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 07.01.16.
 */
public class QuestionDetailPagerAdapter extends FragmentStatePagerAdapter {
    List<String> Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    public ArrayList<Answer> answersList;
    public ArrayList<Comment> commentsList;
    private int mCurrentPosition = -1;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public QuestionDetailPagerAdapter(FragmentManager fm,
                                      List<String> mTitles,
                                      int mNumbOfTabsumb,
                                      ArrayList<Answer> answers,
                                      ArrayList<Comment> comments) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.answersList = answers;
        this.commentsList = comments;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            AnswerListFragment tab1 = AnswerListFragment.newInstance(answersList);
            return tab1;
        }
        else              // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            CommentsListFragment tab2 = new CommentsListFragment();
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public void setAnswersData(ArrayList<Answer> answers) {
        this.answersList = answers;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            QuestionDetailViewPager pager = (QuestionDetailViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
