package com.glumes.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Button
import com.glumes.ezcamera.EzCameraKit
import com.glumes.ezcamera.RequestOptions
import com.glumes.ezcamera.base.AspectRatio
import com.glumes.ezcamera.base.Size

class Camera2Activity : AppCompatActivity() {

    lateinit var display: AutoFitSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2)

        display = findViewById(R.id.display)

        display.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                EzCameraKit.with(holder!!)
                        .apply(RequestOptions.openBackCamera()
                                .setAspectRatio(AspectRatio.of(16, 9))
                                .size(Size(1920, 1080))
                        )
                        .open()
                        .startPreview()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

            }
        })

        findViewById<Button>(R.id.button2).setOnClickListener {
            display.setAspectRatio(800, 800)
        }


    }
}
