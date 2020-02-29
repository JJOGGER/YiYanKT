package com.qmuiteam.qmui.widget.dialog;

import android.content.Context;

import com.qmuiteam.qmui.R;
import com.qmuiteam.qmui.util.QMUIResHelper;

public class QMUIAdaptiveCalculate implements QMUIBottomSheet.BottomGridSheetBuilder.QMUICalculateStrategy {

    private Context mContext;
    public QMUIAdaptiveCalculate(Context mContext){
        this.mContext = mContext;
    }
    /**
     * 拿个数最多的一行，去决策item的平铺/拉伸策略
     *
     * @return item 宽度
     */
    @Override
    public int calculateItemWidth(int width, int maxItemCountInEachLine, int paddingLeft, int paddingRight) {

        int mMiniItemWidth = QMUIResHelper.getAttrDimen(mContext, R.attr.qmui_bottom_sheet_grid_item_mini_width);
        final int parentSpacing = width - paddingLeft - paddingRight;
        int itemWidth = mMiniItemWidth;
        // 看是否需要把 Item 拉伸平分 parentSpacing
        if (maxItemCountInEachLine >= 3
                && parentSpacing - maxItemCountInEachLine * itemWidth > 0
                && parentSpacing - maxItemCountInEachLine * itemWidth < itemWidth) {
            int count = parentSpacing / itemWidth;
            itemWidth = parentSpacing / count;
        }
        // 看是否需要露出半个在屏幕边缘
        if (itemWidth * maxItemCountInEachLine > parentSpacing) {
            int count = (width - paddingLeft) / itemWidth;
            itemWidth = (int) ((width - paddingLeft) / (count + .5f));
        }
        return itemWidth;
    }
}