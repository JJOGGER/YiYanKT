package com.jogger.module_home.view.fragment

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jogger.base.BaseFragment
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.module_home.R
import com.jogger.module_home.databinding.HomeDetailMusicViewBinding
import com.jogger.module_home.databinding.HomeDetailTextViewBinding
import com.jogger.module_home.databinding.HomeDetailTopicViewBinding
import com.jogger.module_home.databinding.HomeFragmentTextCardDetailBinding
import com.jogger.module_home.view.delegate.BaseProxy
import com.jogger.module_home.view.delegate.MusicProxy
import com.jogger.module_home.view.delegate.TextProxy
import com.jogger.module_home.view.delegate.TopicProxy
import com.jogger.module_home.view.viewmodel.TextCardDetailViewModel

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextCardDetailFragment :
    BaseFragment<TextCardDetailViewModel, HomeFragmentTextCardDetailBinding>() {
    private lateinit var mDelegate: BaseProxy<*>
    private lateinit var mTextCard: TextCard
    private val mPosition by lazy {
        arguments?.getInt(ex.POSITION, 0)
    }

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
        mTextCard = arguments!!.getParcelable(ex.TEXT_CARD)!!
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mBinding!!.textCard = mTextCard
        when (mTextCard.category) {
            CARD_CATEGORY.TYPE_TEXT._value,
            CARD_CATEGORY.TYPE_POETRY._value,
            CARD_CATEGORY.TYPE_FILM._value,
            CARD_CATEGORY.TYPE_RECORD._value,
            CARD_CATEGORY.TYPE_WORD._value -> {
                if (!mBinding!!.viewText.isInflated) {
                    val inflate = mBinding!!.viewText.viewStub?.inflate()
                    val binding = DataBindingUtil.getBinding<HomeDetailTextViewBinding>(inflate!!)!!
                    binding.srlRefresh.setEnableLoadMore(false)
                    mViewModel.mTextCardLiveData.observe(this, Observer {
                        binding.srlRefresh.closeHeaderOrFooter()
                        mTextCard = it
                        lazyLoadData()
                    })
                    binding.srlRefresh.setOnRefreshListener {
                        mViewModel.getTextCard(mTextCard.textcardid!!)
                    }
                    mDelegate = TextProxy(binding, this)
                }
                mDelegate.initView()
            }
            CARD_CATEGORY.TYPE_TOPIC._value -> {
                val inflate = mBinding!!.viewTopic.viewStub?.inflate()
                val binding = DataBindingUtil.getBinding<HomeDetailTopicViewBinding>(inflate!!)!!
                mDelegate = TopicProxy(binding, this)
                mDelegate.initView()
            }
            CARD_CATEGORY.TYPE_MUSIC._value -> {
                val inflate = mBinding!!.viewMusic.viewStub?.inflate()
                val binding = DataBindingUtil.getBinding<HomeDetailMusicViewBinding>(inflate!!)!!
                mDelegate = MusicProxy(binding, this)
                mDelegate.initView()
            }
            else -> {
                val inflate = mBinding!!.viewText.viewStub?.inflate()
                val binding = DataBindingUtil.getBinding<HomeDetailTextViewBinding>(inflate!!)!!
                mDelegate = TextProxy(binding, this)
                mDelegate.initView()
            }
        }
    }

     fun getPosition(): Int {
        return mPosition!!
    }
}