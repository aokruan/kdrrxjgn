package com.example.myapplication.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

var View.isVisible: Boolean
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
    get() = this.visibility == View.VISIBLE

fun Fragment.hideKeyboard() {
    val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    manager?.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.showKeyboard() {
    val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    manager?.toggleSoftInput(
        InputMethodManager.SHOW_IMPLICIT,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun Fragment.createDialog(
    context: Context,
    title: String?,
    description: String
): LottieAlertDialog {
    val alertDialog: LottieAlertDialog =
        LottieAlertDialog.Builder(context, DialogTypes.TYPE_LOADING)
            .let { it.setTitle(title) }
            .setDescription(description)
            .build()
    alertDialog.setCancelable(false)
    alertDialog.show()

    return alertDialog
}