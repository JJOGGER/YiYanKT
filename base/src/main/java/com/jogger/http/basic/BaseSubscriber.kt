//package com.jogger.lubanplatform.http.basic
//
//import com.jogger.lubanplatform.http.basic.callback.RequestCallback
//import com.jogger.lubanplatform.http.basic.callback.RequestMultiplyCallback
//import com.jogger.lubanplatform.http.basic.exception.BaseException
//import io.reactivex.observers.DisposableObserver
//
///**
// * Created by jogger on 2020/2/26
// * 描述：
// */
//class BaseSubscriber<T>(callback: RequestCallback<T>) : DisposableObserver<T>() {
//    private var mRequestCallBack = callback
//    override fun onNext(t: T) {
//        mRequestCallBack.onSuccess(t)
//    }
//
//    override fun onError(e: Throwable) {
//        if (mRequestCallBack is RequestMultiplyCallback) {
//            if (e is BaseException) {
//                (mRequestCallBack as RequestMultiplyCallback<T>).onFail(e)
//            } else {
//                (mRequestCallBack as RequestMultiplyCallback<T>).onFail(BaseException(-1, e.message!!))
//            }
//
//        }
//    }
//
//    override fun onComplete() {
//    }
//}