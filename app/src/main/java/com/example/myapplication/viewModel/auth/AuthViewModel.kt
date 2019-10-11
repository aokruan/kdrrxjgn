package com.example.myapplication.viewModel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.CustomException
import com.example.myapplication.common.UnexpectedException
import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.domain.interactor.AuthInteractor
import com.example.myapplication.domain.interactor.PostInteractor
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

    val authToken: BehaviorSubject<AuthToken> = BehaviorSubject.create()
    val isLoading: PublishSubject<Boolean> = PublishSubject.create()

    fun signIn(email:String, password:String) {
        interactor.signIn(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .subscribeBy(
                onSuccess = {
                    Log.e("RESULT", it.accessToken)
                    this.authToken.onNext(it)
                    isSuccess.onNext(true)
                    isError.onNext(false)
                },
                onError = { exception ->
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