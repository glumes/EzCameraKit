package com.glumes.ezcamerakit;

import android.graphics.ImageFormat;
import android.hardware.Camera;

import com.glumes.ezcamerakit.base.AspectRatio;
import com.glumes.ezcamerakit.base.Size;
import com.glumes.ezcamerakit.utils.Constants;


public class RequestOptions {

    int mCameraId;

    AspectRatio mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;

    int mDisplayOrientation;

    boolean mMuteMode;

    Size mSize = new Size(1080, 1920);

    boolean mIsEnableFlash;

    public int mFlashMode;

    public boolean mAutoFocus;

    public int mPixelFormat = ImageFormat.NV21;

    CameraKitListener mListener;

    RequestOptions(int cameraId) {
        mCameraId = cameraId;
    }

    public static RequestOptions openBackCamera() {
        return new RequestOptions(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public static RequestOptions openFrontCamera() {
        return new RequestOptions(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    public RequestOptions size(Size size) {
        mSize = size;
        return this;
    }

    public RequestOptions flashMode(int mode) {
        mFlashMode = mode;
        return this;
    }

    public RequestOptions setAspectRatio(AspectRatio aspectRatio) {
        mAspectRatio = aspectRatio;
        return this;
    }

    public RequestOptions muteMode(boolean mute) {
        mMuteMode = mute;
        return this;
    }

    public RequestOptions autoFocus(boolean autoFocus) {
        mAutoFocus = autoFocus;
        return this;
    }

    public RequestOptions enableFlash(boolean flash) {
        mIsEnableFlash = flash;
        return this;
    }

    public RequestOptions displayOrientation(int degree) {
        mDisplayOrientation = degree;
        return this;
    }

    public RequestOptions setPixelFormat(int format) {
        mPixelFormat = format;
        return this;
    }

    public RequestOptions setListener(CameraKitListener listener) {
        mListener = listener;
        return this;
    }
}
