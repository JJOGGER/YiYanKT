package com.jogger.module_home.view.delegate

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.ObservableField
import com.chad.library.adapter.base.entity.WindowModel
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.event.CardActionEvent
import com.jogger.http.datasource.ArticleActionDataSource
import com.jogger.module_home.databinding.HomeDetailBottomActionBinding
import com.jogger.module_home.view.activity.CommentActivity
import com.jogger.module_home.view.fragment.TextCardDetailFragment
import com.jogger.utils.MConfig
import com.jogger.utils.ToastHelper
import ex.*
import org.greenrobot.eventbus.EventBus


abstract class BaseProxy<T>(binding: T, context: TextCardDetailFragment) : BaseViewModel() {
    val mBinding: T
    val mContext: TextCardDetailFragment
    val mTextCardObservable = ObservableField<TextCard>()
    var mBottomBinding: HomeDetailBottomActionBinding? = null
    private var mIsLike = false

    init {
        mBinding = binding
        mContext = context
    }

    open fun registEvent(isRegist: Boolean) {
        if (isRegist) {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
        } else {
            if (EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this)
        }
    }

    private fun getActivity(): Activity {
        return mContext.activity!!
    }

    abstract fun initView()
    fun onDestroy() {
        registEvent(false)
    }

    /**
     * 安装底部菜单
     */
    fun setupBottomAction(binding: HomeDetailBottomActionBinding) {
        mBottomBinding = binding
    }


    fun checkLiked(cardId: String) {
        launchOnlyresult({
            ArticleActionDataSource.checkLiked(cardId)
        }, {
            mIsLike = (it.l == 1)
            if (mBottomBinding != null)
                mBottomBinding!!.tvLike.isSelected = mIsLike
        })
    }

    /**
     * 收藏
     */
    fun collectArticle(cardId: String) {
        launchOnlyresult({
            ArticleActionDataSource.newLike(cardId)
        }, {})
    }

    /**
     * 同感
     */
    fun likeArticle(cardId: String) {
        mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.tvLike.isSelected
        if (!mIsLike) {
            launchOnlyresult({
                ArticleActionDataSource.newLike(cardId)
            }, {
                mIsLike = !mIsLike
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = mIsLike
                    mBottomBinding!!.tvLike.text =
                        (mBottomBinding!!.tvLike.text.toString().toInt() + 1).toString()
                }
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        } else {
            launchOnlyresult({
                ArticleActionDataSource.cancelLike(cardId)
            }, {
                mIsLike = !mIsLike
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = mIsLike
                    mBottomBinding!!.tvLike.text =
                        (mBottomBinding!!.tvLike.text.toString().toInt() - 1).toString()
                }
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        }

    }

    /**
     * 跳转评论
     */
    fun toComments(card: TextCard) {
        CommentActivity.navTo(getActivity(), card.textcardid!!, card.commentcnt, false)
    }

    fun finish() {
        getActivity().finish()
    }

    fun moreAction(card: TextCard) {
        //判断是否是自己的贴子
        val copyWindow = WindowModel("复制文字", listener = object : OnFunctionWindowClickListener {
            override fun onClick(popupWindow: PopupWindow, index: Int) {
                popupWindow.dismiss()
                //获取剪贴板管理器：
                val cm: ClipboardManager? =
                    getSystemService(getActivity(), ClipboardManager::class.java)
                val mClipData = ClipData.newPlainText("Label", card.content)
                cm?.setPrimaryClip(mClipData)
            }
        })
        if (!card.creator!!.uid!!.equals(MConfig.getLoginResult().user!!.uid)) {
            showFunctionWindow(
                getActivity(), copyWindow,
                WindowModel("反感", listener = object : OnFunctionWindowClickListener {
                    override fun onClick(popupWindow: PopupWindow, index: Int) {
                        popupWindow.dismiss()
                        launchOnlyresult({
                            ArticleActionDataSource.disLikeCard(card.textcardid!!)
                        }, {
                            ToastHelper.showToast("已反感")
                        })
                    }
                }),
                WindowModel(
                    "屏蔽「${card.creator!!.username}」",
                    WindowModel.TYPE_ERROR,
                    object : OnFunctionWindowClickListener {
                        override fun onClick(popupWindow: PopupWindow, index: Int) {
                            popupWindow.dismiss()
                            launchOnlyresult({
                                ArticleActionDataSource.shielduser(card.textcardid!!)
                            }, {
                                ToastHelper.showToast("已屏蔽")
                            })
                        }
                    }),
                WindowModel("举报", WindowModel.TYPE_ERROR)
            )
        } else {
            showFunctionWindow(
                getActivity(),
                copyWindow,
                WindowModel("编辑", listener = object : OnFunctionWindowClickListener {
                    override fun onClick(popupWindow: PopupWindow, index: Int) {
                    }

                }),
                WindowModel("转移至其他文集", listener = object : OnFunctionWindowClickListener {
                    override fun onClick(popupWindow: PopupWindow, index: Int) {
                    }

                }),
                WindowModel(
                    "删除",
                    WindowModel.TYPE_ERROR,
                    listener = object : OnFunctionWindowClickListener {
                        override fun onClick(popupWindow: PopupWindow, index: Int) {
                            popupWindow.dismiss()
                            showFunctionWindow(
                                getActivity(),
                                WindowModel("确定删除？", WindowModel.TYPE_ERROR,
                                    listener = object : OnFunctionWindowClickListener {
                                        override fun onClick(popupWindow: PopupWindow, index: Int) {
                                            popupWindow.dismiss()
                                            launchOnlyresult({
                                                ArticleActionDataSource.deleteCard(card.textcardid!!)
                                            }, {
                                                val event =
                                                    CardActionEvent(CardActionEvent.CARD_DELETE_SUCCESS)
                                                event.putExtra(ex.POSITION, mContext.getPosition())
                                                postEvent(event)
                                            })
                                        }
                                    })
                            )
                        }

                    })
            )
        }
    }

    fun toUserHomePage(uid: String) {
        val map = HashMap<String, Any>()
        map.put(UID, uid)
        toActivity(getActivity(), USER_HOME_PAGE, map)
    }

    fun toBookPage(uid: String, title: String) {
        val map = HashMap<String, Any>()
        map.put(UID, uid)
        map.put(TITLE, title)
        toActivity(getActivity(), USER_BOOK_PAGE, map)
    }
}