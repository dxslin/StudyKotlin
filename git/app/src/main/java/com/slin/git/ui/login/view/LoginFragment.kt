package com.slin.git.ui.login.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.slin.core.ui.CoreFragment
import com.slin.git.BuildConfig
import com.slin.git.MainActivity
import com.slin.git.R
import com.slin.git.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.DI
import org.kodein.di.instance


class LoginFragment : CoreFragment() {

    override val di: DI = DI.lazy {
        extend(super.di)
        import(loginKodeinModule)
    }

    override val layoutResId: Int = R.layout.fragment_login

    private val loginViewModel: LoginViewModel by instance()

    private lateinit var binding: FragmentLoginBinding

    override fun initView(view: View) {
        super.initView(view)
        binding = FragmentLoginBinding.bind(view).apply {
            setLifecycleOwner { lifecycle }
            viewModel = loginViewModel
            loginFormState = loginViewModel.loginFormState.value
        }

//        loginViewModel.loginFormState.observe(this,
//            Observer { loginFormState ->
//                if (loginFormState == null) {
//                    return@Observer
//                }
//                btn_login.isEnabled = loginFormState.isDataValid
//                til_username.error =
//                    if (loginFormState.usernameError == null) {
//                        ""
//                    } else {
//                        getString(loginFormState.usernameError)
//                    }
//                til_password.error =
//                    if (loginFormState.passwordError == null) {
//                        ""
//                    } else {
//                        getString(loginFormState.passwordError)
//                    }
//            })

        loginViewModel.loginResult.observe(this,
            Observer { loginResult ->
                loginResult ?: return@Observer
                pb_loading.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    et_username.text.toString(),
                    et_password.text.toString()
                )
            }
        }
        et_username.addTextChangedListener(afterTextChangedListener)
        et_password.addTextChangedListener(afterTextChangedListener)
        et_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    et_username.text.toString(),
                    et_password.text.toString()
                )
            }
            false
        }

        btn_login.setOnClickListener {
            pb_loading.visibility = View.VISIBLE
            loginViewModel.login(
                et_username.text.toString(),
                et_password.text.toString()
            )
        }

        initData()
    }

    private fun initData() {
        if (BuildConfig.DEBUG) {
            et_username.setText("dxslin")
            et_password.setText("TTdxslin11")
        }

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName

        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}