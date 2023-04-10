package com.iiitd.antonio20028.sensorgrama.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.iiitd.antonio20028.sensorgrama.R

class OnBoardingFragment: Fragment() {


    lateinit var btnStart : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStart = view.findViewById(R.id.btnStart)


        btnStart.setOnClickListener {
            it.findNavController().navigate(R.id.homeFragment)
        }
    }
}
