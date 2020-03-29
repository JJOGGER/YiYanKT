package ex

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.jogger.utils.ToastHelper
import java.io.Serializable
import java.util.*

const val MODULE_HOME_MAIN = "/module_home/main"
const val MODULE_STAR_MAIN = "/module_star/main"
const val MODULE_MESSAGE_MAIN = "/module_message/main"
const val MODULE_MINE_MAIN = "/module_mine/main"
const val MODULE_LOGIN = "/module_app/login"
const val TEXT_CARD_DETAIL = "/module_home/text_card_detail"
const val TOPIC_MIAN_DETAIL = "/module_home/topic_mian_detail"

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

fun getFragment(path: String): Fragment {
    return ARouter.getInstance().build(path).navigation() as Fragment
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