package ex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import com.jogger.utils.LogUtils
import java.io.*

object FileUtil {
    private val FILE_NAME = "yiyan"
    private val IMG_CACHE_FILE_PATH = FILE_NAME + File.separator + "img"
    fun compressImg(filePath: String): File? {
        var file = File(filePath)
        LogUtils.i("-------->>" + filePath + ":" + file.exists())
        if (!file.exists()) return file
        val bitmap = BitmapFactory.decodeFile(filePath) ?: return null
        val baos = ByteArrayOutputStream()
        var options = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
        val date = System.currentTimeMillis()
        file = File(getImgCacheFile(), "$date.jpg")
        while (baos.toByteArray().size / 1024 > 1024) {//大小控制在1m以内
            baos.reset()
            options -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
        }
        val bais = ByteArrayInputStream(baos.toByteArray())
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            val buf = ByteArray(1024)
            var ret: Int
            ret = bais.read(buf, 0, buf.size)
            while (ret != -1) {
                fos.write(buf, 0, ret)
                ret = bais.read(buf, 0, buf.size)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            bais.close()
            baos.close()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }

    fun getImgCacheFile(): File? {
        val sdCardPath = getSDCardPath()
        if (TextUtils.isEmpty(sdCardPath)) {
            return null
        }
        val fileDir = File(sdCardPath + File.separator + IMG_CACHE_FILE_PATH)
        if (!fileDir.exists())
            fileDir.mkdirs()
        return fileDir
    }

    fun getSDCardPath(): String? {
        var sdcardDir: File? = null
        // 判断SDCard是否存在
        val sdcardExist =
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState() == Environment.MEDIA_SHARED
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory()
        }
        return if (sdcardDir != null) {
            sdcardDir.toString() + File.separator
        } else {
            null
        }
    }
}