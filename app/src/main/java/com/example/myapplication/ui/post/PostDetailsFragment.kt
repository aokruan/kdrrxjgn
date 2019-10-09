package com.example.myapplication.ui.post

import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.showDialog
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.post.PostDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_post_details.*
import javax.inject.Inject

class PostDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_post_details
    override fun setListeners() {}

    @Inject
    lateinit var viewModel: PostDetailsViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val post = arguments?.getParcelable("post") as? Post
        post?.let(viewModel::setPost)
    }

    override fun setModelBindings() {
        viewModel.post
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                setPost(it)
            }
            .addTo(compositeDisposable)

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLoading ->
                Log.e("isLoading from View", isLoading.toString())
                if (isLoading){
                    isLoading()
                }
            }
            .addTo(compositeDisposable)
    }

    private fun setPost(post: Post) {
        tvTitle.text = post.title
        tvDescription.text = post.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun isLoading() {
        showDialog(this.requireContext(), null, getString(R.string.loading_please_wait))
        Log.e("showDialog() - Fragment", "")
    }
}
