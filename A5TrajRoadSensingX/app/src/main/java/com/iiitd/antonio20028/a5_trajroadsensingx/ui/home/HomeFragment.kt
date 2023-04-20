package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.changeStatusBarColor

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       activity?.changeStatusBarColor(statusBarColor = activity?.resources?.getColor(R.color.green), navigationBarColor = activity?.resources?.getColor(R.color.white
       ), true)
    }


}