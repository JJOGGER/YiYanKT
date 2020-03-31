package com.jogger.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jogger.event.Message
import com.jogger.http.basic.config.HttpCode
import com.jogger.utils.MConfig
import com.qmuiteam.qmui.skin.QMUISkinLayoutInflaterFactory
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import ex.MODULE_LOGIN
import ex.dismissWindow
import ex.showToast
import ex.toActivity
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mTopBar: QMUITopBarLayout
    protected var mBinding: DB? = null
    protected lateinit var mViewModel: VM
    private var mSkinManager: QMUISkinManager? = null
    private var dialog: QMUITipDialog? = null

    companion object {
        fun navTo(context: Context, clazz: Class<*>) {
            val intent = Intent(context, clazz)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useQMUISkinLayoutInflaterFactory()) {
            val layoutInflater = LayoutInflater.from(this)
            LayoutInflaterCompat.setFactory2(
                layoutInflater,
                QMUISkinLayoutInflaterFactory(this, layoutInflater)
            )
        }
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarDarkMode(this)
        window.setBackgroundDrawable(null)
        mContext = this
        mSkinManager = QMUISkinManager.defaultInstance(this)
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
        createViewModel()
        lifecycle.addObserver(mViewModel)
        //注册 UI事件
        registorDefUIChange()
        init(savedInstanceState)
    }

    abstract fun getLayoutId(): Int
    abstract fun init(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        dismissWindow()
        if (isNeedEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }

    }

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })
        mViewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
        mViewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })
        mViewModel.defUI.msgEvent.observe(this, Observer {
            handleEvent(it)
        })
    }

    private fun handleEvent(it: Message) {
        if (it.code == HttpCode.CODE_ACCOUNT_INVALID) {
            MConfig.setCookie("")
            toActivity(mContext, MODULE_LOGIN)
            finish()
        }
    }

    open fun isFullScreen() = false
    open fun isNeedEventBus() = false
    open fun hasTitleAction() = true
    //    protected abstract fun initBinding(parent: ViewGroup?)
//    protected fun <T : ViewDataBinding> bindingLayout(viewParent: ViewGroup?): T {
//        return if (viewParent != null) {
//             DataBindingUtil.inflate(layoutInflater, getLayoutId(), viewParent, true)
//        } else {
//             DataBindingUtil.setContentView(this, getLayoutId())
//        }
//    }
    protected fun useQMUISkinLayoutInflaterFactory(): Boolean {
        return true
    }

    /**
     * 打开等待框
     */
    private fun showLoading() {
        if (dialog == null) {
            dialog = QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中")
                .create()
//            mLoadingDialog.setOnDismissListener(dialog -> backgroundAlpha(1f));
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
        }
        dialog!!.show()

    }

    /**
     * 关闭等待框
     */
    private fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }

    override fun onStart() {
        super.onStart()
        if (mSkinManager != null) {
            mSkinManager!!.register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (mSkinManager != null) {
            mSkinManager!!.unRegister(this)
        }
    }

    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProvider(this, ViewModelFactory()).get(tClass) as VM
        }
    }
}