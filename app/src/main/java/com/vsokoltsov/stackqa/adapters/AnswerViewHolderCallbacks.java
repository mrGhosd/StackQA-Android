package com.vsokoltsov.stackqa.adapters;

import android.view.MenuItem;
import android.view.View;

import com.vsokoltsov.stackqa.models.Answer;

/**
 * Created by vsokoltsov on 06.03.16.
 */
public interface AnswerViewHolderCallbacks {
    void onOptionsClicked(Answer answer, View itemView, MenuItem menuItem);

    void onCommentsClicked(Answer answer);
}
