package com.jogger.module_home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextCardDetailAdapter(activity: FragmentActivity, fragments: ArrayList< out Fragment>) :
    FragmentStateAdapter(activity) {
    var mFragments = fragments
    override fun getItemCount(): Int = mFragments.size

    override fun createFragment(position: Int): Fragment = mFragments[position]
}