package com.antonio20028.a3_englishdictionary.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.data.WordDefinitionModel
import com.antonio20028.a3_englishdictionary.model.data.WordMeaningModel
import com.antonio20028.a3_englishdictionary.view.adapters.WordMeaningDefinitionItem

class DefinitionListFragment : Fragment() {

    lateinit var txtDefinition:TextView
    lateinit var definitionList:RecyclerView
    lateinit var wordHeadingTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.definitions_list_fragnment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtDefinition = view.findViewById(R.id.txt_pos)
        definitionList = view.findViewById(R.id.definitions_recycler_view)
        wordHeadingTextView = view.findViewById(R.id.txt_def_word)
        val args = arguments
        var data: WordMeaningModel? = null
        if (args != null) {
            data = args.getParcelable("MeaningDefinitionData")


            txtDefinition.text = "(${data?.partOfSpeech})"

            val definitions = data?.definitions!!
            val definitionsAdapter: WordMeaningDefinitionItem = WordMeaningDefinitionItem(requireContext(), definitions)
            definitionList.adapter = definitionsAdapter
        }

    }
}