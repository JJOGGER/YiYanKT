package ex

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.jogger.manager.QDSkinManager
import com.tencent.mmkv.MMKV

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */

 lateinit var  sApplication:Application
fun initApp(context: Context) {
    initApp(context.applicationContext as Application)
}

fun initApp(app: Application) {
    sApplication =app
    MMKV.initialize(app)
    ARouter.openLog()
    ARouter.openDebug()
    ARouter.init(app)
    QDSkinManager.install(app)

}

