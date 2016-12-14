package com.xing.android.app.ui.filter.filterview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xing.android.app.ui.filter.filter.Filter;
import com.xing.android.app.ui.filter.filter.FilterGroup;
import com.xing.android.app.ui.filter.filter.ScopeFilter;
import com.xing.android.app.ui.filter.filter.SelectFilter;
import com.xing.android.common.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zxx09506 on 2015/3/11.
 */
public abstract class FilterViewGroup extends LinearLayout implements View.OnClickListener {
    protected final String LOG_TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected View mRootLayout;

    protected HashMap<String, FilterView> mFilterViewList = new HashMap<String, FilterView>();

    protected FilterGroup mFilterGroup;
    public FilterViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        onCreate();
    }

    public FilterViewGroup(Context context) {
        super(context);
        mContext = context;
        onCreate();
    }

    protected void onCreate() {
        mRootLayout = LayoutInflater.from(mContext).inflate(getContentLayoutRes(), this, true);
        initViews();
    }

    public abstract int getContentLayoutRes();
    public abstract void initViews();

    public FilterView getFilterView(String key) {
        if(TextUtils.isEmpty(key)) {
            throw new NullPointerException("FilterViewGroup:getFilterView:key = null");
        }
        if(mFilterViewList == null) {
            return null;
        }
        return mFilterViewList.get(key);
    }

    public boolean setFilterData(Filter filter) {
        if(filter == null) {
            LogUtil.e(LOG_TAG, "setFilterData:filter = null");
            return false;
        }
        String key = filter.getFilterKey();
        if(TextUtils.isEmpty(key)) {
            LogUtil.w(LOG_TAG, "setFilterData:key = null");
            return false;
        }
        if(mFilterViewList.get(key) == null) {
            LogUtil.w(LOG_TAG, "setFilterData:do not hava the view of key(" + key + ")");
            return false;
        }
        FilterView filterView = mFilterViewList.get(key);
        if(filter instanceof SelectFilter && filterView instanceof SelectFilterView) {
            ((SelectFilterView) filterView).setSelectFilter((SelectFilter) filter);
        } else if(filter instanceof ScopeFilter && filterView instanceof ScopeFilterView) {
            ((ScopeFilterView) filterView).setScopeFilter((ScopeFilter) filter);
        } else {
            LogUtil.w(LOG_TAG, "setFilterData:filter is not matched with filterView");
            return false;
        }
        if(mFilterGroup == null) {
            mFilterGroup = filterView.getFilterGroup();
        } else if(mFilterGroup != filterView.getFilterGroup()) {
            throw new IllegalArgumentException("setFilterData:mFilterGroup != filterView.getFilterGroup()");
        }
        return true;
    }

    public int setFilterData(List<Filter> list) {
        if(list == null) {
            LogUtil.w(LOG_TAG, "addAllFilterData:list = null");
            return 0;
        }
        int count = 0;
        for(Filter filter:list) {
            if(setFilterData(filter)) {
                count++;
            }
        }
        return count;
    }

    public void doFilter() {
        if(mFilterGroup != null) {
            mFilterGroup.doFilter();
        }
    }
}
