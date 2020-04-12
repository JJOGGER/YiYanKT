package com.jogger.module_mine.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.entity.OriginBook
import com.jogger.module_mine.R
import com.jogger.module_mine.adapter.BookRecyclerViewAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import ex.sApplication

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
class BookRecyclerView : RecyclerView {
    private var mPlusEnable = true
    private var mSortable = true
    private var mPlusDrawableResId = R.drawable.icon_add_book
    private var mItemRadius = QMUIDisplayHelper.dp2px(sApplication, 4)
    private var mItemSpacing = 0
    private var mItemSpanCount = 2
    private var mOtherItemSpacing = 0
    private var mItemWidth = 0
    private var mItemTouchHelper = ItemTouchHelper(mItemTouchHelperCallback())
    private lateinit var mAdapter: BookRecyclerViewAdapter
    private var mCheckMode = false
    private lateinit var mLayoutManager: GridLayoutManager
    private var mListener: onItemClickListener? = null
    private var mMoveListener: onItemMoveListener? = null

    interface onItemClickListener {
        fun onAddClick()
        fun onItemClick(book: OriginBook, position: Int)
    }

    interface onItemMoveListener {
        fun onItemMoved()
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        initCustomAttrs(context, attrs)
        init()
    }

    private fun initCustomAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BookRecyclerView)
        for (i in 0..typedArray.indexCount) {
            initCustomAttr(typedArray.getIndex(i), typedArray)
        }
        typedArray.recycle()
    }

    private fun initCustomAttr(attr: Int, typedArray: TypedArray) {
        when (attr) {
            R.styleable.BookRecyclerView_book_plus_enable -> mPlusEnable = typedArray.getBoolean(attr, true)
            R.styleable.BookRecyclerView_book_sortable -> mSortable = typedArray.getBoolean(attr, true)
            R.styleable.BookRecyclerView_book_item_radius -> mItemRadius =
                typedArray.getDimensionPixelOffset(attr, mItemRadius)
            R.styleable.BookRecyclerView_book_item_spacing -> mItemSpacing =
                typedArray.getDimensionPixelOffset(attr, mItemSpacing)
            R.styleable.BookRecyclerView_book_plus_drawable -> mPlusDrawableResId =
                typedArray.getResourceId(attr, mPlusDrawableResId)
            R.styleable.BookRecyclerView_book_other_item_spacing -> mOtherItemSpacing =
                typedArray.getDimensionPixelOffset(attr, mOtherItemSpacing)
            R.styleable.BookRecyclerView_book_item_span_count -> mItemSpanCount =
                typedArray.getInteger(attr, mItemSpanCount)
        }
    }

    private fun init() {
        mItemWidth =
            if (mItemWidth == 0) (QMUIDisplayHelper.getScreenWidth(sApplication) - mOtherItemSpacing) / mItemSpanCount else mItemWidth + mItemSpacing
        overScrollMode = View.OVER_SCROLL_NEVER
        mItemTouchHelper.attachToRecyclerView(this)
        mLayoutManager = GridLayoutManager(context, mItemSpanCount)
        layoutManager = mLayoutManager
        addItemDecoration(BookGridDivider.newInstanceWithSpacePx(mItemSpacing / 2))
        mAdapter = BookRecyclerViewAdapter(null)
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val originBook = adapter.getItem(position) as OriginBook?
                if (originBook == null) return
                if (mListener == null) return
                if (originBook.itemType == -1) {
                    mListener!!.onAddClick()
                } else {
                    if (mCheckMode) {
                        mAdapter.setCurrentCheckMode(position)
                    }
                    mListener!!.onItemClick(originBook, position)
                }
            }
        })
        adapter = mAdapter
    }

    fun setData(list: MutableList<OriginBook>?) {
        mAdapter.setNewInstance(list)
    }

    fun getData(): MutableList<OriginBook>? {
        return mAdapter.data.filter { it.bookid != null } as MutableList<OriginBook>
    }

    fun setCheckMode(checkMode: Boolean) {
        mCheckMode = checkMode
        mAdapter.setCheckMode(mCheckMode)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    fun setOnItemMoveListener(listener: onItemMoveListener) {
        mMoveListener = listener
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var spanCount = mItemSpanCount
        val itemCount = mAdapter.getItemCount()
        if (itemCount > 0 && itemCount < mItemSpanCount) {
            spanCount = itemCount
        }
        mLayoutManager.setSpanCount(spanCount)

        val expectWidth = mItemWidth * spanCount
        var expectHeight = 0
        if (itemCount > 0) {
            val rowCount = (itemCount - 1) / spanCount + 1
            expectHeight = mItemWidth * rowCount
        }

        var width = resolveSize(expectWidth, widthMeasureSpec)
        var height = resolveSize(expectHeight, heightMeasureSpec)
        width = Math.min(width, expectWidth)
        height = Math.min(height, expectHeight)

        setMeasuredDimension(width, height)
    }

    inner class mItemTouchHelperCallback : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled(): Boolean = mSortable && (adapter as BaseQuickAdapter<*, *>).data.size > 1
        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
            var dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            if ((adapter as BookRecyclerViewAdapter).data.get(viewHolder.adapterPosition).itemType == -1) {
                dragFlags = ACTION_STATE_IDLE
            }
            return makeMovementFlags(dragFlags, ACTION_STATE_IDLE)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
            if (viewHolder.itemViewType != target.itemViewType || (adapter as BookRecyclerViewAdapter).data.get(
                    viewHolder.adapterPosition
                ).itemType == -1
            ) {
                return false
            }
            (adapter as BookRecyclerViewAdapter).moveItem(viewHolder.adapterPosition, target.adapterPosition)
            if (mMoveListener != null)
                mMoveListener!!.onItemMoved()
            return true
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        }

        override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
            if (actionState != ACTION_STATE_IDLE) {
                ViewCompat.setScaleX(viewHolder!!.itemView, 1.2f)
                ViewCompat.setScaleY(viewHolder.itemView, 1.2f)
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
            ViewCompat.setScaleX(viewHolder.itemView, 1.0f)
            ViewCompat.setScaleY(viewHolder.itemView, 1.0f)
            super.clearView(recyclerView, viewHolder)
        }

    }

}