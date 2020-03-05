package ex

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.jogger.manager.QDSkinManager
import com.tencent.mmkv.MMKV
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import android.R
import android.R.attr.colorPrimary
import com.jogger.widget.YiYanHeader
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator


/**
 * Created by jogger on 2020/2/26
 * 描述：
 */

lateinit var sApplication: Application

fun initApp(context: Context) {
    initApp(context.applicationContext as Application)
}

fun initApp(app: Application) {
    sApplication = app
    MMKV.initialize(app)
    ARouter.openLog()
    ARouter.openDebug()
    ARouter.init(app)
    QDSkinManager.install(app)
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
        override fun createRefreshHeader(context: Context, layout: RefreshLayout) = YiYanHeader(app)
    })
}

