package com.antonio20028.alarmapp.utils

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Handler
import android.os.HandlerThread

class RingtonePlayer {

    companion object {
        private var ringtone: Ringtone? = null
        private lateinit var handlerThread: MyHandlerThread

        fun startRingtone(context: Context, durationMillis: Long = 10000) {
            if (ringtone == null) {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ringtone = RingtoneManager.getRingtone(context, notification)
            }

            if (!::handlerThread.isInitialized) {
                handlerThread = MyHandlerThread()
                handlerThread.start()
            }

            ringtone?.play()

            // Stop the ringtone after the specified duration on the background thread
            val runnable = Runnable {
                stopRingtone()

            }
            val toastRunnable = Runnable {
                LoggingUtils().showToast(context, "Alarm Ringing")
            }

            handlerThread.postRunnableDelayed(runnable, durationMillis)
            handlerThread.postRunnableDelayed(toastRunnable, durationMillis)
        }

        fun stopRingtone() {
            ringtone?.stop()
            handlerThread.removeCallbacksAndMessages(null)
        }

        fun isPlaying() : Boolean? {
            return ringtone?.isPlaying
        }
    }
}

class MyHandlerThread : HandlerThread("MyHandlerThread") {

    private lateinit var handler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler =  android.os.Handler(looper)
    }

    fun postRunnableDelayed(runnable: Runnable, delayMillis: Long) {
        handler.postDelayed(runnable, delayMillis)
    }

    fun removeCallbacksAndMessages(nothing: Nothing?) {
        handler.removeCallbacksAndMessages(nothing)
    }
}