package com.glumes.sample

import android.Manifest
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.SurfaceView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.glumes.rxcamerakit.RxCameraKit
import com.glumes.rxcamerakit.camera.CameraInternal
import com.glumes.sample.util.PermissionsUtils
import io.reactivex.*
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

class MainActivity : AppCompatActivity() {

    lateinit var mFloatActionButton: FloatingActionButton
    lateinit var mSurfaceView: SurfaceView

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
//                        RequestOptions.circleCropTransform().
                ).into(imageView)


        val kit = RxCameraKit()

        kit.open(0)
                .with(mSurfaceView.holder)
                .apply()
                .build()

//        Camera = CameraKit.with(surface)
//                .setFace()
//                .setRatio()
//                .enableFlash()
//                .setPreviewSize
//                .setPictureSize
//                .
//                .build()
//
//
//        Camera.startPreview()
//        Camera.takePicture()
//        Camera.

    }

    private fun startCameraAndPreview() {


        var camrea = mCameraKit.open(0)
                .with(mSurfaceView.holder)
                .setAspect()
                .enableFlash()
                .build()


//        mCameraKit
//                .open()
//                .flatMap(Function<CameraInternal, ObservableSource<CameraInternal>> {
//                    it.setDisplaySurface(mSurfaceView.holder)
//                    val cameraInternal = it
//                    return@Function Observable.create {
//                        it.onNext(cameraInternal)
//                        it.onComplete()
//                    }
//                })
//                .flatMap(Function<CameraInternal, ObservableSource<CameraInternal>> {
//                    it.startPreview()
//                    val cameraInternal = it
//                    return@Function Observable.create {
//                        it.onNext(cameraInternal)
//                        it.onComplete()
//                    }
//                })
//                .subscribe(Consumer {
//                    Log.d("RxCameraKit", "onNext")
//                })
    }
}
