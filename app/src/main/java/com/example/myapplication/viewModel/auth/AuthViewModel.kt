package com.example.myapplication.viewModel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.CustomException
import com.example.myapplication.common.UnexpectedException
import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.domain.interactor.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val interactor: AuthInteractor
) : ViewModel() {
    private val isError: PublishSubject<Boolean> = PublishSubject.create()
    val isSuccess: PublishSubject<Boolean> = PublishSubject.create()
    val error: PublishSubject<String> = PublishSubject.create()
    private val disposable = CompositeDisposable()

    val isLoading: PublishSubject<Boolean> = PublishSubject.create()

    fun signIn(email:String, password:String) {
        interactor.signIn(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .subscribeBy(
                onSuccess = {
                    isSuccess.onNext(true)
                    isError.onNext(false)
                },
                onError = { exception ->
                    Log.e("Request Error", exception.toString())
                    isError.onNext(true)
                    val customException = exception as? CustomException
                        ?: UnexpectedException
                    customException.message?.let(error::onNext)
                }
            )
            .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
    }
}