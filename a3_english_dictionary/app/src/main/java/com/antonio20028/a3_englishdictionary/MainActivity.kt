package com.antonio20028.a3_englishdictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    lateinit var btnSearch: Button
    lateinit var edtWord: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        //transaction.replace(R.id.main_container_view, HomePageFragment()).commit()

        btnSearch = findViewById(R.id.btn_search)
        edtWord = findViewById(R.id.edt_word)

        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        btnSearch.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ResultListActivity::class.java)
            startActivity(intent)
        })
    }
}