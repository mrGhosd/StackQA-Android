package com.vsokoltsov.stackqa.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vsokoltsov on 09.02.16.
 */
public class QuestionDetailViewPager extends ViewPager {
    public QuestionDetailViewPager (Context context) {
        super(context);
    }

    public QuestionDetailViewPager (Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        boolean wrapHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST;
//
//        final View tab = getChildAt(0);
//        int width = getMeasuredWidth();
//        int tabHeight = tab.getMeasuredHeight();
//
//        if (wrapHeight) {
//            // Keep the current measured width.
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//        }
//
//        int fragmentHeight = measureFragment(((Fragment) getAdapter().instantiateItem(this, getCurrentItem())).getView());
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(fragmentHeight +
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                50,
//                getResources().getDisplayMetrics()), MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}
