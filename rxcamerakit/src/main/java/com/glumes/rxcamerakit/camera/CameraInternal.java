package com.glumes.rxcamerakit.camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.glumes.rxcamerakit.base.AspectRatio;
import com.glumes.rxcamerakit.base.SizeMap;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by glumes on 19/06/2018
 */
public class CameraInternal {

    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Camera mCamera;
    private Camera.Parameters mCameraParameters;

    private AspectRatio mAspectRatio;
    private SizeMap sizeMap;

    public boolean openCamera() {

        mCamera = Camera.open(mCameraId);
        return mCamera != null;
    }


    public boolean setDisplaySurface(SurfaceHolder holder) {

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean startPreview() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(1080, 1920);
        mCamera.startPreview();
        return true;
    }


}
