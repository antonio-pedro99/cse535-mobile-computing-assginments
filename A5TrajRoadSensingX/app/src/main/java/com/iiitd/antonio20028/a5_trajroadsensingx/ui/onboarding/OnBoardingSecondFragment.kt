package com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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



    private val genres = listOf<String>("Male", "Female")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editViewHeight = view.findViewById<EditText>(R.id.user_height)
        val editViewGenre = view.findViewById<AutoCompleteTextView>(R.id.user_genre)

        val onBoardingFragmentViewModel = (activity as MainActivity).getViewModel()

        validateEditText(editViewHeight, {
            onBoardingFragmentViewModel.setUserHeight(it.toDouble())
        }){
            Toast.makeText(requireContext(), "Enter a valid entry", Toast.LENGTH_LONG).show()
        }

        editViewGenre.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genres
            )
        )


        editViewGenre.setOnItemClickListener(){ parent, view, position, id ->
            onBoardingFragmentViewModel.setUserGenre(genres[position])
        }

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