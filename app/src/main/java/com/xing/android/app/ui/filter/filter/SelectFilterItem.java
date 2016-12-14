package com.xing.android.app.ui.filter.filter;

import android.text.TextUtils;

/**
 * Created by zxx09506 on 2015/3/2.
 */
public class SelectFilterItem<T> {
    protected boolean isSelected;
    protected String mFilterItemKey;
    protected T mFilterItemValue;

    public SelectFilterItem(String key, T value) {
        this(key, value, false);
    }

    public SelectFilterItem(String key, T value, boolean isSelected) {
        if(TextUtils.isEmpty(key)) {
            throw new NullPointerException();
        }
        mFilterItemKey = key;
        mFilterItemValue = value;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean value) {
        this.isSelected = value;
    }

    public String getFilterItemKey() {
        return mFilterItemKey;
    }

    public void setFilterItemKey(String value) {
        this.mFilterItemKey = value;
    }

    public T getFilterItemValue() {
        return mFilterItemValue;
    }

    public void setFilterItemValue(T value) {
        this.mFilterItemValue = value;
    }
}
