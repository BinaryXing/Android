package com.xing.android.app.ui.filter.filter;

import android.text.TextUtils;

import com.xing.android.app.ui.filter.filterview.ScopeFilterView;
import com.xing.android.common.util.LogUtil;

import java.util.Comparator;

/**
 * Created by zxx09506 on 2015/3/9.
 */
public class ScopeFilter<T> extends Filter {
    private final String LOG_TAG = "ScopeFilter";

    protected String mFilterItemKey;
    protected T mBeginFilterValue;
    protected T mEndFilterValue;
    protected Comparator<T> mComparator;

    protected ScopeFilterView<T> mScopeFilterView;

    protected ScopeFilterListener<T> mFilterListener;

    public ScopeFilter(String key, T beginValue, T endValue, Comparator<T> comparator) {
        if(TextUtils.isEmpty(key)) {
            throw new NullPointerException("ScopeFilterItem:key = null");
        }
        if(beginValue == null) {
            throw new NullPointerException("ScopeFilterItem:beginValue is null");
        }
        if(endValue == null) {
            throw new NullPointerException("ScopeFilterItem:endValue is null");
        }
        if(comparator == null) {
            LogUtil.w(LOG_TAG, "ScopeFilter:comparator = null");
        }
        if(comparator != null && comparator.compare(beginValue, endValue) > 0) {
            throw new IllegalArgumentException("ScopeFilterItem:beginValue:" + beginValue.toString() + " is larger than endValue:" + endValue.toString());
        }
        mBeginFilterValue = beginValue;
        mEndFilterValue = endValue;
        mComparator = comparator;
    }

    public void setScopeFilterView(ScopeFilterView<T> scopeFilterView) {
        mScopeFilterView = scopeFilterView;
    }

    public ScopeFilterView<T> getScopeFilterView() {
        return mScopeFilterView;
    }

    public void setScopeFilterListener(ScopeFilterListener<T> listener) {
        mFilterListener = listener;
    }

    public T getBeginFilterValue() {
        return mBeginFilterValue;
    }

    public boolean setBeginFilterValue(T beginValue) {
        if(mComparator != null) {
            if(mComparator.compare(beginValue, mEndFilterValue) <= 0) {
                mBeginFilterValue = beginValue;
                onDataChange();
                return true;
            }
            LogUtil.w(LOG_TAG, "setBeginFilterValue:beginValue is larger than mEndFilterValue");
            return false;
        }
        mBeginFilterValue = beginValue;
        onDataChange();
        return true;
    }

    public T getEndFilterValue() {
        return mEndFilterValue;
    }

    public boolean setEndFilterValue(T endValue) {
        if(mComparator != null) {
            if(mComparator.compare(mBeginFilterValue, endValue) <= 0) {
                mEndFilterValue = endValue;
                onDataChange();
                return true;
            }
            LogUtil.w(LOG_TAG, "setEndFilterValue:endValue is less than mBeginFilterValue");
            return false;
        }
        mEndFilterValue = endValue;
        onDataChange();
        return true;
    }

    public boolean setFilterValue(T beginValue, T endValue) {
        if(mComparator != null) {
            if(mComparator.compare(beginValue, endValue) <= 0) {
                mBeginFilterValue = beginValue;
                mEndFilterValue = endValue;
                onDataChange();
                return true;
            }
            LogUtil.w(LOG_TAG, "setFilterValue:beginValue is larger endValue");
            return false;
        }
        mBeginFilterValue = beginValue;
        mEndFilterValue = endValue;
        onDataChange();
        return true;
    }

    @Override
    public void reset() {
        if(mFilterListener != null) {
            mFilterListener.reset(this);
            onDataChange();
        }
    }

    @Override
    public boolean isAffectFilters() {
        if(mFilterListener != null) {
            return mFilterListener.isAffectFilters();
        }
        return false;
    }

    @Override
    public int isAffectedByFilters() {
        if(mFilterListener != null) {
            return mFilterListener.isAffectedByFilters();
        }
        return FilterGroup.FILTER_AFFECTED_NO;
    }

    @Override
    public void onOtherFilterChange(SelectFilter filter) {
        if(filter == null) {
            return;
        }
        if(mFilterListener != null) {
            mFilterListener.onOtherFilterChange(filter);
        }
    }

    @Override
    public void onOtherFilterChange(ScopeFilter filter) {
        if(filter == null) {
            return;
        }
        if(mFilterListener != null) {
            mFilterListener.onOtherFilterChange(filter);
        }
    }

    @Override
    public void onDataChange() {
        if(mScopeFilterView != null) {
            mScopeFilterView.updateView();
        }
        if(mFilterGroup != null) {
            mFilterGroup.onFilterChange(this);
        }
    }

    public interface ScopeFilterListener<T> {
        void reset(ScopeFilter<T> scopeFilter);
        boolean isAffectFilters();
        int isAffectedByFilters();
        void onOtherFilterChange(SelectFilter filter);
        void onOtherFilterChange(ScopeFilter filter);
    }
}
