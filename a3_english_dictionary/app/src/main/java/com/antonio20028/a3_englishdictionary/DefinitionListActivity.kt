package com.antonio20028.a3_englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.a3_englishdictionary.view.DefinitionListFragment

class DefinitionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definition_list)

        val fragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.definition_list_container_view, DefinitionListFragment()).commit()
    }
}