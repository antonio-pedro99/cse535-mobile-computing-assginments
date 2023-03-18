package com.antonio20028.a3_englishdictionary.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.data.WordDefinitionModel

class WordMeaningDefinitionItem(private val context:Context, private val definitions:List<WordDefinitionModel?>) : RecyclerView.Adapter<WordMeaningDefinitionItem.WordMeaningDefinitionItemViewHolder>() {
    class WordMeaningDefinitionItemViewHolder(view: View) : RecyclerView.ViewHolder (view){
        val definitionTxt : TextView = view.findViewById(R.id.definitionTextView)
        val exampleTxt : TextView = view.findViewById(R.id.exampleTextView)
        val synonymsTxt : TextView = view.findViewById(R.id.synonymsTextView)
        val antonymsTxt : TextView = view.findViewById(R.id.antonymsTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordMeaningDefinitionItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.definition_recycler_view_item, parent, false)
        return WordMeaningDefinitionItemViewHolder(layout)
    }

    override fun getItemCount(): Int = definitions.size

    override fun onBindViewHolder(holder: WordMeaningDefinitionItemViewHolder, position: Int) {
        val item = definitions[position]
        holder.definitionTxt.text = item?.definition
        if (!item?.example.isNullOrEmpty()){
            holder.exampleTxt.text = "Example: ${item?.example}"
        } else {
            holder.exampleTxt.text = "No Example found"
        }
        holder.antonymsTxt.text = item?.antonyms.toString()
        holder.synonymsTxt.text = item?.antonyms.toString()
    }
}