package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingFragmentViewModel:ViewModel() {
    private val userHeight = MutableLiveData<Double>()
    private val userWeight = MutableLiveData<Double>()
    private val strideLength = MutableLiveData<Double>()

    fun computeStrideLength(height: Double, weight: Double){
        strideLength.apply {
            value = .4 * height + .0003 * weight + .15
        }
    }
    fun setUserHeight(height: Double){
        userHeight.apply {
            value = height
        }
    }
    fun setUserWeight(weight: Double){
        userWeight.apply {
            value = weight
        }
    }

    fun getStrideLength() = strideLength
    fun getHeight() = userHeight
    fun getWeight() = userWeight
}