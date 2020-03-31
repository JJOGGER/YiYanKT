package com.jogger.module_mine.adapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.OriginBook
import com.jogger.manager.AssetsManager
import com.jogger.module_mine.R

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
class BookRecyclerViewAdapter(data: MutableList<OriginBook>?) :
    BaseMultiItemQuickAdapter<OriginBook, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.mine_rv_book_item)
        addItemType(-1, R.layout.mine_rv_book_foot)
    }

    override fun convert(helper: BaseViewHolder, item: OriginBook) {
        if (item.itemType == 0) {
            Glide.with(context)
                .load(item.picpath)
                .into(helper.getView(R.id.iv_item))
            helper.getView<TextView>(R.id.tv_book_name).text = item.bookname
            helper.getView<TextView>(R.id.tv_book_name).typeface =
                AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT2)
            helper.setText(R.id.tv_book_size, "${item.cardcnt} 字句")
                .setVisible(R.id.v_shadow, !TextUtils.isEmpty(item.picpath))
            if (!TextUtils.isEmpty(item.picpath)) {
                helper.setTextColor(R.id.tv_book_name, Color.WHITE)
                    .setTextColor(R.id.tv_book_size, Color.WHITE)
            }
        } else {
            helper.setImageResource(
                R.id.iv_item,
                R.drawable.icon_add_book
            )
        }
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    fun moveItem(fromPosition: Int, toPosition: Int) {
        notifyItemChangedWrapper(fromPosition)
        notifyItemChangedWrapper(toPosition)
        // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作
        data.add(toPosition, data.removeAt(fromPosition))
        notifyItemMovedWrapper(fromPosition, toPosition)
    }

    fun notifyItemChangedWrapper(position: Int) {
        if (headerLayoutCount == 0) {
            notifyItemChanged(position)
        } else {
            notifyItemChanged(headerLayoutCount + position)
        }
    }

    fun notifyItemMovedWrapper(fromPosition: Int, toPosition: Int) {
        if (headerLayoutCount == 0) {
            notifyItemMoved(fromPosition, toPosition)
        } else {
            notifyItemMoved(
                headerLayoutCount + fromPosition,
                headerLayoutCount + toPosition
            )
        }
    }
}