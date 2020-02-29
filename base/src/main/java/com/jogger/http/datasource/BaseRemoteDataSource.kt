//package com.jogger.lubanplatform.http.datasource
//
//import com.jogger.lubanplatform.http.basic.BaseResponse
//import com.jogger.lubanplatform.http.basic.BaseSubscriber
//import com.jogger.lubanplatform.http.basic.RetrofitManager
//import com.jogger.lubanplatform.http.basic.callback.RequestCallback
//import io.reactivex.Observable
//import io.reactivex.ObservableTransformer
//import io.reactivex.Observer
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import java.util.*
//import java.util.concurrent.TimeUnit
//
///**
// * Created by jogger on 2020/2/26
// * 描述：
// */
//abstract class BaseRemoteDataSource {
//    private var mCompositeDisposable: CompositeDisposable
//
//    initApp {
//        mCompositeDisposable = CompositeDisposable();
//    }
//
//    fun <T> execute(observable: Observable<BaseResponse<T>>, callback: RequestCallback<T>) {
//        execute(observable, BaseSubscriber(callback));
//    }
//
//    fun <T> execute(observable: Observable<BaseResponse<T>>, observer: Observer<T>) {
//        val subscribeWith: Disposable = observable.throttleFirst(500, TimeUnit.MILLISECONDS)
//            .subscribeOn(Schedulers.io())
//            .unsubscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .compose(applySchedulers())
//            .subscribeWith(observer) as Disposable
//        addDisposable(subscribeWith)
//
//    }
//
//    private fun addDisposable(disposable: Disposable) {
//        mCompositeDisposable.add(disposable)
//    }
//
//    private fun <T> applySchedulers(): ObservableTransformer<BaseResponse<T>, T> = RetrofitManager.applySchedulers()
//
//
//    fun <T> getService(clz: Class<T>): T = RetrofitManager.getService(clz)
//
//
//}