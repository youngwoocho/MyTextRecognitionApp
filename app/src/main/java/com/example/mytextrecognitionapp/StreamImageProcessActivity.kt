package com.example.mytextrecognitionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytextrecognitionapp.databinding.ActivityStreamImageProcessBinding

class StreamImageProcessActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityStreamImageProcessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityStreamImageProcessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.title = TITLE_ACTIVITY
    }

    companion object {
        private const val TITLE_ACTIVITY = "이미지 스트림 인식"
    }
}