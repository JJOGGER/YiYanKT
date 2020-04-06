package ex

import android.os.Handler
import android.os.Looper
import com.jogger.utils.LogUtils
import com.tencent.cos.xml.CosXmlService
import com.tencent.cos.xml.CosXmlServiceConfig
import com.tencent.cos.xml.exception.CosXmlClientException
import com.tencent.cos.xml.exception.CosXmlServiceException
import com.tencent.cos.xml.listener.CosXmlResultListener
import com.tencent.cos.xml.model.CosXmlRequest
import com.tencent.cos.xml.model.CosXmlResult
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider
import java.util.*

class CosService {
    private var mCosServiceResultListener: CosServiceResultListener? = null
    private val mHandler = Handler(Looper.getMainLooper())

    interface CosServiceResultListener {
        fun onSuccess(path: String)
        fun onFailure()
    }

    fun setCosServiceResultListener(listener: CosServiceResultListener) {
        mCosServiceResultListener = listener
    }

    companion object {
        val BUCKET = "total-1258939334"
        var sCosXmlService: CosXmlService? = null
        fun getCosXmlService(): CosXmlService {
            if (sCosXmlService == null) {
                val region = "ap-guangzhou"
                //创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
                val serviceConfig = CosXmlServiceConfig.Builder()
                    .setRegion(region)
                    .setDebuggable(true)
                    .builder()
                val credentialProvider = ShortTimeCredentialProvider(
                    "AKIDsbCbuCVUTHAwX0Q5LTTJ9sUTHwQjPnZJ",
                    "ZyBD5mHbfQ0PeibJpDKG1c2HLEySltqz", 300
                )
                sCosXmlService = CosXmlService(sApplication, serviceConfig, credentialProvider)
            }
            return sCosXmlService!!
        }
    }

    private val mTransferConfig by lazy {
        TransferConfig.Builder().build()
    }
    private val mTransferManager by lazy {
        TransferManager(getCosXmlService(), mTransferConfig)
    }

    fun uploadImg(filePath: String) {
        val file = FileUtil.compressImg(filePath)
        if (file == null || !file.exists()) return
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val cosPath =
            "app/upload/" + year + "/" + month + "/" + day + "/" + System.currentTimeMillis() + ".jpg" //即对象到 COS 上的绝对路径, 格式如 cosPath = "text.txt";
        val uploadId: String? = null //若存在初始化分片上传的 UploadId，则赋值对应 uploadId 值用于续传，否则，赋值 null。
//上传对象
        val cosxmlUploadTask = mTransferManager.upload(BUCKET, cosPath, filePath, uploadId)
//设置上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener({ complete, target ->
            val progress = 1.0f * complete / target * 100
        })
//设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(object : CosXmlResultListener {
            override fun onSuccess(request: CosXmlRequest, result: CosXmlResult) {
                LogUtils.d("-----------Success: " + result.accessUrl)
                file.delete()
                mHandler.post {
                    if (mCosServiceResultListener != null)
                        mCosServiceResultListener!!.onSuccess(result.accessUrl)
                }

            }

            override fun onFail(
                request: CosXmlRequest,
                exception: CosXmlClientException?,
                serviceException: CosXmlServiceException
            ) {
                mHandler.post {
                    LogUtils.e("-----------Failed: " + (exception?.toString() ?: serviceException.message))
                    if (mCosServiceResultListener != null)
                        mCosServiceResultListener!!.onFailure()
                }
            }
        })
        //设置任务状态回调, 可以查看任务过程
        cosxmlUploadTask.setTransferStateListener({ state -> LogUtils.d("-----------Task state:" + state.name) })
    }
}