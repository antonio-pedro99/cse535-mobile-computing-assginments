package com.iiitd.antonio20028.a5_trajroadsensingx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding.OnBoardingFragmentViewModel

class MainActivity : AppCompatActivity() {

    private val userInfoViewModel by viewModels<OnBoardingFragmentViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment_view_container)
    }

    fun getViewModel():OnBoardingFragmentViewModel = userInfoViewModel
}