package com.example.myapplication.viewModel.post

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.domain.interactor.PostInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class PostListViewModel(
    private val interactor: PostInteractor
) : ViewModel() {
    private val isLoading: PublishSubject<Boolean> = PublishSubject.create()
    private val isError: PublishSubject<Boolean> = PublishSubject.create()
    private val isSuccess: PublishSubject<Boolean> = PublishSubject.create()
    private val disposable = CompositeDisposable()

    val listOfPost: BehaviorSubject<List<Post>> = BehaviorSubject.create()

    fun getAll() {
        interactor.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .doOnSuccess { }
            .subscribeBy(
                onSuccess = {
                    listOfPost.onNext(it)
                    isSuccess.onNext(true)
                    isError.onNext(false)
                },
                onError = { isError.onNext(true) }
            )
            .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
    }
}