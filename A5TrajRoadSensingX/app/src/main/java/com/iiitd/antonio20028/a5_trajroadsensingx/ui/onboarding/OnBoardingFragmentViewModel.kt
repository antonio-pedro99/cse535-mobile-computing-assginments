package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingFragmentViewModel:ViewModel() {
    private val userHeight = MutableLiveData<Double>()
    private val userGenre = MutableLiveData<String>()
    private val strideLength = MutableLiveData<Double>()

    fun computeStrideLength(height: Double, genre: String){
        strideLength.apply {
            when(genre.lowercase()){
                "male"-> value = height * .415
                "female"-> value = height * .413
            }
            Log.d("F", value.toString())
        }
    }
    fun setUserHeight(height: Double){
        userHeight.apply {
            value = height
        }
    }
    fun setUserGenre(genre: String){
        userGenre.apply {
            value = genre
        }
    }

    fun getStrideLength() = strideLength
    fun getHeight() = userHeight
    fun getWeight() = userGenre
}