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
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.jogger.base.BaseFragment
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.request.PublishTextCardRequest
import com.jogger.module_mine.R
import com.jogger.module_mine.activity.MyBookActivity
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
import ex.showToast
import kotlinx.android.synthetic.main.mine_fragment_publish_card.*
import java.io.File

class PublishCardFragment : BaseFragment<PublishCardViewModel, MineFragmentPublishCardBinding>(),
    RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private val CODE_GALLERY_REQUEST_N = 0X0a
    private val CODE_GALLERY_REQUEST = 0X0c
    private val CODE_RESULT_REQUEST = 0X0b
    private lateinit var mPermission: RxPermissions
    private val mRequest = PublishTextCardRequest()
    private val mCropFile =
        File(FileUtil.getImgCacheFile()!!.absolutePath, "${System.currentTimeMillis()}.jpg")//裁剪后的File对象

    companion object {
        fun getInstance(): PublishCardFragment {
            val fragment = PublishCardFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutId() = R.layout.mine_fragment_publish_card

    override fun initView(savedInstanceState: Bundle?) {
        mPermission = RxPermissions(this)
        mBinding!!.flImg.onClick {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent, CODE_GALLERY_REQUEST_N)
            } else
                startActivityForResult(intent, CODE_GALLERY_REQUEST)
        }
        mBinding!!.scOrigin.setOnCheckedChangeListener(this)
        mBinding!!.scSecret.setOnCheckedChangeListener(this)
        SoftKeyBoardListener.setListener(mContext!!, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardHide(height: Int) {
                mBinding!!.flImg.visibility = View.VISIBLE
                mBinding!!.etContent.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                mBinding!!.llAction.visibility = View.VISIBLE
            }

            override fun keyBoardShow(height: Int) {
                mBinding!!.flImg.visibility = View.GONE
                mBinding!!.etContent.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f)
                mBinding!!.llAction.visibility = View.GONE
            }
        })
    }

    /**
     * 截取图片
     */
    private fun cropRawPhoto(uri: Uri?): Boolean {
        if (uri == null)
            return false
        val intent = Intent("com.android.camera.action.CROP")
        if (intent.resolveActivity(sApplication.getPackageManager()) == null) {
            return false
        }
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val outPutUri = Uri.fromFile(mCropFile)
            intent.setDataAndType(uri, "image/*")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri)
            intent.putExtra("noFaceDetection", false)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val outPutUri = Uri.fromFile(mCropFile)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val url = GetImagePath.getPath(mContext, uri)//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                if (!TextUtils.isEmpty(url))
                    intent.setDataAndType(Uri.fromFile(File(url)), "image/*")
            } else {
                intent.setDataAndType(uri, "image/*")
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri)
        }

        // 设置裁剪
        intent.putExtra("crop", "true")
        //X方向上的比例
        intent.putExtra("aspectX", 1)
        //Y方向上的比例
        intent.putExtra("aspectY", 1)
        //是否保留比例
        intent.putExtra("scale", true)
        intent.putExtra("outputX", QMUIDisplayHelper.dp2px(mContext, 64))
        intent.putExtra("outputY", QMUIDisplayHelper.dp2px(mContext, 64))
        intent.putExtra("white_return-data", false)
        //输出图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString())
        //裁剪图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropFile))
        startActivityForResult(intent, CODE_RESULT_REQUEST)
        return true
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.sc_origin -> {
                mRequest.original = if (isChecked) 1 else 0
            }
            R.id.sc_secret -> {
                mRequest.priv = if (isChecked) 1 else 0
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rb_text ->
                mRequest.category = CARD_CATEGORY.TYPE_TEXT._value
            R.id.rb_film -> mRequest.category = CARD_CATEGORY.TYPE_FILM._value
            R.id.rb_record -> mRequest.category = CARD_CATEGORY.TYPE_RECORD._value
            R.id.rb_poetry -> mRequest.category = CARD_CATEGORY.TYPE_POETRY._value
            R.id.rb_word -> mRequest.category = CARD_CATEGORY.TYPE_WORD._value
        }
    }

    fun onNextAction() {
        LogUtils.e("$mRequest")
        mRequest.title = et_title.text.toString().trim()
        mRequest.content = et_content.text.toString().trim()
        mRequest.from = et_from.text.toString().trim()
        if (TextUtils.isEmpty(mRequest.content)) {
            showToast("还未写下文字")
            return
        }
        MyBookActivity.navTo(mContext!!, mRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CODE_GALLERY_REQUEST_N -> {
                    val path = GetImagePath.getPath(mContext, data?.getData())
                    LogUtils.e("---------->path:$path")
                    if (!TextUtils.isEmpty(path)) {
                        val imgUri = File(path)
                        val dataUri = FileProvider.getUriForFile(
                            mContext!!,
                            sApplication.getPackageName() + ".fileprovider",
                            imgUri
                        )
                        if (!cropRawPhoto(dataUri)) {
                            mRequest.pic = path
                        }

                    }
                }
                CODE_GALLERY_REQUEST -> {
                    if (!cropRawPhoto(data?.getData())) {
                        val path = GetImagePath.getPath(mContext, data?.getData())
                        mRequest.pic = path
                        LogUtils.e("---------->path:$path")
                        mBinding!!.tvHint.visibility = View.GONE
                        mBinding!!.ivImg.visibility = View.VISIBLE
                        Glide.with(mContext!!)
                            .load(path)
                            .into(mBinding!!.ivImg)
                    }
                }
                CODE_RESULT_REQUEST -> if (mCropFile.exists()) {
//                    mPath = mCropFile.getAbsolutePath()
                    mRequest.pic = mCropFile.getAbsolutePath()
                }
            }
        }
    }
}