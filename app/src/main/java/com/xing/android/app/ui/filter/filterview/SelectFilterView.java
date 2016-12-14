package com.xing.android.app.ui.filter.filterview;

import android.content.Context;
import android.util.AttributeSet;

import com.xing.android.app.ui.filter.filter.SelectFilter;

/**
 * Created by zxx09506 on 2015/3/10.
 */
public abstract class SelectFilterView<T> extends FilterView {
    protected SelectFilter<T> mFilterData;
    public SelectFilterView(Context context) {
        super(context);
    }

    public SelectFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setSelectFilter(SelectFilter<T> filter) {
        if(filter != null) {
            mFilterData = filter;
            mFilterGroup = mFilterData.getFilterGroup();
            filter.setSelectFilterView(this);
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
     * 点击FilterItem的事件，需要调用该方法，UI的Change不能由点击事件直接改变，只能由updateView进行Change
     * @param key
     * @param position
     */
    protected abstract void onSelectFilterItemClick(String key, int position);
}
