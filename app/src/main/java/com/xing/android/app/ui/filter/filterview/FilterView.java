package com.xing.android.app.ui.filter.filterview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xing.android.app.ui.filter.filter.FilterGroup;

/**
 * Created by zxx09506 on 2015/3/9.
 */
public abstract class FilterView extends LinearLayout implements View.OnClickListener {

    protected final String LOG_TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected View mContentLayout;

    protected FilterGroup mFilterGroup;

    public FilterView(Context context) {
        super(context);
        mContext = context;
        onCreate();
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        onCreate();
    }

    public FilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        onCreate();
    }

    public FilterGroup getFilterGroup() {
        return mFilterGroup;
    }

    protected abstract void initViews();

    protected abstract int getContentLayoutRes();

    public abstract void updateView();

    public abstract String getFilterKey();


    protected void onCreate() {
        mContentLayout = LayoutInflater.from(mContext).inflate(getContentLayoutRes(), this, true);
        initViews();
    }

    @Override
    public void onClick(View v) {

    }
}
