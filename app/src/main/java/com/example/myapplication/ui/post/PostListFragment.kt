package com.example.myapplication.ui.post

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.hideKeyboard
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.post.PostListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_post_list.*
import javax.inject.Inject

class PostListFragment : BaseFragment() {
    override fun setListeners() {}

    override val layoutRes: Int = R.layout.fragment_post_list

    @Inject
    lateinit var viewModel: PostListViewModel

    private val disposeBag = CompositeDisposable()

    private val postListAdapter = PostListAdapter(onPostClick = this::routeToDetails)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAll()
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
    }

    override fun setModelBindings() {
        viewModel.listOfPost
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                postListAdapter.postList = it
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
