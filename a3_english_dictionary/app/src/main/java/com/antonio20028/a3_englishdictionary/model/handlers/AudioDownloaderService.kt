package com.antonio20028.a3_englishdictionary.model.handlers

import android.content.Context
import android.media.MediaPlayer
import android.os.AsyncTask
import androidx.loader.content.AsyncTaskLoader
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class AudioDownloaderService(val context: Context, val callback: (Boolean) -> Unit) :
    AsyncTask<URL, Integer, Boolean>() {


    lateinit var fileName:String

    override fun doInBackground(vararg params: URL?): Boolean {
        var con: HttpURLConnection? = null
        return try {
            con = params[0]?.openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            con.connect()

            val input = con.inputStream

            fileName = "Audio ${System.currentTimeMillis()}"
            val output =
                FileOutputStream(File(context.filesDir, fileName))
            input.use { inp -> output.use { out -> inp.copyTo(out) } }

            true
        } catch (e: IOException) {
            false
        } finally {
            con?.disconnect()
        }
    }

    override fun onPostExecute(result: Boolean?) {
        if (result == true) {
            val mp = MediaPlayer()
            mp.setDataSource(File(context.filesDir, fileName).absolutePath)
            mp.prepare()
            mp.start()
        }

        if (result != null) {
            callback(result)
        }

    }
}