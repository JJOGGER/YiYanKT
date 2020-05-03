package com.jogger.event

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.NonNull
import java.io.Serializable
import java.util.*

open class BaseActionEvent(action: Int) {
    var mAction=action

    private var mExtras: Bundle? = null

    open fun putExtra(name: String?, value: Boolean) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putBoolean(name, value)
    }

    open fun putExtra(name: String?, value: Byte) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putByte(name, value)
    }

    open fun putExtra(name: String?, value: Char) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putChar(name, value)
    }

    open fun putExtra(name: String?, value: Short) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putShort(name, value)
    }

    open fun putExtra(name: String?, value: Int) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putInt(name, value)
    }

    open fun putExtra(name: String?, value: Long) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putLong(name, value)
    }

    open fun putExtra(name: String?, value: Float) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putFloat(name, value)
    }

    open fun putExtra(name: String?, value: Double) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putDouble(name, value)
    }

    open fun putExtra(name: String?, value: String?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putString(name, value)
    }

    open fun putExtra(name: String?, value: CharSequence?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putCharSequence(name, value)
    }

    open fun putExtra(name: String?, value: Parcelable?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putParcelable(name, value)
    }

    open fun putExtra(name: String?, value: Array<Parcelable?>?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putParcelableArray(name, value)
    }

    open fun putParcelableArrayListExtra(
        name: String?,
        value: ArrayList<out Parcelable?>?
    ) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putParcelableArrayList(name, value)
    }

    open fun putIntegerArrayListExtra(
        name: String?,
        value: ArrayList<Int?>?
    ) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putIntegerArrayList(name, value)
    }

    open fun putStringArrayListExtra(
        name: String?,
        value: ArrayList<String?>?
    ) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putStringArrayList(name, value)
    }

    open fun putCharSequenceArrayListExtra(
        name: String?,
        value: ArrayList<CharSequence?>?
    ) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putCharSequenceArrayList(name, value)
    }

    open fun putExtra(name: String?, value: Serializable?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putSerializable(name, value)
    }

    open fun putExtra(name: String?, value: BooleanArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putBooleanArray(name, value)
    }

    open fun putExtra(name: String?, value: ByteArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putByteArray(name, value)
    }

    open fun putExtra(name: String?, value: ShortArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putShortArray(name, value)
    }

    open fun putExtra(name: String?, value: CharArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putCharArray(name, value)
    }

    open fun putExtra(name: String?, value: IntArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putIntArray(name, value)
    }

    open fun putExtra(name: String?, value: LongArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putLongArray(name, value)
    }

    open fun putExtra(name: String?, value: FloatArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putFloatArray(name, value)
    }

    open fun putExtra(name: String?, value: DoubleArray?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putDoubleArray(name, value)
    }

    open fun putExtra(name: String?, value: Array<String?>?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putStringArray(name, value)
    }

    open fun putExtra(
        name: String?,
        value: Array<CharSequence?>?
    ) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putCharSequenceArray(name, value)
    }

    open fun putExtra(name: String?, value: Bundle?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putBundle(name, value)
    }


    open fun putExtras(@NonNull extras: Bundle?) {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        mExtras!!.putAll(extras)
    }

    open fun getExtras(): Bundle? {
        return mExtras
    }

    open fun getBooleanExtra(name: String?, defaultValue: Boolean): Boolean {
        return if (mExtras == null) defaultValue else mExtras!!.getBoolean(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getByteExtra(name: String?, defaultValue: Byte): Byte {
        return if (mExtras == null) defaultValue else mExtras!!.getByte(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getShortExtra(name: String?, defaultValue: Short): Short {
        return if (mExtras == null) defaultValue else mExtras!!.getShort(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getCharExtra(name: String?, defaultValue: Char): Char {
        return if (mExtras == null) defaultValue else mExtras!!.getChar(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getIntExtra(name: String?, defaultValue: Int): Int {
        return if (mExtras == null) defaultValue else mExtras!!.getInt(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getLongExtra(name: String?, defaultValue: Long): Long {
        return if (mExtras == null) defaultValue else mExtras!!.getLong(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getFloatExtra(name: String?, defaultValue: Float): Float {
        return if (mExtras == null) defaultValue else mExtras!!.getFloat(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getDoubleExtra(name: String?, defaultValue: Double): Double {
        return if (mExtras == null) defaultValue else mExtras!!.getDouble(name, defaultValue)
    }

    /**
     * @see .putExtra
     */
    open fun getStringExtra(name: String?): String? {
        return if (mExtras == null) null else mExtras!!.getString(name)
    }

    /**
     * @see .putExtra
     */
    open fun getCharSequenceExtra(name: String?): CharSequence? {
        return if (mExtras == null) null else mExtras!!.getCharSequence(name)
    }

    /**
     * @see .putExtra
     */
    open fun <T : Parcelable?> getParcelableExtra(name: String?): T? {
        return if (mExtras == null) null else mExtras!!.getParcelable<T>(name)
    }

    /**
     * @see .putExtra
     */
    open fun getParcelableArrayExtra(name: String?): Array<Parcelable?>? {
        return if (mExtras == null) null else mExtras!!.getParcelableArray(name)
    }

    /**
     * @see .putParcelableArrayListExtra
     */
    open fun <T : Parcelable?> getParcelableArrayListExtra(name: String?): ArrayList<T>? {
        return if (mExtras == null) null else mExtras!!.getParcelableArrayList(name)
    }

    /**
     * @see .putExtra
     */
    open fun getSerializableExtra(name: String?): Serializable? {
        return if (mExtras == null) null else mExtras!!.getSerializable(name)
    }

    /**
     * @see .putIntegerArrayListExtra
     */
    open fun getIntegerArrayListExtra(name: String?): ArrayList<Int?>? {
        return if (mExtras == null) null else mExtras!!.getIntegerArrayList(name)
    }

    /**
     * @see .putStringArrayListExtra
     */
    open fun getStringArrayListExtra(name: String?): ArrayList<String?>? {
        return if (mExtras == null) null else mExtras!!.getStringArrayList(name)
    }

    /**
     * @see .putCharSequenceArrayListExtra
     */
    open fun getCharSequenceArrayListExtra(name: String?): ArrayList<CharSequence?>? {
        return if (mExtras == null) null else mExtras!!.getCharSequenceArrayList(name)
    }

    /**
     * @see .putExtra
     */
    open fun getBooleanArrayExtra(name: String?): BooleanArray? {
        return if (mExtras == null) null else mExtras!!.getBooleanArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getByteArrayExtra(name: String?): ByteArray? {
        return if (mExtras == null) null else mExtras!!.getByteArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getShortArrayExtra(name: String?): ShortArray? {
        return if (mExtras == null) null else mExtras!!.getShortArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getCharArrayExtra(name: String?): CharArray? {
        return if (mExtras == null) null else mExtras!!.getCharArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getIntArrayExtra(name: String?): IntArray? {
        return if (mExtras == null) null else mExtras!!.getIntArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getLongArrayExtra(name: String?): LongArray? {
        return if (mExtras == null) null else mExtras!!.getLongArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getFloatArrayExtra(name: String?): FloatArray? {
        return if (mExtras == null) null else mExtras!!.getFloatArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getDoubleArrayExtra(name: String?): DoubleArray? {
        return if (mExtras == null) null else mExtras!!.getDoubleArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getStringArrayExtra(name: String?): Array<String?>? {
        return if (mExtras == null) null else mExtras!!.getStringArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getCharSequenceArrayExtra(name: String?): Array<CharSequence?>? {
        return if (mExtras == null) null else mExtras!!.getCharSequenceArray(name)
    }

    /**
     * @see .putExtra
     */
    open fun getBundleExtra(name: String?): Bundle? {
        return if (mExtras == null) null else mExtras!!.getBundle(name)
    }
}