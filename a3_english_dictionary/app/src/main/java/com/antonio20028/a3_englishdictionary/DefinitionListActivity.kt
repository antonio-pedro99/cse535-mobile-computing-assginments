package com.antonio20028.a3_englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.a3_englishdictionary.model.data.WordDefinitionModel
import com.antonio20028.a3_englishdictionary.model.data.WordMeaningModel
import com.antonio20028.a3_englishdictionary.view.DefinitionListFragment
import com.antonio20028.a3_englishdictionary.view.ResultListFragment

class DefinitionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definition_list)

        val data = intent.getParcelableExtra<WordMeaningModel>("MeaningDefinitions")

       // Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show()
        val fragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()
        val definitionListFragment = DefinitionListFragment()
        val args = Bundle()
        args.putParcelable("MeaningDefinitionData", data)
        definitionListFragment.arguments = args
        transaction.replace(R.id.definition_list_container_view, definitionListFragment).commit()
    }
}