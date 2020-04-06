package com.jogger.module_mine.adapter

import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.OriginBook
import com.jogger.manager.AssetsManager
import com.jogger.module_mine.R
import com.qmuiteam.qmui.kotlin.skin

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
class BookRecyclerViewAdapter(data: MutableList<OriginBook>?) :
    BaseMultiItemQuickAdapter<OriginBook, BaseViewHolder>(data) {
    private var mCheckMode = false

    init {
        addItemType(0, R.layout.mine_rv_book_item)
        addItemType(-1, R.layout.mine_rv_book_foot)
    }

    override fun convert(holder: BaseViewHolder, item: OriginBook) {
        if (item.itemType == 0) {
            holder.setGone(R.id.cb_check, !mCheckMode)
            holder.getView<CheckBox>(R.id.cb_check).isChecked = item.isCheck!!
            Glide.with(context)
                .load(item.picpath)
                .into(holder.getView(R.id.iv_item))
            holder.getView<TextView>(R.id.tv_book_name).text = item.bookname
            holder.getView<TextView>(R.id.tv_book_name).typeface =
                AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT2)
            holder.setText(R.id.tv_book_size, "${item.cardcnt} 字句")
                .setVisible(R.id.v_shadow, !TextUtils.isEmpty(item.picpath))
            if (!TextUtils.isEmpty(item.picpath)) {
                holder.getView<View>(R.id.tv_book_name)
                    .skin {
                        it.textColor(R.attr.app_primary_color)
                    }
                holder.getView<View>(R.id.tv_book_size)
                    .skin {
                        it.textColor(R.attr.app_primary_color)
                    }
//                helper.setTextColor(R.id.tv_book_name, Color.WHITE)
//                    .setTextColor(R.id.tv_book_size, Color.WHITE)
            }
        } else {
            holder.setImageResource(
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

    fun setCheckMode(checkMode: Boolean) {
        mCheckMode = checkMode
        notifyDataSetChanged()
    }

    fun setCurrentCheckMode(position: Int) {
        data.forEach {
            it.isCheck = false
        }
        data[position].isCheck = true
        notifyDataSetChanged()
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