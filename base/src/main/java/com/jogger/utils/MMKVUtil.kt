package com.jogger.utils

import com.tencent.mmkv.MMKV

/**
 * Created by jogger on 2020/3/17
 * 描述：
 */
class MMKVUtil private constructor(name: String) {
    /**
     * 默认配置文件
     */
    val mName = name

    init {
        if (mName == DEFAULT_MCONFIG_FILE_NAME) {
            mmkv = MMKV.defaultMMKV()
        } else {
            mmkv = MMKV.mmkvWithID(mName)
        }
    }

    companion object {
        val DEFAULT_MCONFIG_FILE_NAME = "default_mconfig"
        @Volatile
        private var instance: MMKVUtil? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MMKVUtil(DEFAULT_MCONFIG_FILE_NAME).apply {
                instance = this
            }
        }

        fun getInstance(name: String) = instance ?: synchronized(this) {
            instance ?: MMKVUtil(name).apply {
                instance = this
            }
        }

        private lateinit var mmkv: MMKV
    }

    fun <T> setValue(key: String, value: T) {
        if (value is Int) {
            mmkv.encode(key, value as Int)
        } else if (value is String) {
            mmkv.encode(key, value as String)
        } else if (value is Long) {
            mmkv.encode(key, value as Long)
        } else if (value is Float) {
            mmkv.encode(key, value as Float)
        } else if (value is Double) {
            mmkv.encode(key, value as Double)
        } else if (value is Boolean) {
            mmkv.encode(key, value as Boolean)
        } else if (value is ByteArray) {
            mmkv.encode(key, value as ByteArray)
        } else if (value is Set<*>) {
            mmkv.encode(key, value as Set<String>)
        }
    }

    fun getIntValue(key: String, defValue: Int): Int {
        return mmkv.getInt(key, 0)
    }

    fun getStringValue(key: String, defValue: String): String? {
        return mmkv.getString(key, defValue)
    }

    fun getBooleanValue(key: String, defValue: Boolean): Boolean {
        return mmkv.getBoolean(key, defValue)
    }

    fun getFloatValue(key: String, defValue: Float): Float {
        return mmkv.getFloat(key, defValue)
    }

    fun getDoubleValue(key: String, defValue: Double): Double {
        return mmkv.decodeDouble(key, defValue)
    }

    fun getLongValue(key: String, defValue: Long): Long {
        return mmkv.getLong(key, defValue)
    }

    fun getByteArrayValue(key: String): ByteArray {
        return mmkv.decodeBytes(key)
    }

    fun getSetValue(key: String, defValue: Set<String>): Set<String> {
        return mmkv.decodeStringSet(key, defValue)
    }

    fun clear(keys: Array<String>) {
        mmkv.removeValuesForKeys(keys)
    }


}