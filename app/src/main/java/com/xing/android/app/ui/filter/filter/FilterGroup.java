package com.xing.android.app.ui.filter.filter;

import android.text.TextUtils;


import com.xing.android.common.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zxx09506 on 2015/3/9.
 */
public class FilterGroup {
    public static final int FILTER_AFFECTED_NO = 0;
    public static final int FILTER_AFFECTED_RESET = 1;
    public static final int FILTER_AFFECTED_DEPEND_CASE = 2;

    private final String LOG_TAG = this.getClass().getSimpleName();

    protected HashMap<String, Filter> mFilterMap;

    protected FilterGroupListener mFilterGroupListener;

    public FilterGroup(List<Filter> list) {
        mFilterMap = new HashMap<String, Filter>();
        if(list != null) {
            for(Filter filter:list) {
                addFilter(filter);
            }
        }
    }

    public void setFilterGroupListener(FilterGroupListener listener) {
        mFilterGroupListener = listener;
    }

    public boolean addFilter(Filter filter) {
        if(filter == null) {
            LogUtil.w(LOG_TAG, "addFilter:filter = null");
            return false;
        }
        if(TextUtils.isEmpty(filter.getFilterKey())) {
            LogUtil.w(LOG_TAG, "addFilter:key = null");
            return false;
        }
        mFilterMap.put(filter.getFilterKey(), filter);
        filter.setFilterGroup(this);
        return true;
    }

    public int addFilters(List<Filter> list) {
        if(list == null) {
            return 0;
        }
        int count = 0;
        for(Filter filter:list) {
            if(addFilter(filter)) {
                count++;
            }
        }
        return count;
    }

    public Filter getFilter(String key) {
        if(TextUtils.isEmpty(key)) {
            LogUtil.w(LOG_TAG, "getFilter:key = null");
            return null;
        }
        return mFilterMap.get(key);
    }

    public HashMap<String, List<SelectFilterItem>> getSelectFilterValue() {
        HashMap<String, List<SelectFilterItem>> hashMap = new HashMap<String, List<SelectFilterItem>>();
        for(Filter filter:mFilterMap.values()) {
            if(filter != null && filter instanceof SelectFilter) {
                hashMap.put(filter.getFilterKey(), ((SelectFilter) filter).getFilterValue());
            }
        }
        return hashMap;
    }

    public HashMap<String, ScopeFilter> getScopeFilterValue() {
        HashMap<String, ScopeFilter> hashMap = new HashMap<String, ScopeFilter>();
        for(Filter filter:mFilterMap.values()) {
            if(filter != null && filter instanceof ScopeFilter) {
                hashMap.put(filter.getFilterKey(), (ScopeFilter) filter);
            }
        }
        return hashMap;
    }

    public boolean onFilterChange(Filter filter) {
        if(filter == null || TextUtils.isEmpty(filter.getFilterKey())) {
            LogUtil.w(LOG_TAG, "onFilterChange:filter = null or filter.getFilterKey() = null");
            return false;
        }
        if(mFilterMap.get(filter.getFilterKey()) == null) {
            LogUtil.w(LOG_TAG, "onFilterChange:this FilterGroup do not contain the filter");
            return false;
        }
        if(!filter.isAffectFilters()) {
            return true;
        }
        for(Filter item:mFilterMap.values()) {
            switch (item.isAffectedByFilters()) {
                case FILTER_AFFECTED_NO:
                    //Do nothing
                    break;
                case FILTER_AFFECTED_RESET:
                    item.reset();
                    break;
                case FILTER_AFFECTED_DEPEND_CASE:
                    if(filter instanceof SelectFilter) {
                        item.onOtherFilterChange((SelectFilter) filter);
                    } else if(filter instanceof ScopeFilter) {
                        item.onOtherFilterChange((ScopeFilter) filter);
                    } else {
                        LogUtil.w(LOG_TAG, "");
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public void doFilter() {
        if(mFilterGroupListener != null) {
            mFilterGroupListener.doFilter(this);
        }
    }

    public interface FilterGroupListener {
        void doFilter(FilterGroup filterGroup);
    }
}
