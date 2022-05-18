package com.example.mytextrecognitionapp

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraTextScanManager(
    private val activity: AppCompatActivity,
    private val viewFinder: PreviewView,
    private val textRecognitionResultListener: OnTextRecognizeResultListener,
) {
    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun interface OnTextRecognizeResultListener {
        fun onTextRecognizeResult(textRecognitionResult: String)
    }

    fun start() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    fun stop() {
        cameraExecutor.shutdown()
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.permission_message_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()

                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                val preview = getPreview()
                val textRecognizer = getTextRecognizer()

                try {
                    // Unbind all use cases before rebinding
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        activity,
                        cameraSelector,
                        preview,
                        textRecognizer
                    )

                } catch (exc: IllegalStateException) {
                    LogUtils.d("Binding already bound or not in main thread $exc")
                    return@addListener

                } catch (exc: IllegalArgumentException) {
                    LogUtils.d("Binding does not work with camera selector $exc")
                    return@addListener
                }


            }, ContextCompat.getMainExecutor(activity)
        )

    }

    private fun getPreview(): Preview {
        return Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(viewFinder.display.rotation)
            .build()
            .also { preview ->
                preview.setSurfaceProvider(viewFinder.surfaceProvider)
            }
    }

    private fun getTextRecognizer(): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(viewFinder.display.rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also { analysis ->
                analysis.setAnalyzer(cameraExecutor, TextRecognizer(textRecognitionResultListener))
            }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    activity, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 1000
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}