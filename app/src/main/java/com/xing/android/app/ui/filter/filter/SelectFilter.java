package com.xing.android.app.ui.filter.filter;

import android.text.TextUtils;

import com.xing.android.app.ui.filter.filterview.SelectFilterView;
import com.xing.android.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxx09506 on 2015/3/2.
 */
public class SelectFilter<T> extends Filter {
    public static final int SELECT_MODE_SINGLE = 0;
    public static final int SELECT_MODE_MULTI = 1;

    protected List<SelectFilterItem<T>> mFilterItems;

    protected int mSelectMode = SELECT_MODE_SINGLE;

    /**
     * 是否允许0个选中的状态
     */
    protected boolean isAllowZeroSelected = false;
    /**
     * 该选项代表其他所有选项（类似于“所有”，“不限”）
     */
    protected SelectFilterItem<T> mAllFilterItem = null;

    protected SelectFilterView<T> mSelectFilterView;

    protected SelectFilterListener<T> mFilterListener;

    public SelectFilter(String key) {
        this(key, null, SELECT_MODE_SINGLE);
    }

    public SelectFilter(String key, List<SelectFilterItem<T>> list) {
        this(key, list, SELECT_MODE_SINGLE);
    }

    public SelectFilter(String key, List<SelectFilterItem<T>> list, int mode) {
        if(TextUtils.isEmpty(key)) {
            throw new NullPointerException("SelectFilter:key = null");
        }
        if(list == null) {
            list = new ArrayList<SelectFilterItem<T>>();
        }
        mFilterItems = list;
        if(mode == SELECT_MODE_SINGLE || mode == SELECT_MODE_MULTI) {
            mSelectMode = mode;
        }
    }

    public int getSelectMode() {
        return mSelectMode;
    }

    public void setSelectMode(int mode) {
        if(mSelectMode == mode) {
            return;
        }
        switch (mode) {
            case SELECT_MODE_SINGLE:
                mSelectMode = SELECT_MODE_SINGLE;
                reset();
                break;
            case SELECT_MODE_MULTI:
                mSelectMode = SELECT_MODE_MULTI;
                break;
            default:
                LogUtil.e(LOG_TAG, "setSelectMode:mode:" + mode + " is not valid");
                break;
        }
    }

    public void setFilterListener(SelectFilterListener<T> listener) {
        mFilterListener = listener;
    }

    public boolean isAllowZeroSelected() {
        return isAllowZeroSelected;
    }

    /**
     * 设置是否允许0个选中
     * @param value
     */
    public void setAllowZeroSelected(boolean value) {
        //当选中个数为0，并且从允许0个选中改为不允许0个选中时，重置
        boolean isNeedReset = !value && isAllowZeroSelected && getSelectedCount() == 0;
        isAllowZeroSelected = value;
        if(isNeedReset) {
            reset();
        }
    }

    /**
     * 获取“全选”Filter
     * @return
     */
    public SelectFilterItem<T> getAllFilterItem() {
        if(mFilterItems == null || mAllFilterItem == null || !mFilterItems.contains(mAllFilterItem)) {
            mAllFilterItem = null;
        }
        return mAllFilterItem;
    }

    public void setAllFilterItem(SelectFilterItem<T> item) {
        if(item == mAllFilterItem) {
            return;
        }
        if(mFilterItems != null && item != null && mFilterItems.contains(item)) {
            mAllFilterItem = item;
            onDataChange();
        }
    }

    public boolean setAllFilterItem(int position) {
        if(position > 0 && position <= mFilterItems.size()) {
            SelectFilterItem<T> filterItem = mFilterItems.get(position - 1);
            if(filterItem != null && filterItem != mAllFilterItem) {
                mAllFilterItem = filterItem;
                onDataChange();
            }
            return true;
        } else {
            return false;
        }
    }

    public int getAllFilterItemPosition() {
        if(mAllFilterItem == null) {
            return -1;
        }
        for(int i = 0; i < mFilterItems.size(); i++) {
            SelectFilterItem<T> item = mFilterItems.get(i);
            if(item != null && item == mAllFilterItem) {
                return i + 1;
            }
        }
        mAllFilterItem = null;
        return -1;
    }

    /**
     * 只有position的筛选项选中
     * @param position 从1开始
     */
    public boolean setSingleSelected(int position) {
        if(mFilterItems != null && position > 0 && position <= mFilterItems.size()) {
            for(SelectFilterItem<T> item:mFilterItems) {
                item.setSelected(false);
            }
            mFilterItems.get(position - 1).setSelected(true);
            onDataChange();
            return true;
        } else {
            LogUtil.e(LOG_TAG, "setSingleSelected:position(" + position + ") is invalid(list size:" + mFilterItems.size());
            return false;
        }
    }

    /**
     *
     * @param position 从1开始
     * @param value
     * @return
     */
    public boolean setFilterItemSelect(int position, boolean value) {
        if(mFilterItems != null && position > 0 && position < mFilterItems.size() + 1) {
            SelectFilterItem<T> item = mFilterItems.get(position);
            if(item == null) {
                LogUtil.w(LOG_TAG, "setFilterItemSelect:item of position(" + position + ") = null");
                return false;
            }
            if(item.isSelected() == value) {
                return true;
            }
            int count = getSelectedCount();
            boolean hasAllFilterItem = mAllFilterItem != null && mFilterItems.contains(mAllFilterItem);
            if(isAllowZeroSelected) {
                if(hasAllFilterItem) {
                    if(mAllFilterItem == item && value) {
                        setSingleSelected(position);
                    } else if(count == mFilterItems.size() - 2 && value) {
                        setMaxFilterValue();
                    } else {
                        item.setSelected(value);
                        onDataChange();
                    }
                } else {
                    item.setSelected(value);
                    onDataChange();
                }
            } else {
                if(hasAllFilterItem) {
                    if(mAllFilterItem == item && value) {
                        setSingleSelected(position);
                    } else if(count == mFilterItems.size() - 2 && value) {
                        setMaxFilterValue();
                    } else if(count == 1 && !value) {
                        setMinFilterValue(0);
                    } else {
                        item.setSelected(value);
                        onDataChange();
                    }
                } else {
                    if(count == 1 && !value) {
                        //Do nothing
                    } else {
                        item.setSelected(value);
                        onDataChange();
                    }
                }
            }
            return true;
        } else {
            if(mFilterItems == null) {
                LogUtil.w(LOG_TAG, "setFilterItemSelect:mFilterItems = null");
            } else {
                LogUtil.w(LOG_TAG, "setFilterItemSelect:position(" + position + ") is not valid,size = " + mFilterItems.size());
            }
        }
        return false;
    }


    public void setMinFilterValue(int position) {
        if(mFilterItems == null || mFilterItems.size() == 0) {
            LogUtil.w(LOG_TAG, "setMinFilterValue:mFilterItems = null or mFilterItems size = 0");
            return;
        }
        int allFilterItemPosition = 0;
        for(int i = 0 ; i < mFilterItems.size() ; i++) {
            SelectFilterItem<T> item = mFilterItems.get(i);
            if(mAllFilterItem != null && mAllFilterItem == item) {
                allFilterItemPosition = i + 1;
            }
            item.setSelected(false);
        }
        if(!isAllowZeroSelected) {
            if(allFilterItemPosition > 0 && position == allFilterItemPosition) {
                LogUtil.w(LOG_TAG, "setMinFilterValue:the item of position is AllFilterItem");
                for(int i = 0 ; i < mFilterItems.size() ; i++) {
                    if(allFilterItemPosition != i + 1) {
                        mFilterItems.get(i).setSelected(true);
                    }
                }
            } else {
                mFilterItems.get(position).setSelected(true);
            }
        }
        onDataChange();
    }

    public void setMaxFilterValue() {
        if(mFilterItems == null || mFilterItems.size() == 0) {
            LogUtil.w(LOG_TAG, "setMaxFilterValue:mFilterItems = null or mFilterItems size = 0");
            return;
        }
        if(mAllFilterItem != null && mFilterItems.contains(mAllFilterItem)) {
            for(SelectFilterItem item:mFilterItems) {
                item.setSelected(false);
            }
            mAllFilterItem.setSelected(true);
        } else {
            for(SelectFilterItem item:mFilterItems) {
                item.setSelected(true);
            }
        }
        onDataChange();
    }

    /**
     * 默认重置：如果有全选Item的话，只选中该Item，否则只选中第一个；
     * 如果子类要重写该方法，需要考虑单选，多选以及知否可以0选中
     */
    @Override
    public void reset() {
        SelectFilterItem<T> allFilterItem = getAllFilterItem();
        if(allFilterItem != null) {
            for(SelectFilterItem<T> item:mFilterItems) {
                item.setSelected(false);
            }
            allFilterItem.setSelected(true);
        } else {
            setSingleSelected(0);
        }
        if(mFilterListener != null) {
            mFilterListener.reset(this);
        }
        onDataChange();
    }

    public List<SelectFilterItem<T>> getFilterValue() {
        List<SelectFilterItem<T>> list = new ArrayList<SelectFilterItem<T>>();
        for(SelectFilterItem<T> item:mFilterItems) {
            if(item.isSelected()) {
                list.add(item);
            }
        }
        return list;
    }

    public int getSelectedCount() {
        int count = 0;
        if(mFilterItems != null) {
            for(SelectFilterItem<T> item:mFilterItems) {
                if(item.isSelected()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void setSelectFilterView(SelectFilterView filterView) {
        mSelectFilterView = filterView;
    }

    public SelectFilterView<T> getSelectFilterView() {
        return mSelectFilterView;
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
        if(mSelectFilterView != null) {
            mSelectFilterView.updateView();
        }
        if(mFilterGroup != null) {
            mFilterGroup.onFilterChange(this);
        }
    }

    public interface SelectFilterListener<T> {
        void reset(SelectFilter<T> filter);
        boolean isAffectFilters();
        int isAffectedByFilters();
        void onOtherFilterChange(SelectFilter filter);
        void onOtherFilterChange(ScopeFilter filter);
    }
}
