package com.example.myapplication.ui.post

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.createDialog
import com.example.myapplication.presentation.hideKeyboard
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.post.PostListViewModel
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_post_list.*
import javax.inject.Inject

class PostListFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: PostListViewModel
    override val layoutRes: Int = R.layout.fragment_post_list
    private val disposeBag = CompositeDisposable()
    private val postListAdapter = PostListAdapter(onPostClick = this::routeToDetails)
    private val loadingDialog: LottieAlertDialog by lazy {
        Log.e("LAZY", "LAZY")
        createDialog(
            this.requireContext(),
            null,
            getString(R.string.loading_please_wait)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        if (!viewModel.listOfPost.hasValue()) {
            viewModel.getFirstPage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setListeners() {
        srlGates.setOnRefreshListener {
            viewModel.getFirstPage()
        }

        rvPostList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    viewModel.getNextPage()
                }
            }
        })
    }

    override fun setModelBindings() {
        viewModel.listOfPost
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                postListAdapter.postList = it
            }
            .addTo(disposeBag)

        viewModel.isLoading
            .subscribe { isLoading ->
                if (isLoading) {
                    loadingDialog
                } else {
                    loadingDialog.dismiss()
                    srlGates.isRefreshing = isLoading
                }
            }
            .addTo(disposeBag)
    }

    private fun setupRecyclerView() {
        rvPostList.also { rv ->
            rv.setHasFixedSize(true)
            rv.isMotionEventSplittingEnabled = false
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = postListAdapter
        }
    }

    private fun routeToDetails(post: Post) {
        hideKeyboard()
        val bundle = bundleOf("post" to post)
        findNavController().navigate(R.id.actionToPostDetails, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBag.clear()
    }
}
