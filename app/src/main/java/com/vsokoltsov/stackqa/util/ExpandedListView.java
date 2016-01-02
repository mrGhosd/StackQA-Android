package com.vsokoltsov.stackqa.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by vsokoltsov on 02.01.16.
 */
public class ExpandedListView extends ListView {
    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        params = getLayoutParams();
        int height = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View item = getChildAt(i);
            height += item.getMeasuredHeight();
            height += this.getDividerHeight();
        }
        params.height  = height;
        setLayoutParams(params);

        super.onDraw(canvas);
    }
}
