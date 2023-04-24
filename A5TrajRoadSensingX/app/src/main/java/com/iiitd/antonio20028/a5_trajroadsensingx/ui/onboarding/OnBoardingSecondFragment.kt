package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iiitd.antonio20028.a5_trajroadsensingx.MainActivity
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.isValidEntry

class OnBoardingSecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editViewHeight = view.findViewById<EditText>(R.id.user_height)
        val editViewWeight = view.findViewById<EditText>(R.id.user_weight)

        val onBoardingFragmentViewModel = (activity as MainActivity).getViewModel()

        validateEditText(editViewHeight, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            onBoardingFragmentViewModel.setUserHeight(it.toDouble())
        }){
            Toast.makeText(requireContext(), "Enter a valid entry", Toast.LENGTH_LONG).show()
        }

        validateEditText(editViewWeight, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            onBoardingFragmentViewModel.setUserWeight(it.toDouble())
        }){
            Toast.makeText(requireContext(), "Enter a valid entry", Toast.LENGTH_LONG).show()
        }
        //onBoardingFragmentViewModel.setUserHeight(editViewHeight.text.toString().toDouble())

    }

    private fun validateEditText(view:EditText, onSuccess:(value:String)->Unit, onFail:()->Unit){
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                if (view.isValidEntry()){
                    onSuccess(view.text.toString())
                } else {
                    onFail()
                }
                return@setOnKeyListener true
            }
            false
        }
    }
}