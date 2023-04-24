package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.iiitd.antonio20028.a5_trajroadsensingx.MainActivity
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding.OnBoardingPageAdapter

class OnBoardingFragment: Fragment() {


    private val fragments = listOf<Fragment>(
        OnBoardingFirstFragment(),
        OnBoardingSecondFragment()
    )

    private lateinit var viewPager: ViewPager2
    private lateinit var btnPrevious: Button
    private lateinit var btnNext:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.on_boarding_page_view)
        btnNext = view.findViewById(R.id.btn_next_page)
        btnPrevious = view.findViewById(R.id.btn_previous_page)

        val viewPagerAdapter = OnBoardingPageAdapter(requireActivity(), fragments)
        viewPager.adapter = viewPagerAdapter

        val userInfoViewModel = (activity as MainActivity).getViewModel()

        var currentPage = fragments.indexOf(fragments.first())

        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0){
                    btnPrevious.visibility = View.INVISIBLE
                    btnNext.text = activity?.resources?.getString(R.string.on_boarding_next)
                } else if(position == fragments.size - 1) {
                    btnPrevious.visibility = View.VISIBLE
                    btnNext.text = activity?.resources?.getString(R.string.on_boarding_start)
                }
                currentPage = position
                Log.d("P", currentPage.toString())
            }
        })

        var height:Double = .0
        var weight:Double = .0

        userInfoViewModel.getHeight().observe(viewLifecycleOwner){
            it?.let {
                height = it
            }
        }

        userInfoViewModel.getWeight().observe(viewLifecycleOwner){
            it?.let {
                weight = it
            }
        }

        btnNext.setOnClickListener {
            Log.d("P", currentPage.toString())
            if (currentPage == 0){
                currentPage += 1
                viewPager.currentItem = currentPage
            } else if (currentPage == 1) {
             try {
                userInfoViewModel.computeStrideLength(height, weight)
                it.findNavController().navigate(R.id.action_onboarding_fragment_to_homeFragment)
             }  catch (e:IllegalStateException) {
                 Log.d("EX", e.message.toString())
             }
            }
        }

        btnPrevious.setOnClickListener {
            if (currentPage >= 0){
                currentPage -= 1
                viewPager.currentItem = currentPage
            }
        }
    }


}