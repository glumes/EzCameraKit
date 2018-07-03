package com.glumes.sample

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.SurfaceView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.glumes.ezcamera.CameraEngine
import com.glumes.ezcamera.ConfigOptions
import com.glumes.ezcamera.RxCameraKit
import com.glumes.ezcamera.base.AspectRatio
import com.glumes.sample.util.PermissionsUtils

class MainActivity : AppCompatActivity() {

    lateinit var mFloatActionButton: FloatingActionButton
    lateinit var mSurfaceView: SurfaceView

    var mEngine: CameraEngine? = null

    private val mCameraKit = RxCameraKit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFloatActionButton = findViewById(R.id.floatingActionButton)
        mSurfaceView = findViewById(R.id.surfaceView2)


        val permissions = arrayOf(Manifest.permission.CAMERA)
        PermissionsUtils.checkAndRequestMorePermissions(this, permissions, 1, PermissionsUtils.PermissionRequestSuccessCallBack {
            mFloatActionButton.setOnClickListener {
                startCameraAndPreview()
            }
        })


        val imageView = findViewById<ImageView>(R.id.imageView)


        Glide.with(this)
                .load(R.drawable.ic_launcher_background)
                .apply(
                        RequestOptions.centerCropTransform().placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .priority(Priority.HIGH)
                ).into(imageView)

    }

    private fun startCameraAndPreview() {

        mEngine = mCameraKit.open(0)
                .with(mSurfaceView)
                .apply(ConfigOptions
                        .getCameraParameter()
                        .with(1080, 1920)
                        .setAspectRatio(AspectRatio.of(16, 9))
                )
                .build()
        mEngine?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        mEngine?.stopPreview()
        mEngine?.release()
    }
}
