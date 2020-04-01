package ex

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcelable
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.entity.WindowModel
import com.jogger.base.R
import com.jogger.utils.ToastHelper
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.kotlin.skin
import com.qmuiteam.qmui.skin.QMUISkinManager
import java.io.Serializable
import java.util.*

const val MODULE_HOME_MAIN = "/module_home/main"
const val MODULE_STAR_MAIN = "/module_star/main"
const val MODULE_MESSAGE_MAIN = "/module_message/main"
const val MODULE_MINE_MAIN = "/module_mine/main"
const val MODULE_LOGIN = "/module_app/login"
const val TEXT_CARD_DETAIL = "/module_home/text_card_detail"
const val TOPIC_MIAN_DETAIL = "/module_home/topic_mian_detail"
const val COMMENT_DETAIL = "/module_home/comment_detail"
const val USER_HOME_PAGE = "/module_mine/user_home_page"
const val USER_BOOK_PAGE = "/module_mine/user_book_page"

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */

fun showToast(resId: Int) {
    ToastHelper.showToast(resId)
}

fun showToast(text: String) {
    ToastHelper.showToast(text)
}

fun getFragment(path: String): Fragment? {
    return ARouter.getInstance().build(path).navigation() as Fragment?
}

fun getActivity(path: String): Activity {
    return ARouter.getInstance()
        .build(path)
        .navigation() as Activity
}

/**
 * ARouter跳转Activity
 *
 * @param url 目标Activity Url
 */
fun toActivity(mContext: Context, url: String) {
    toActivity(mContext, url, null)
}

fun toActivity(mContext: Activity, url: String) {
    toActivity(mContext, url, null)
}

fun toActivity(mContext: Activity, url: String, params: Map<String, *>?) {
    toActivity(mContext.baseContext, url, params)
}

/**
 * ARouter跳转Activity
 *
 * @param url    目标Activity Url
 * @param params 参数
 */
fun toActivity(context: Context, url: String, params: Map<String, *>?) {
    if (TextUtils.isEmpty(url)) {
        throw NullPointerException("url is null")
    }
    val postcard = ARouter.getInstance()
        .build(url)
    if (params != null) {
        for ((key) in params) {
            val value = params[key]
            if (value is String) {
                postcard.withString(key, value as String?)
            } else if (value is Boolean) {
                postcard.withBoolean(key, value)
            } else if (value is Int) {
                postcard.withInt(key, value)
            } else if (value is Float) {
                postcard.withFloat(key, value)
            } else if (value is Double) {
                postcard.withDouble(key, value)
            } else if (value is Long) {
                postcard.withLong(key, value)
            } else if (value is Short) {
                postcard.withShort(key, value)
            } else if (value is HashMap<*, *>) {
                postcard.withSerializable(key, value as HashMap<*, *>?)
            } else if (value is Parcelable) {
                postcard.withParcelable(key, value as Parcelable?)
            } else if (value is ArrayList<*>) {
                if (value[0] is Parcelable) {
                    postcard.withParcelableArrayList(key, value as ArrayList<out Parcelable>?)
                } else if (value[0] is Serializable) {
                    postcard.withSerializable(key, value as ArrayList<*>?)
                }
            } else {
                postcard.withSerializable(key, value as Serializable?)
            }
        }
    }
    postcard.navigation(context)
}


fun showActionAnimation(view: View) {
    val animator = ValueAnimator.ofFloat(1f, 1.3f, 1f)
    with(animator) {
        setDuration(1000)
        addUpdateListener {
            val animatedValue = it.animatedValue as Float
            print("-----------animatedValue:$animatedValue")
            view.scaleX = animatedValue
            view.scaleY = animatedValue
        }
        start()
    }
}

var popupWindow: PopupWindow? = null

interface OnFunctionWindowClickListener {
    fun onClick(popupWindow: PopupWindow, index: Int)
}

fun showFunctionWindow(context: Activity, vararg windowModels: WindowModel, listener: OnFunctionWindowClickListener?) {
    val resources = context.resources
    val view = LayoutInflater.from(context).inflate(R.layout.window_common_function, null) as ViewGroup

//    val textColor = QMUIResHelper.getAttrColorStateList(
//        view.getContext(),
//        QMUISkinManager.defaultInstance(context).currentTheme,
//        R.attr.app_skin_common_title_text_color
//    )!!.defaultColor
    for (i in 0..windowModels.size - 1) {
        view.addView(TextView(context).apply {
            setText(windowModels[i].text)
            if (windowModels[i].type == WindowModel.TYPE_ERROR) {
                skin {
                    it.textColor(R.attr.app_skin_common_title_text_color)
                }
                setTextColor(Color.RED)
            } else {
                skin { it.textColor(R.attr.app_skin_common_title_text_color) }
//                setTextColor(textColor)
            }
//            setBackgroundColor(backgroundColor)
            layoutParams =
                ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                )
            setPadding(
                0,
                resources.getDimensionPixelOffset(R.dimen.dp_16),
                0,
                resources.getDimensionPixelOffset(R.dimen.dp_16)
            )
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelOffset(R.dimen.text_size_16).toFloat())
            onClick {
                if (listener != null)
                    listener.onClick(popupWindow!!, i)
            }
        })
        val line = View(context)
        if (i == windowModels.size - 1) {
            line.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelOffset(R.dimen.text_size_6)
            )
            line.setBackgroundColor(resources.getColor(R.color.color_80e2e2e2))
        } else {
            line.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelOffset(R.dimen.text_size_dot5)
            )
            line.setBackgroundColor(resources.getColor(R.color.gray_999999))
        }
        view.addView(line)
    }
    val cancel = TextView(context)
        .apply {
            text = "取消"
            skin {
                it.textColor(R.attr.app_skin_common_title_text_color)
            }
//            setTextColor(textColor)
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setPadding(
                0,
                context.resources.getDimensionPixelOffset(R.dimen.dp_16),
                0,
                context.resources.getDimensionPixelOffset(R.dimen.dp_16)
            )
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelOffset(R.dimen.text_size_16).toFloat())
            gravity = Gravity.CENTER
            onClick { popupWindow?.dismiss() }
        }
    view.addView(cancel)
    if (popupWindow == null) {
        popupWindow = PopupWindow(
            view, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        popupWindow!!.setOutsideTouchable(true)
        popupWindow!!.isFocusable = true
        popupWindow!!.animationStyle = R.style.AnimBottom
        popupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow!!.setOnDismissListener {
            backgroundAlpha(context, 1f)
        }
    } else {
        popupWindow!!.contentView = view
    }
    backgroundAlpha(context, 0.5f)
    if (context.isFinishing || context.isDestroyed) return
    QMUISkinManager.defaultInstance(context).register(popupWindow!!)
    popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)
}

fun dismissWindow() {
    if (popupWindow != null) {
        popupWindow!!.dismiss()
        popupWindow = null
    }
}

private fun backgroundAlpha(context: Activity, bgAlpha: Float) {
    val layoutParams = context.window.attributes
    layoutParams.alpha = bgAlpha
    context.window.setAttributes(layoutParams)
}