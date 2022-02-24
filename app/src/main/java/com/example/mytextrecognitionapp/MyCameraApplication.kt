package com.example.mytextrecognitionapp

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig

class MyCameraApplication : Application(), CameraXConfig.Provider {

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}