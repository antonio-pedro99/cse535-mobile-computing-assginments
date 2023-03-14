package com.antonio20028.a3_englishdictionary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.repository.GarbageWordsList
import com.antonio20028.a3_englishdictionary.view.adapters.WordMeaningItemAdapter

class ResultListFragment : Fragment(){


    lateinit var meaningRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meaningRecyclerView = view.findViewById(R.id.word_meaning_recycler_view)
        meaningRecyclerView.setHasFixedSize(true)

        val meanings = GarbageWordsList.getWords().first().meanings
        meaningRecyclerView.adapter =
            meanings?.let { WordMeaningItemAdapter(requireContext(), meanings = it) }
    }
}