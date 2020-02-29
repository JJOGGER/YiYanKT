package com.qmuiteam.qmui.widget.dialog;

public class QMUIEqualCalculate implements QMUIBottomSheet.BottomGridSheetBuilder.QMUICalculateStrategy {
    /**
     * 均分item
     * @param width
     * @param maxItemCountInEachLine
     * @param paddingLeft
     * @param paddingRight
     * @return
     */
    @Override
    public int calculateItemWidth(int width, int maxItemCountInEachLine, int paddingLeft, int paddingRight) {
        final int parentSpacing = width - paddingLeft - paddingRight;
        return parentSpacing / maxItemCountInEachLine;
    }
}