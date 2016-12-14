package com.xing.android.app.ui.filter.filter;


/** T是数据的类型
 * Created by zxx09506 on 2015/3/9.
 */
public abstract class Filter {
    protected final String LOG_TAG = this.getClass().getSimpleName();

    /**
     * Filter的Key，只有在new对象的时候赋值
     */
    protected String mFilterKey;
    /**
     * 该Filter所在的FilterGroup
     */
    protected FilterGroup mFilterGroup;


    public String getFilterKey() {
        return mFilterKey;
    }

    public void setFilterGroup(FilterGroup filterGroup) {
        mFilterGroup = filterGroup;
    }

    public FilterGroup getFilterGroup() {
        return mFilterGroup;
    }


    public abstract void reset();

    /**
     * 是否影响其他Filter（同一个FilterGroup中的Filter）,默认不影响
     * @return
     */
    public abstract boolean isAffectFilters();

    /**
     * 是否是否受其他Filter（同一个FilterGroup中的Filter）影响，默认不影响
     * @return
     */
    public abstract int isAffectedByFilters();

    /**
     * isAffectedByFilters()返回FilterGroup.FILTER_AFFECTED_DEPEND_CASE时会走该方法
     * @param filter
     */
    public abstract void onOtherFilterChange(SelectFilter filter);
    /**
     * isAffectedByFilters()返回FilterGroup.FILTER_AFFECTED_DEPEND_CASE时会走该方法
     * @param filter
     */
    public abstract void onOtherFilterChange(ScopeFilter filter);

    /**
     * 如果Filter的数据有改变时，需要调用该方法更新View
     */
    public abstract void onDataChange();
}
