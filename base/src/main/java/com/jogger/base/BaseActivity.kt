package com.jogger.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mTopBar: QMUITopBarLayout
    protected var mBinding: DB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarDarkMode(this)
        window.setBackgroundDrawable(null)
        mContext = this
        if (isFullScreen()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        if (isNeedEventBus()) {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
        }
        if (hasTitleAction()) {
            setContentView(R.layout.activity_base)
            mTopBar = topbar
            mBinding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), fl_content, true)
        } else {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        }
        init(savedInstanceState)
    }

    abstract fun getLayoutId(): Int
    abstract fun init(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        if (isNeedEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }

    }

    fun isFullScreen(): Boolean = true
    fun isNeedEventBus(): Boolean = false
    fun hasTitleAction(): Boolean = true
    //    protected abstract fun initBinding(parent: ViewGroup?)
//    protected fun <T : ViewDataBinding> bindingLayout(viewParent: ViewGroup?): T {
//        return if (viewParent != null) {
//             DataBindingUtil.inflate(layoutInflater, getLayoutId(), viewParent, true)
//        } else {
//             DataBindingUtil.setContentView(this, getLayoutId())
//        }
//    }

}