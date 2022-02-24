package com.example.mytextrecognitionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.mytextrecognitionapp.databinding.ActivityStreamImageProcessBinding

class StreamImageProcessActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityStreamImageProcessBinding
    private lateinit var cameraTextScanManager: CameraTextScanManager

    private val listenerV1 = CameraTextScanManager.OnV1TextRecognizeListener { }
    private val listenerV2 = CameraTextScanManager.OnV2TextRecognizeListener { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityStreamImageProcessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.title = TITLE_ACTIVITY

        cameraTextScanManager = CameraTextScanManager(
            activity = this,
            viewFinder = viewBinding.viewFinderStream,
            listenerV1,
            listenerV2
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

    companion object {
        private const val TITLE_ACTIVITY = "이미지 스트림 인식"
    }
}