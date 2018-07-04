package com.glumes.ezcamera;

import android.hardware.Camera;

import com.glumes.ezcamera.base.AspectRatio;

/**
 * @Author glumes
 */
public class RequestOptions<T> {

    private T mDisplaySurface;

    private int mCameraId;

    private AspectRatio mAspectRatio;

    private int mDisplayOrientation;

    private int mIsAutoFocus;

    private int mDisplayWidth;

    private int mDisplayHeight;

    private boolean mIsMuteMode;

    private boolean mIsEnableFlash;

    private RequestOptions(int cameraId) {
        mCameraId = cameraId;
    }

    public static RequestOptions openBackCamera() {
        return new RequestOptions(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public static RequestOptions openFrontCamera() {
        return new RequestOptions(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    public RequestOptions setWidth(int width) {
        mDisplayWidth = width;
        return this;
    }

}
