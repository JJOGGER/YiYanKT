package com.jogger.module_star.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.module_star.R
import com.jogger.widget.YiYanHeader
import ex.INDEX
import kotlinx.android.synthetic.main.star_fragment_item.*

/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class StarItemFragment : BaseFragment<BaseViewModel, ViewDataBinding>() {
    override fun layoutId(): Int = R.layout.star_fragment_item

    companion object {
        fun getInstance(type: Int): StarItemFragment {
            val fragment = StarItemFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        srl_refresh.setRefreshHeader(YiYanHeader(mContext!!))
        val type = arguments!!.getInt(INDEX)
        tv_content.text = "--------$type"
    }
}