package com.example.mytextrecognitionapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytextrecognitionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        viewBinding.buttonSingleImage.setOnClickListener {
            startActivity(Intent(this, SingleImageProcessActivity::class.java))
        }

        viewBinding.buttonStreamImage.setOnClickListener {
            startActivity(Intent(this, StreamImageProcessActivity::class.java))
        }
    }
}