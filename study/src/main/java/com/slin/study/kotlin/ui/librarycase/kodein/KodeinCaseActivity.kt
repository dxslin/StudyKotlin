package com.slin.study.kotlin.ui.librarycase.kodein

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kodein_case.*
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext


/**
 * author: slin
 * date: 2020-09-02
 * description: kodein 简单使用例子
 * github: https://github.com/Kodein-Framework/Kodein-DI
 *
 *
 *
 */
class KodeinCaseActivity : BaseActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodeinContext: KodeinContext<*>
        get() = kcontext(this)

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
        import(moduleKodeinCase)
        bind<Activity>() with instance(this@KodeinCaseActivity)
    }

    private val userManager by instance<UserManager>()
    private val user by instance<User>()
    private val kodeinCaseFragment by instance<KodeinCaseFragment>()
    private val inflater by instance<LayoutInflater>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.activity_kodein_case, null)
        setContentView(view)
//        setContentView(R.layout.activity_kodein_case)

        tv_user.text = "${userManager.getName()}: ${user.name}"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, kodeinCaseFragment)
            .commit()

    }
}