package com.antonio20028.a3_englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonio20028.a3_englishdictionary.view.ResultListFragment

class ResultListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_list)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.result_container_view, ResultListFragment()).commit()
    }
}