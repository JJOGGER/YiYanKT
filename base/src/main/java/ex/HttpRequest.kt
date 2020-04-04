package ex

import com.jogger.http.basic.exception.ExceptionHandle
import com.jogger.http.basic.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun launchUI(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch {
    block()
}

fun <T> launchFlow(block: suspend () -> T): Flow<T> {
    return flow {
        emit(block())
    }
}

private suspend fun <T> executeResponse(res: T, success: suspend CoroutineScope.(T) -> Unit) {
    coroutineScope {
        if (res != null)
            success(res)
        else throw ResponseThrowable(-1, "")
    }
}


fun <T> launchOnlyresult(
    block: suspend CoroutineScope.() -> T,
    success: (T) -> Unit,
    error: (ResponseThrowable) -> Unit = {},
    complete: () -> Unit = {}
) {
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
            val ex = ExceptionHandle.handleException(e)
//            if (ex.errorcode == HttpCode.CODE_ACCOUNT_INVALID) {
//                val message = Message()
//                message.code = ex.errorcode
//                message.msg = ex.errormsg
//                defUI.toastEvent.postValue("${ex.errorcode}:${ex.errormsg}")
//                defUI.msgEvent.postValue(message)
//            } else {
            error(ex)
//            }
        } finally {
            complete()
        }
    }
}