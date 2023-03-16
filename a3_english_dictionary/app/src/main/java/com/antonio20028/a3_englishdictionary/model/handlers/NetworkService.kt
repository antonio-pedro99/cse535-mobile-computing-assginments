package com.antonio20028.a3_englishdictionary.model.handlers

import android.os.AsyncTask
import android.util.Log
import com.antonio20028.a3_englishdictionary.model.interfaces.NetworkServiceState
import com.antonio20028.a3_englishdictionary.model.repository.WordsRepository
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkService(private val networkServiceState: NetworkServiceState):AsyncTask<URL, Void, String>() {

    override fun doInBackground(vararg params: URL?): String {
        var con:HttpURLConnection? = null
        var status: String? = "Failed"
        try {
            con = params.first()?.openConnection() as HttpURLConnection
            con.readTimeout = 5000
            con.requestMethod = "GET"
            con.readTimeout  = 1000

            con.connect()

            when(con.responseCode) {
                200, 201 -> {
                    val br = BufferedReader(InputStreamReader(con.inputStream))
                    val sb = StringBuilder()
                    var raw :String?
                    while (br.readLine().also { raw = it } != null){
                        sb.append(raw)
                    }
                    br.close()
                    con.disconnect()
                    Log.i("Network", sb.toString())
                    status = sb.toString()
                }
            }
        } catch (e:IOException) {
           status =  e.stackTrace.toString()
        } finally {
           // status = "Disconnected"
            con?.disconnect()
        }

        return status.toString()
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        networkServiceState.updateUI(result)
    }
}