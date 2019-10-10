package com.example.myapplication.ui.post

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.createDialog
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.post.PostDetailsViewModel
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_post_details.tvDescription
import kotlinx.android.synthetic.main.fragment_post_details.tvTitle
import kotlinx.android.synthetic.main.post_item.*
import javax.inject.Inject

class PostDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_post_details
    override fun setListeners() {}

    @Inject
    lateinit var viewModel: PostDetailsViewModel
    private val loadingDialog: LottieAlertDialog by lazy {
        Log.e("LAZY", "LAZY")
        createDialog(
            this.requireContext(),
            null,
            getString(R.string.loading_please_wait)
        )
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            .subscribe { isLoading ->
                Log.e("isLoading from View", isLoading.toString())
                if (isLoading) {
                    loadingDialog
                } else {
                    loadingDialog.dismiss()
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
}
