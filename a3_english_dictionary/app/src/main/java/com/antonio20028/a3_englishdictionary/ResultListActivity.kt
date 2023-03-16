package com.antonio20028.a3_englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.antonio20028.a3_englishdictionary.model.data.WordModel
import com.antonio20028.a3_englishdictionary.view.ResultListFragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ResultListActivity : AppCompatActivity() {

    private var json = Json { ignoreUnknownKeys = true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_list)


        if (intent.hasExtra("ResponseDataJson")) {
            val data = intent.getStringExtra("ResponseDataJson")
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val resultListFragment = ResultListFragment()
            val args = Bundle()
            args.putString("JsonData", data)
            resultListFragment.arguments = args
            fragmentTransaction.replace(R.id.result_container_view, resultListFragment).commit()
        }


    }
}