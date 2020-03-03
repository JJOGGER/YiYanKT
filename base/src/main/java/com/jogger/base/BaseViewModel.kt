package com.jogger.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jogger.event.Message
import com.jogger.event.SingleLiveEvent
import com.jogger.http.basic.exception.ExceptionHandle
import com.jogger.http.basic.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.ParameterizedType

/**
 * Created by jogger on 2020/2/27
 * 描述：
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    val defUI: UIChange by lazy { UIChange() }
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }

    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

//    fun launchGo(
//        block: suspend CoroutineScope.() -> Unit,
//        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {
//            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
//        },
//        complete: suspend CoroutineScope.() -> Unit = {},
//        isShowDialog: Boolean = true
//    ) {
//        if (isShowDialog) defUI.showDialog.call()
//        launchUI {
//            handleException(
//                withContext(Dispatchers.IO) { block },
//
//                { error(it) },
//                {
//                    defUI.dismissDialog.call()
//                    complete()
//                }
//            )
//        }
//    }

    private suspend fun <T> executeResponse(res: T, success: suspend CoroutineScope.(T) -> Unit) {
        coroutineScope {
            if (res != null)
                success(res)
            else throw ResponseThrowable(-1, "")
        }
    }

    private suspend fun <T> executeResponse2(res: Any, success: suspend CoroutineScope.(T) -> Unit) {
        coroutineScope {
            //            res.toString()
            val clz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
            val obj = Gson().fromJson(res.toString(), clz)
            success(obj)
        }
    }

    fun <T> launchTest(
        block: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = true
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    executeResponse(res) { success(it) }
                },
                {
                    error(it)
                },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    fun <T> launchOnlyresult(
        block: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = true
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                {
                    withContext(Dispatchers.IO) {
                        block()
                    }
                },
                { res ->
                    executeResponse(res) { success(it) }
                },
                {
                    error(it)
                },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> T,
        success: suspend CoroutineScope.(T) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }


    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }
}