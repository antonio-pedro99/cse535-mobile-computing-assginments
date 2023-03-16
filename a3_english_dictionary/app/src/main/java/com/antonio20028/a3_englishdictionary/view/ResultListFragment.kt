package com.antonio20028.a3_englishdictionary.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.data.WordModel
import com.antonio20028.a3_englishdictionary.model.repository.GarbageWordsList
import com.antonio20028.a3_englishdictionary.view.adapters.WordMeaningItemAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ResultListFragment : Fragment(){


    lateinit var meaningRecyclerView: RecyclerView

    private val json = Json {ignoreUnknownKeys = true}

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

        val args = arguments
        var data: String? = null
        if (args != null) {
            data = args.getString("JsonData")
        }

        val ser = data?.let { json.decodeFromString<List<WordModel>>(it) }
        val meanings = ser?.get(0)?.meanings
        Log.d("Meaning", ser?.get(0)?.meanings.toString())

        //Log.d("Meaning", data.toString())
       meaningRecyclerView.adapter =
            meanings?.let { WordMeaningItemAdapter(requireContext(), meanings = it) }
    }
}