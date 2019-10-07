package com.example.myapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.presentation.inflate
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(layoutRes).also {
            setHasOptionsMenu(true)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setModelBindings()
        setListeners()
    }

    abstract fun setModelBindings()

    abstract fun setListeners()

    open fun showMessage(resMsg: Int) {
        val view = view ?: return
        Snackbar.make(view, resMsg, Snackbar.LENGTH_SHORT).show()
    }

    open fun showMessage(msg: String) {
        val view = view ?: return
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}