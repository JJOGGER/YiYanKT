package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.jogger.base.BaseActivity
import com.jogger.entity.request.PublishTextCardRequest
import com.jogger.event.PublishEvent
import com.jogger.manager.AssetsManager
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineActivitySelectModeBinding
import com.jogger.module_mine.viewmodel.PublishCardViewModel
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.kotlin.registOnClicks
import com.qmuiteam.qmui.kotlin.skin
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.mine_activity_select_mode.*
import kotlinx.android.synthetic.main.mine_rv_comment_item.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SelectModeActivity :
    BaseActivity<PublishCardViewModel, MineActivitySelectModeBinding>() {
    private var mImgStyle: Int = 0//非会员支持0，1，11
    private var mFontStyle: Int = 1
    private var mGravityStyle: Int = 0//1代表局左，0代表居中
    private var mTextDir: Int = 1//1代表横向，0代表竖向
    private val mRequest: PublishTextCardRequest by lazy {
        intent.getParcelableExtra<PublishTextCardRequest>(ex.PUBLISH_TEXTCARD_REQUEST)
    }

    companion object {
        fun navTo(context: Context, request: PublishTextCardRequest) {
            val intent = Intent(context, SelectModeActivity::class.java)
            intent.putExtra(ex.PUBLISH_TEXTCARD_REQUEST, request)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_select_mode

    override fun init(savedInstanceState: Bundle?) {
        with(mTopBar) {
            setTitle("「选择模板」")
            addLeftBackImageButton().onClick {
                finish()
            }
            with(addRightTextButton("完成", R.id.action_complete)) {
                skin {
                    it.textColor(R.attr.app_skin_text_selected_color)
                }
                onClick {
                    val type = StringBuilder()
                    type.append("yyh_")
                    type.append(mImgStyle)
                    type.append("_")
                    type.append(mTextDir)
                    type.append("_")
                    type.append(mFontStyle)
                    type.append("_")
                    type.append(mGravityStyle)
                    mRequest.type = type.toString()
                    mViewModel.publishCard(mRequest)
                }
            }
            with(addRightTextButton("预览", R.id.action_preview)) {
                skin {
                    it.textColor(R.attr.app_skin_text_selected_color)
                }
            }
        }
        if (!TextUtils.isEmpty(mRequest.pic)) {
            iv_header.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(mRequest.pic)
                .into(iv_header)
        }
        if (mTextDir == 1) {
            ll_verticle.visibility = View.VISIBLE
            ll_horizontal.visibility = View.GONE
        } else {
            ll_verticle.visibility = View.GONE
            ll_horizontal.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(mRequest.title)) {
            tv_title1.visibility = View.VISIBLE
            tv_title1.text = mRequest.title
            tv_title2.visibility = View.VISIBLE
            tv_title2.text = mRequest.title
        }
        tv_content1.text = mRequest.content
        tv_content2.text = mRequest.content
        mViewModel.mPublishLiveData.observe(this, Observer {
            finish()
        })
        initClick()
    }

    private fun initClick() {
        rg_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_1 -> {
                        mTextDir = 1
                        ll_verticle.visibility = View.VISIBLE
                        ll_horizontal.visibility = View.GONE
                    }
                    R.id.rb_2 -> {
                        mTextDir = 0
                        ll_verticle.visibility = View.GONE
                        ll_horizontal.visibility = View.VISIBLE
                    }
                }
            }

        })
        registOnClicks(iv_img_style, iv_font, iv_gravity, block = {
            when (it) {
                iv_img_style -> {
                    if (mImgStyle == 0) {
                        iv_header.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            resources.getDimensionPixelOffset(R.dimen.dp_200)
                        ).apply {
                            topMargin = resources.getDimensionPixelOffset(R.dimen.dp_10)
                            leftMargin = topMargin
                            rightMargin = leftMargin
                        }
                        iv_header.isCircle = false
                        mImgStyle = 1
                    } else if (mImgStyle == 1) {
                        iv_header.layoutParams = LinearLayout.LayoutParams(
                            QMUIDisplayHelper.getScreenWidth(mContext) - resources.getDimensionPixelOffset(R.dimen.dp_20),
                            QMUIDisplayHelper.getScreenWidth(mContext) - resources.getDimensionPixelOffset(R.dimen.dp_20)
                        )
                        iv_header.isCircle = false
                        mImgStyle = 11
                    } else {
                        iv_header.layoutParams = LinearLayout.LayoutParams(
                            resources.getDimensionPixelOffset(R.dimen.dp_140),
                            resources.getDimensionPixelOffset(R.dimen.dp_140)
                        ).apply {
                            topMargin = resources.getDimensionPixelOffset(R.dimen.dp_50)
                        }
                        iv_header.isCircle = true
                        mImgStyle = 0
                    }
                }
                iv_font -> {
                    if (mFontStyle < 5) {
                        mFontStyle++
                    } else {
                        mFontStyle = 1
                    }
                    tv_title.typeface = AssetsManager.getTypeFaceByType(mFontStyle)
                    tv_content.typeface = tv_title.typeface
                }
                iv_gravity -> {
                    mGravityStyle = 1 - mGravityStyle
                    if (mGravityStyle == 0) {
                        tv_title.gravity = Gravity.CENTER_HORIZONTAL
                        tv_content.gravity = Gravity.CENTER_HORIZONTAL
                    } else {
                        tv_title.gravity = Gravity.START
                        tv_content.gravity = Gravity.START
                    }
                }
            }
        })
    }


    override fun isNeedEventBus() = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPublishEvent(event: PublishEvent) {
        if (event.mAction == PublishEvent.PUBLISH_SUCCESS) {
            finish()
        }
    }
}
