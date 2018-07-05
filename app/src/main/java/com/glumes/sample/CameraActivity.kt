package com.glumes.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.OrientationEventListener
import android.view.OrientationListener
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageView
import com.glumes.ezcamera.ConfigOptions
import com.glumes.ezcamera.EzCamera
import com.glumes.ezcamera.EzCameraKit
import com.glumes.ezcamera.RequestOptions
import com.glumes.ezcamera.base.AspectRatio
import com.glumes.ezcamera.base.Size

class CameraActivity : AppCompatActivity() {

    private val mSurfaceView by lazy {
        findViewById<SurfaceView>(R.id.surfaceView)
    }
    private val mTakePic by lazy {
        findViewById<FloatingActionButton>(R.id.takePic)
    }
    private val mSwitchCamera by lazy {
        findViewById<ImageView>(R.id.switchCamera)
    }
    private val mAspectRatio by lazy {
        findViewById<ImageView>(R.id.aspectRatio)
    }
    private val mEnableFlash by lazy {
        findViewById<ImageView>(R.id.imageView)
    }


    val engine: EzCamera? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        mSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                startPreview(holder, width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

            }

        })

        var mOrientationListener = object : OrientationEventListener(this) {

            override fun onOrientationChanged(orientation: Int) {
                engine?.setDisplayOrientation(orientation)
            }
        }

        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable()
        } else {
            mOrientationListener.disable()
        }

    }

    fun startPreview(holder: SurfaceHolder?, width: Int, height: Int) {
//        EzCameraKit.open(0)
//                .with(holder!!)
//                .apply(ConfigOptions
//                        .getCameraParameter()
//                        .setAspectRatio(AspectRatio.of(16, 9))
//                        .with(width, height)
//                )
//                .build()
//                .startPreview()

//        EzCameraKit.with(mSurfaceView.holder)
//                .apply(RequestOptions.openFrontCamera())
//                .build()

        EzCameraKit.with(mSurfaceView.holder)
                .apply(RequestOptions
                        .openFrontCamera()
                        .size(Size(1080, 1920))
                        .setAspectRatio(AspectRatio.of(16, 9))
                        .displayOrientation(0))
                .open()
                .startPreview()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
