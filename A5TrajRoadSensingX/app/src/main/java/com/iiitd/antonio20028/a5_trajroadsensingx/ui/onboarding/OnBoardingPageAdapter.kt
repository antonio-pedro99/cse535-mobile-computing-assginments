package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingPageAdapter(activity:FragmentActivity, private val pages:List<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = pages.size
    override fun createFragment(position: Int): Fragment = pages[position]
}