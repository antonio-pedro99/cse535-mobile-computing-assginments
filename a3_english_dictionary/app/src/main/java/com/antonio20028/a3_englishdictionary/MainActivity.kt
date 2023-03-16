package com.antonio20028.a3_englishdictionary

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Insets.add
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.antonio20028.a3_englishdictionary.model.data.WordModel
import com.antonio20028.a3_englishdictionary.model.interfaces.NetworkServiceState
import com.antonio20028.a3_englishdictionary.model.repository.WordsRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private var json = Json { ignoreUnknownKeys = true }

class MainActivity : AppCompatActivity(), NetworkServiceState {

    lateinit var btnSearch: Button
    lateinit var edtWord: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cm : ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetwork

        btnSearch = findViewById(R.id.btn_search)
        edtWord = findViewById(R.id.edt_word)

        btnSearch.setOnClickListener(View.OnClickListener {

            WordsRepository.fetchWords("a", this)
        })
    }

    override fun updateUI(data: String) {

        val ser = json.decodeFromString<List<WordModel>>(data)
        Toast.makeText(this, ser[0].word, Toast.LENGTH_LONG).show()
        /*val intent = Intent(this, ResultListActivity::class.java)
        intent.putExtra("ResponseDataJson", data)
        startActivity(intent)*/
    }

    override fun hasInternetConnection(context: Context): Boolean {
        val status = super.hasInternetConnection(this)

        Toast.makeText(this, "$status", Toast.LENGTH_LONG).show()
        return status
    }

}