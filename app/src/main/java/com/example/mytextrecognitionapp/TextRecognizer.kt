package com.example.mytextrecognitionapp

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextRecognizer(private val listener: CameraTextScanManager.OnTextRecognizeResultListener) :
    ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image

        mediaImage?.let {
            val imageToRecognize =
                InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            val result = recognizer.process(imageToRecognize)
                .addOnSuccessListener { visionText ->
                    listener.onTextRecognizeResult(visionText.text)
                }
                .addOnFailureListener {
                    LogUtils.d("Text Recognition Failed")
                    throw it
                }
                .addOnCompleteListener {
                    image.close()
                }

        }
    }
}