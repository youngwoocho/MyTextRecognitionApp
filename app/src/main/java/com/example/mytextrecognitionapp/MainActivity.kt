package com.example.mytextrecognitionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytextrecognitionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var cameraTextScanManager: CameraTextScanManager

    private val textRecognitionResultListener =
        CameraTextScanManager.OnTextRecognizeResultListener { textResult ->
            viewBinding.textRecognitionResult.text =
                getString(R.string.text_recognition_result, textResult)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        cameraTextScanManager = CameraTextScanManager(
            activity = this,
            viewFinder = viewBinding.viewFinderStream,
            textRecognitionResultListener,
        )

        viewBinding.root.post {
            cameraTextScanManager.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraTextScanManager.stop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraTextScanManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}