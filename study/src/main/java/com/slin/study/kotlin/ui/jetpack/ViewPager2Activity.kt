package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityViewPager2Binding
import com.slin.study.kotlin.ui.home.testDataList
import kotlin.math.roundToInt

/**
 * author: slin
 * date: 2021/8/18
 * description:
 *
 */
class ViewPager2Activity : BaseActivity() {

    private lateinit var binding: ActivityViewPager2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPager2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setShowBackButton(true)
        initView()
    }

    private fun initView() {
        binding.apply {


            viewpager2.adapter = object : FragmentStateAdapter(this@ViewPager2Activity) {
                override fun getItemCount(): Int {
                    return testDataList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return CardPagerFragment.newInstance(testDataList[position])
                }
            }

            //直接替换掉ViewPager2中RecyclerView的LayoutManager布局能够改变
            //但是与TabLayout联动存在问题
//            val prop = viewpager2.javaClass.getDeclaredField("mRecyclerView")
//            prop.isAccessible = true
//            val recyclerView = prop.get(viewpager2) as RecyclerView
//            recyclerView.layoutManager = CardLayoutManager()


            tlTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            TabLayoutMediator(tlTabLayout, viewpager2, true, true) { tab, position ->
                tab.text = testDataList[position].name
                tab.icon =
                    ContextCompat.getDrawable(this@ViewPager2Activity, testDataList[position].icon)
                        ?.apply {
                            setBounds(48.dp2Px(), 48.dp2Px(), 48.dp2Px(), 48.dp2Px())
                        }

            }.attach()

        }
    }

    private fun Int.dp2Px(): Int {
        return (this * resources.displayMetrics.density + 0.5f).roundToInt()
    }

}