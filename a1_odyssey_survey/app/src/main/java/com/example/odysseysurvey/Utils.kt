package com.example.odysseysurvey

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast

class Utils {
    fun showLogToast(context: Context, activityName: String, stateFrom: String, stateTo: String) {
        val message = "$activityName change from $stateFrom to $stateTo"
        Log.d(TAG, message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

fun EditText.validateText(): Boolean = this.text.toString() != ""