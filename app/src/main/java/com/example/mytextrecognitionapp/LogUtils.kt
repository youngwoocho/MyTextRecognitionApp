package com.example.mytextrecognitionapp

import android.util.Log

class LogUtils {

    companion object {
        private const val DEFAULT_TAG = "TextRecognitionApp"

        fun d(tag: String, message: String?, throwable: Throwable? = null) {
           if (BuildConfig.DEBUG) {
               Log.d(tag, message, throwable)
           }
        }
        fun d(message: String?, throwable: Throwable? = null) {
            d(DEFAULT_TAG, message, throwable)
        }
    }
}