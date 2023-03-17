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
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.data.WordDefinitionModel
import com.antonio20028.a3_englishdictionary.model.data.WordMeaningModel

class DefinitionListFragment : Fragment() {

    lateinit var txtDefinition:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.definitions_list_fragnment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtDefinition = view.findViewById(R.id.txt)

        val args = arguments
        var data: WordMeaningModel? = null
        if (args != null) {
            data = args.getParcelable("MeaningDefinitionData")

            txtDefinition.text = data?.partOfSpeech
        }

    }
}