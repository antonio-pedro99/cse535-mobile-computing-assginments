package com.antonio20028.a3_englishdictionary.model.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.antonio20028.a3_englishdictionary.model.handlers.NetworkService
import com.antonio20028.a3_englishdictionary.model.interfaces.NetworkServiceState
import java.io.IOException
import java.net.NetworkInterface
import java.net.URL

object WordsRepository {

    val urlBase  = "https://api.dictionaryapi.dev/api/v2/entries/en"
    @Throws(IOException::class)
    fun fetchWords(searchWord:String, state : NetworkServiceState){
        val url: URL = URL("$urlBase/$searchWord")
        NetworkService(state).execute(url)
    }
}