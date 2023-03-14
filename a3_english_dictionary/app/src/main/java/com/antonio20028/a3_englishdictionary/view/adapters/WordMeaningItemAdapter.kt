package com.antonio20028.a3_englishdictionary.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.a3_englishdictionary.R
import com.antonio20028.a3_englishdictionary.model.data.WordMeaningModel

class WordMeaningItemAdapter(
    private val context: Context,
    private val meanings: List<WordMeaningModel>
) :
    RecyclerView.Adapter<WordMeaningItemAdapter.WordMeaningItemViewHolder>() {

    class WordMeaningItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val partOfSpeech: TextView = view.findViewById(R.id.part_of_speech)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordMeaningItemViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_meaning_recyclerview_item, parent, false)
        return WordMeaningItemViewHolder(layout)
    }

    override fun getItemCount(): Int = meanings.size

    override fun onBindViewHolder(holder: WordMeaningItemViewHolder, position: Int) {
        val item  : WordMeaningModel = meanings[position]
        holder.partOfSpeech.text = item.partOfSpeech
    }
}