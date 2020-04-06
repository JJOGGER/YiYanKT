package com.jogger.module_mine.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.jogger.base.BaseFragment
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineFragmentPublishCardBinding
import com.jogger.module_mine.viewmodel.PublishCardViewModel
import com.jogger.utils.GetImagePath
import com.jogger.utils.LogUtils
import com.jogger.utils.SoftKeyBoardListener
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import ex.FileUtil
import ex.sApplication
import java.io.File

class PublishMusicFragment : BaseFragment<PublishCardViewModel, com.jogger.module_mine.databinding.MineFragmentPublishMusicBinding>() {

    companion object {
        fun getInstance(): PublishMusicFragment {
            val fragment = PublishMusicFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutId() = R.layout.mine_fragment_publish_music

    override fun initView(savedInstanceState: Bundle?) {
    }

}