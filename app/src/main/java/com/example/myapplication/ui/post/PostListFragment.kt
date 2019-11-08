package com.example.myapplication.ui.post

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.EmptyList
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.createDialog
import com.example.myapplication.presentation.hideKeyboard
import com.example.myapplication.presentation.isVisible
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
    @Inject
    lateinit var settingsRepository: SettingsRepository
    override val layoutRes: Int = R.layout.fragment_post_list
    private val disposeBag = CompositeDisposable()
    private val postListAdapter = PostListAdapter(onPostClick = this::routeToDetails, onProvideForViewModel = this::provideForViewModel)
    private val postHotListAdapter = PostHotListAdapter()
    private val loadingDialog: LottieAlertDialog by lazy {
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
        Log.e("ORDER", settingsRepository.order.toString())
    }

    override fun setListeners() {
        srlPosts.setOnRefreshListener {
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
                postHotListAdapter.postList = it
            }
            .addTo(disposeBag)

        viewModel.isLoading
            .subscribe { isLoading ->
                if (isLoading) {
                    loadingDialog
                } else {
                    loadingDialog.dismiss()
                    srlPosts.isRefreshing = isLoading
                }
            }
            .addTo(disposeBag)

        viewModel.emptyStatus
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { status ->
                setEmptyListStatus(status)
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

        rvHotList.also { rv ->
            rv.setHasFixedSize(true)
            rv.isMotionEventSplittingEnabled = false
            rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = postHotListAdapter
        }
    }

    private fun routeToDetails(post: Post) {
        hideKeyboard()
        val bundle = bundleOf("post" to post)
        findNavController().navigate(R.id.actionToPostDetails, bundle)
    }

    /*Метод передаёт данные во ViewModel*/
    private fun provideForViewModel(data:ArrayList<Int>?){
        viewModel.provideForViewModel(data)
    }

    private fun setEmptyListStatus(status: EmptyList) {
        if (status != EmptyList.NONE) {
            tvEmptyList.text = getString(status.message)
            tvEmptyList.setCompoundDrawablesWithIntrinsicBounds(0, status.icon, 0, 0)
            tvEmptyList.isVisible = true
        } else {
            tvEmptyList.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        settingsRepository.order = "It's unit!"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBag.clear()
    }
}
