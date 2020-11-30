package com.slin.git.ui.login.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.slin.core.ui.CoreFragment
import com.slin.git.BuildConfig
import com.slin.git.R
import com.slin.git.databinding.FragmentLoginBinding

/**
 * 登录界面
 */
class LoginFragment : CoreFragment() {


    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = loginViewModel
            loginFormState = loginViewModel.loginFormState.value

            loginViewModel.loginResult.observe(this@LoginFragment.viewLifecycleOwner,
                Observer { loginResult ->
                    loginResult ?: return@Observer
                    pbLoading.visibility = View.GONE
                    loginResult.error?.let {
                        showLoginFailed(it)
                    }
                    loginResult.success?.let {
                        updateUiWithUser(it)
                    }
                })

            val afterTextChangedListener = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // ignore
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // ignore
                }

                override fun afterTextChanged(s: Editable) {
                    loginViewModel.loginDataChanged(
                        etUsername.text.toString(),
                        etPassword.text.toString()
                    )
                }
            }

            etUsername.addTextChangedListener(afterTextChangedListener)
            etPassword.addTextChangedListener(afterTextChangedListener)
            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(
                        etUsername.text.toString(),
                        etPassword.text.toString()
                    )
                }
                false
            }

            btnLogin.setOnClickListener {
                pbLoading.visibility = View.VISIBLE
                loginViewModel.login(
                    etUsername.text.toString(),
                    etPassword.text.toString()
                )
            }

            initData()
        }

        //拦截掉返回按钮事件
        requireActivity().onBackPressedDispatcher.addCallback(this) {

        }
    }


    private fun initData() {
        if (BuildConfig.DEBUG) {
            binding.etUsername.setText(BuildConfig.DEFAULT_ACCOUNT)
            binding.etPassword.setText(BuildConfig.DEFAULT_PASSWORD)
        }
        if (loginViewModel.isAutoLogin()) {
            loginViewModel.login(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        findNavController().popBackStack()

//        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}