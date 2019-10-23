package com.example.myapplication.ui.auth

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.presentation.createDialog
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.viewModel.auth.AuthViewModel
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: AuthViewModel
    override val layoutRes: Int = R.layout.fragment_auth
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

                routeToMainScreen()
            }
            .addTo(compositeDisposable)
    }

    override fun setListeners() {

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            viewModel.signIn(email, password)
        }

        tvSignUp.setOnClickListener {
            routeToSignUp()
        }
    }

    private fun routeToMainScreen() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun routeToSignUp() {
        findNavController().navigate(R.id.actionToSignUp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}