package com.glumes.sample

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.glumes.ezcamerakit.EzCamera
import com.glumes.ezcamerakit.EzCameraKit
import com.glumes.sample.util.PermissionsUtils

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf(Manifest.permission.CAMERA)

        PermissionsUtils.checkAndRequestMorePermissions(this, permissions, 1, PermissionsUtils.PermissionRequestSuccessCallBack {
            findViewById<Button>(R.id.button).setOnClickListener {
                startActivity(Intent(this, CameraActivity::class.java))
            }

            findViewById<Button>(R.id.button3).setOnClickListener {
                startActivity(Intent(this, Camera2Activity::class.java))
            }

        })
    }

}
