package com.example.mytextrecognitionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytextrecognitionapp.databinding.ActivitySingleImageProcessBinding

class SingleImageProcessActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySingleImageProcessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySingleImageProcessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        supportActionBar?.title = TITLE_ACTIVITY
    }

    companion object {
        private const val TITLE_ACTIVITY = "이미지 한 장 인식"
    }
}