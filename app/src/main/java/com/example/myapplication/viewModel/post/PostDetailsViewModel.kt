package com.example.myapplication.viewModel.post

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.domain.interactor.PostInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class PostDetailsViewModel(
    private val interactor: PostInteractor
) : ViewModel() {
    private val isError: PublishSubject<Boolean> = PublishSubject.create()
    private val isSuccess: PublishSubject<Boolean> = PublishSubject.create()
    private val disposable = CompositeDisposable()

    val isLoading: PublishSubject<Boolean> = PublishSubject.create()
    val post: BehaviorSubject<Post> = BehaviorSubject.create()

    fun setPost(post: Post) {

        interactor.getById(post.id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .subscribeBy(
                onSuccess = {
                    this.post.onNext(it)
                    isSuccess.onNext(true)
                    isError.onNext(false)
                },
                onError = {
                    isError.onNext(true)
                    Log.e("ERROR", post.id.toString(), it)
                }
            )
            .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
    }
}