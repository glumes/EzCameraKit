package com.glumes.sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.OrientationEventListener
import android.view.SurfaceHolder
import android.widget.ImageView
import com.glumes.ezcamera.EzCamera
import com.glumes.ezcamera.EzCameraKit
import com.glumes.ezcamera.RequestOptions
import com.glumes.ezcamera.base.AspectRatio
import com.glumes.ezcamera.base.Size
import com.glumes.ezcamera.utils.Constants

class CameraActivity : AppCompatActivity() {

    private val mSurfaceView by lazy {
        findViewById<AutoFitSurfaceView>(R.id.surfaceView)
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
        findViewById<ImageView>(R.id.enableFlash)
    }


    var engine: EzCamera? = null
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


        mTakePic.setOnClickListener {
            takePic()
        }

        mAspectRatio.setOnClickListener {
            changeAspectRatio()
        }

        mEnableFlash.setOnClickListener {
            enableFlash()
        }

        mSwitchCamera.setOnClickListener {
            switchCamera()
        }
    }

    private fun switchCamera() {
        Log.d("EzCamera", "switch camera")
        engine?.changeCamera(0)
    }

    private fun enableFlash() {
        Log.d("EzCamera", "enableFlash")
        engine?.setFlashMode(Constants.FLASH_ON)
    }

    private fun changeAspectRatio() {
        Log.d("EzCamera", "changeAspectRatio")
        engine?.setAspectRatio(AspectRatio.of(4, 3))
        mSurfaceView.setAspectRatio(10, 10)
    }

    private fun takePic() {
        Log.d("EzCamera", "takePic")
        engine?.takePicture()
    }

    fun startPreview(holder: SurfaceHolder?, width: Int, height: Int) {

        engine = EzCameraKit.with(mSurfaceView.holder)
                .apply(RequestOptions
                        .openFrontCamera()
                        .size(Size(1080, 1920))
                        .setAspectRatio(AspectRatio.of(16, 9))
                        .displayOrientation(0))
                .open()

        engine!!.startPreview()


    }

}
