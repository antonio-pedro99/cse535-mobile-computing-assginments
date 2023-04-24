package com.iiitd.antonio20028.a5_trajroadsensingx.utils

import android.widget.EditText

fun EditText.isValidEntry():Boolean{
    return this.text.isNotEmpty() || this.text.isNotBlank()
}