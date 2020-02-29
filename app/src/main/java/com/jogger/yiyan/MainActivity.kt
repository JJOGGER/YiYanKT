package com.jogger.yiyan

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.yiyan.databinding.ActivityMainBinding
import ex.MODULE_HOME_MAIN
import ex.getFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val TAB_INDEX = "tabdIndex"
private const val TAB_HOME = 0
private const val TAB_STAR = 1
private const val TAB_MESSAGE = 2
private const val TAB_MINE = 3

class MainActivity : BaseActivity<BaseViewModel, ActivityMainBinding>() {

    private val mFragments = arrayListOf<Fragment>()
    private val mBottomPath = arrayListOf<String>()
    private var mIndex = TAB_HOME
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun init(savedInstanceState: Bundle?) {
        initBottom()
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(TAB_INDEX)
        }
        fl_home.setOnClickListener({ v ->
            setTabStatus(TAB_HOME)
        })
        fl_star.setOnClickListener({ v ->
            setTabStatus(TAB_STAR)
        })
        fl_message.setOnClickListener({ v ->
            setTabStatus(TAB_MESSAGE)
        })
        fl_mine.setOnClickListener({ v ->
            setTabStatus(TAB_MINE)
        })
        setTabStatus(mIndex)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_INDEX, mIndex)
    }

    private fun initBottom() {

        mBottomPath.add(MODULE_HOME_MAIN)
        mBottomPath.add(MODULE_HOME_MAIN)
        mBottomPath.add(MODULE_HOME_MAIN)
        mBottomPath.add(MODULE_HOME_MAIN)
    }

    private fun setTabStatus(status: Int) {
        when (status) {
            TAB_HOME -> {
                tab_home.isSelected = true
                tab_star.isSelected = false
                tab_message.isSelected = false
                tab_mine.isSelected = false
            }
            TAB_STAR -> {
                tab_home.isSelected = false
                tab_star.isSelected = true
                tab_message.isSelected = false
                tab_mine.isSelected = false
            }
            TAB_MESSAGE -> {
                tab_home.isSelected = false
                tab_star.isSelected = false
                tab_message.isSelected = true
                tab_mine.isSelected = false
            }
            TAB_MINE -> {
                tab_home.isSelected = false
                tab_star.isSelected = false
                tab_message.isSelected = false
                tab_mine.isSelected = true
            }
        }
        val path = mBottomPath.get(status)
        if (!TextUtils.isEmpty(path)) {
            changeFragment(path)
        }
        mIndex = status
    }

    private fun changeFragment(tag: String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        var fragment = fragmentManager.findFragmentByTag(tag)
        hideFragment(tag, transaction)
        if (fragment != null) {
            transaction.show(fragment)
        } else {
            fragment = getFragment(tag)
            transaction.add(R.id.fl_container, fragment, tag)
        }
        if (!mFragments.contains(fragment)) {
            mFragments.add(fragment)
        }
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragment(tag: String, transaction: FragmentTransaction) {
        for (mFragment in mFragments) {
            if (tag != mFragment.getTag()) {
                transaction.hide(mFragment)
            }
        }
    }


}
