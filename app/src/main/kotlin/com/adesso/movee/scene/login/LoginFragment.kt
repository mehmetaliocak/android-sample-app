package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentLoginBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.util.Event

class LoginFragment : BaseTransparentStatusBarFragment<LoginViewModel, FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login

    override fun initialize() {
        super.initialize()

        binder.composeView.setContent {
            LoginScene(
                usernameLiveData = viewModel.username,
                passwordLiveData = viewModel.password,
                onUsernameChange = { viewModel.username.value = it },
                onPasswordChange = { viewModel.password.value = it },
                onForgotPasswordClick = { viewModel.onForgotPasswordClick() },
                onLoginClick = { viewModel.onLoginClick() },
                onRegisterClick = { viewModel.onRegisterClick() }
            )
        }

        viewModel.navigateUri.observeNonNull(viewLifecycleOwner, ::handleNavigateUriEvent)
    }

    private fun handleNavigateUriEvent(event: Event<Uri>) {
        event.getContentIfNotHandled()?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
