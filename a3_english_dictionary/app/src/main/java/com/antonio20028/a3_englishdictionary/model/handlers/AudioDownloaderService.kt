package com.antonio20028.a3_englishdictionary.model.handlers

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

class AudioDownloaderService(val callback:(Boolean)-> Unit): AsyncTask<URL, Integer, Boolean>() {


    override fun doInBackground(vararg params: URL?): Boolean {
        var con:HttpURLConnection? = null
        return try {
            con = params.first()?.openConnection() as HttpURLConnection

            con.connect()

            val input =  con.inputStream
            val output = FileOutputStream("test")
            input.copyTo(output)

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
            mp.setDataSource(File("test").absolutePath)
            mp.prepare()
            mp.start()
        }

        if (result != null) {
            callback(result)
        }

    }
}