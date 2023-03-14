package com.antonio20028.a3_englishdictionary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.a3_englishdictionary.R

class HomePageFragment: Fragment() {

    lateinit var btnSearch: Button
    lateinit var edtWord:EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSearch = view.findViewById(R.id.btn_search)
        edtWord = view.findViewById(R.id.edt_word)

        val fragmentManager : FragmentManager = parentFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        btnSearch.setOnClickListener(View.OnClickListener {
            transaction.replace(R.id.main_container_view, ResultListFragment()).commit()
            transaction.addToBackStack(null)
        })
    }
}