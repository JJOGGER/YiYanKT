package ex

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.jogger.utils.ToastHelper
import java.security.MessageDigest

const val MODULE_HOME_MAIN = "/module_home/main"
const val MODULE_STAR_MAIN = "/module_star/main"
const val MODULE_MESSAGE_MAIN = "/module_message/main"
const val MODULE_MINE_MAIN = "/module_mine/main"

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

fun String.MD5(): String {
    try {
        val messageDigest = MessageDigest.getInstance("MD5")
        messageDigest.update(this.toByteArray())
        val arrayOfByte = messageDigest.digest()
        val sb = StringBuffer()
        for (i in arrayOfByte.indices) {
            val j = 0xFF and arrayOfByte[i].toInt()
            if (j < 16)
                sb.append("0")
            sb.append(Integer.toHexString(j))
        }
        return sb.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""

}