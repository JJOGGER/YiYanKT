package com.jogger.module_home.view.fragment

import android.os.Bundle
import com.jogger.base.BaseFragment
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.module_home.R
import com.jogger.module_home.databinding.HomeFragmentTextCardDetailBinding
import com.jogger.module_home.view.delegate.TextCardDetailDelegate
import com.jogger.module_home.view.viewmodel.TextCardDetailViewModel
import kotlinx.android.synthetic.main.home_fragment_text_card_detail.*

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextCardDetailFragment :
    BaseFragment<TextCardDetailViewModel, HomeFragmentTextCardDetailBinding>() {
    private lateinit var mDelegate: TextCardDetailDelegate

    companion object {
        fun getInstance(textCard: TextCard, position: Int): TextCardDetailFragment {
            val fragment = TextCardDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ex.TEXT_CARD, textCard)
            bundle.putInt(ex.POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutId() = R.layout.home_fragment_text_card_detail
    override fun initView(savedInstanceState: Bundle?) {
        if (arguments == null) return
        val textCard = arguments!!.getParcelable<TextCard>(ex.TEXT_CARD)!!
        tv_name.text = textCard.title + "---->" + arguments!!.getInt(ex.POSITION)
        mBinding!!.textCard = textCard
        when (textCard.category) {
            CARD_CATEGORY.TYPE_TEXT._value,
            CARD_CATEGORY.TYPE_POETRY._value,
            CARD_CATEGORY.TYPE_FILM._value-> {

            }
            CARD_CATEGORY.TYPE_TOPIC._value -> {

            }
            CARD_CATEGORY.TYPE_MUSIC._value -> {
            }
        }
    }

}