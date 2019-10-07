package com.example.myapplication.common

import io.reactivex.Completable
import io.reactivex.disposables.Disposable

fun Completable.defaultSubscribeBy(onComplete: () -> Unit = {}): Disposable =
    subscribe(onComplete, Throwable::printStackTrace)