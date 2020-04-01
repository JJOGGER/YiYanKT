package com.jogger.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jogger.event.Message
import com.jogger.http.basic.config.HttpCode
import com.jogger.utils.MConfig
import com.qmuiteam.qmui.kotlin.skin
import com.qmuiteam.qmui.skin.QMUISkinManager
import ex.MODULE_LOGIN
import ex.showToast
import ex.toActivity
import java.lang.reflect.ParameterizedType

/**
 *   @auther : Aleyn
 *   time   : 2019/11/01
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    protected lateinit var mViewModel: VM

    protected var mBinding: DB? = null

    //是否第一次加载
    private var isFirst: Boolean = true

    protected var mContext: Activity? = null
    private var mSkinManager: QMUISkinManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
            if (getBackgroundAttr() != -1) {
                mBinding?.root?.skin {
                    it.background(getBackgroundAttr())
                }
            }
            return mBinding?.root
        }
        return inflater.inflate(layoutId(), container, false)
    }

   open fun getBackgroundAttr(): Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSkinManager = QMUISkinManager.defaultInstance(mContext)
        onVisible()
        createViewModel()
        lifecycle.addObserver(mViewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
    }

    fun navTo(clz: Class<*>) {
        val intent = Intent(mContext, clz)
        startActivity(intent)
    }

    fun navTo(context: Context, clz: Class<*>) {
        val intent = Intent(context, clz)
        context.startActivity(intent)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    abstract fun layoutId(): Int

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.defUI.showDialog.observe(viewLifecycleOwner, Observer {
            //            showLoading()
        })
        mViewModel.defUI.dismissDialog.observe(viewLifecycleOwner, Observer {
            //            dismissLoading()
        })
        mViewModel.defUI.toastEvent.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
        mViewModel.defUI.msgEvent.observe(viewLifecycleOwner, Observer {
            handleEvent(it)
        })
    }

    private fun handleEvent(it: Message) {
        if (it.code == HttpCode.CODE_ACCOUNT_INVALID) {
            MConfig.setCookie("")
            mContext?.let { it1 -> toActivity(it1, MODULE_LOGIN) }
            mContext?.finish()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = activity
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