package com.example.myapplication.ui.auth

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.presentation.createDialog
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.auth.SignUpViewModel
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_auth.etEmail
import kotlinx.android.synthetic.main.fragment_auth.etPassword
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: SignUpViewModel
    override val layoutRes: Int = R.layout.fragment_signup
    private val compositeDisposable = CompositeDisposable()
    private val loadingDialog: LottieAlertDialog by lazy {
        createDialog(
            this.requireContext(),
            null,
            getString(R.string.auth_checking_please_wait)
        )
    }

    override fun setModelBindings() {
        viewModel.isLoading
            .subscribe { isLoading ->
                if (isLoading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
            .addTo(compositeDisposable)

        viewModel.error
            .subscribe { error ->
                showMessage(error)
            }
            .addTo(compositeDisposable)
        viewModel.isSuccess
            .subscribe {
                routeToPostList()
            }
            .addTo(compositeDisposable)
    }

    override fun setListeners() {
        btnSignUp.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val passwordConfirmation = etPasswordConfirmation.text.toString()
            viewModel.signUp(name, email, password, passwordConfirmation)
        }
    }

    private fun routeToPostList() {
        findNavController().navigate(R.id.actionToAuthFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}