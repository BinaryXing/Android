package com.xing.android.app.ui.filter.filterview;

import android.content.Context;
import android.util.AttributeSet;

import com.xing.android.app.ui.filter.filter.ScopeFilter;

/**
 * Created by zxx09506 on 2015/3/11.
 */
public abstract class ScopeFilterView<T> extends FilterView {
    protected ScopeFilter<T> mFilterData;

    public ScopeFilterView(Context context) {
        super(context);
    }

    public ScopeFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScopeFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScopeFilter(ScopeFilter<T> filter) {
        if(filter != null) {
            mFilterData = filter;
            mFilterGroup = mFilterData.getFilterGroup();
            filter.setScopeFilterView(this);
            updateView();
        }
    }

    @Override
    public String getFilterKey() {
        if(mFilterData != null) {
            return mFilterData.getFilterKey();
        }
        return "";
    }

    /**
     * 起始值改变
     * 用户的操作应该先传到Filter，Filter改变之后再通过updateView进行UI改变
     * @param beginValue
     */
    public abstract void onBeginValueChange(T beginValue);

    /**
     * 结束值改变
     * 用户的操作应该先传到Filter，Filter改变之后再通过updateView进行UI改变
     * @param endValue
     */
    public abstract void onEndvalueChange(T endValue);
}
