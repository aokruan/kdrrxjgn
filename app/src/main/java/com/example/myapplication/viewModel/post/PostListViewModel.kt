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
import javax.inject.Inject

class PostListViewModel @Inject constructor(
    private val interactor: PostInteractor
) : ViewModel() {
    private val isError: PublishSubject<Boolean> = PublishSubject.create()
    private val isSuccess: PublishSubject<Boolean> = PublishSubject.create()
    private val disposable = CompositeDisposable()

    val isLoading: PublishSubject<Boolean> = PublishSubject.create()
    val listOfPost: BehaviorSubject<List<Post>> = BehaviorSubject.create()

    private var nextPage = 1
    private var isEndOfList = false

    fun getAll() {
        interactor.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .doOnSuccess { }
            .subscribeBy(
                onSuccess = { it ->
                    it.map { Log.e("ERROR: ", it.toString()) }
                    //listOfPost.onNext(it)
                    isSuccess.onNext(true)
                    isError.onNext(false)
                },
                onError = { isError.onNext(true) }
            )
            .addTo(disposable)
    }

    fun getFirstPage() {
        reset()
        interactor.getAllPostsByPage(1)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .doOnSuccess { nextPage++ }
            .subscribeBy(
                onSuccess = {
                    listOfPost.onNext(it.data.distinct())
                    if (it.last_page_url.isEmpty()) {
                        isEndOfList = true
                    }
                },
                onError = { e -> Log.e("ERROR: ", e.toString()) }
            )
            .addTo(disposable)
    }

    fun getNextPage() {
        if (isEndOfList || listOfPost.value.isNullOrEmpty()) return

        interactor.getAllPostsByPage(nextPage)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .doOnSuccess { nextPage++ }
            .subscribeBy(
                onSuccess = { nextList ->
                    listOfPost.value?.toMutableList()?.apply {
                        addAll(nextList.data)
                    }?.distinct()?.let(listOfPost::onNext)
                },
                onError = { e -> Log.e("ERROR: ", e.toString()) }
            )
            .addTo(disposable)
    }

    private fun reset() {
        isEndOfList = false
        nextPage = 1
        listOfPost.onNext(emptyList())
        disposable.clear()
    }

    override fun onCleared() {
        disposable.clear()
    }
}