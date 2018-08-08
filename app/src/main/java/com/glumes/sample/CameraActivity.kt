package com.glumes.sample

import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Display
import android.view.OrientationEventListener
import android.view.SurfaceHolder
import android.widget.ImageView
import com.glumes.ezcamerakit.CameraKitListenerAdapter
import com.glumes.ezcamerakit.EzCamera
import com.glumes.ezcamerakit.EzCameraKit
import com.glumes.ezcamerakit.RequestOptions
import com.glumes.ezcamerakit.base.AspectRatio
import com.glumes.ezcamerakit.base.Size
import com.glumes.ezcamerakit.utils.Constants
import kotlinx.android.synthetic.main.activity_camera_layout.*

class CameraActivity : AppCompatActivity(), AspectRatioFragment.Listener {


    private val mSurfaceView by lazy {
        findViewById<AutoFitSurfaceView>(R.id.surfaceView)
    }
    private var mCameraId = 0

    private val FRAGMENT_PERMISSION = "permission"

    private val TAG = "EzCamera"

    var engine: EzCamera? = null

    lateinit var mOrientationEventListener: OrientationEventListener

    lateinit var mDisplay: Display


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        mDisplay = ViewCompat.getDisplay()

        mOrientationEventListener = object : OrientationEventListener(this) {

            override fun onOrientationChanged(orientation: Int) {
                Log.d(TAG, "orientation is $orientation")

                var rotation = mDisplay.rotation

                Log.d(TAG, "rotation is $rotation")

            }
        }
//        mOrientationEventListener.enable()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_layout)




        mSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                Log.d(TAG, "width is $width Height is $height")
                startPreview(holder, width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

            }

        })


//        var mOrientationListener = object : OrientationEventListener(this) {
//
//            override fun onOrientationChanged(orientation: Int) {
//                engine?.setDisplayOrientation(orientation)
//            }
//        }
//
//        if (mOrientationListener.canDetectOrientation()) {
//            mOrientationListener.enable()
//        } else {
//            mOrientationListener.disable()
//        }


        findViewById<FloatingActionButton>(R.id.takePic).setOnClickListener {
            takePic()
        }

        findViewById<ImageView>(R.id.aspectRatio).setOnClickListener {

            changeAspectRatio()
        }

        findViewById<ImageView>(R.id.enableFlash).setOnClickListener {
            enableFlash()
        }

        findViewById<ImageView>(R.id.switchCamera).setOnClickListener {
            switchCamera()
        }
    }

    private fun switchCamera() {
        Log.d(TAG, "switch camera")
        mCameraId = if (mCameraId == 0) 1 else 0
        engine?.changeCamera(mCameraId)
    }

    private fun enableFlash() {
        Log.d(TAG, "enableFlash")
        engine?.setFlashMode(Constants.FLASH_ON)
    }

    private fun changeAspectRatio() {
        Log.d(TAG, "changeAspectRatio")
        showAspectDialog()
    }

    private fun showAspectDialog() {
        val fragmentManager = supportFragmentManager
        val ratios = engine!!.supportedAspectRatios
        val currentAspect = engine!!.aspectRatio

        AspectRatioFragment.newInstance(ratios, currentAspect).show(fragmentManager, FRAGMENT_PERMISSION)
    }

    private fun takePic() {
        Log.d(TAG, "takePic")
        engine?.takePicture()
    }

    fun startPreview(holder: SurfaceHolder?, width: Int, height: Int) {
        mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT

        engine = EzCameraKit.with(mSurfaceView.holder)
                .apply(RequestOptions
                        .openFrontCamera()
                        .size(Size(width, height))
                        .setListener(CameraKitListenerAdapter())
                ).open()

        engine!!.startPreview()

    }

    override fun onPause() {
        super.onPause()
        engine?.stopPreview()
    }

    override fun onAspectRatioSelected(ratio: AspectRatio) {
        Log.d(TAG, "x is " + ratio.x + " y is " + ratio.y)
        engine?.aspectRatio = ratio
        mSurfaceView.setAspectRatio(ratio.y, ratio.x)

    }
}
