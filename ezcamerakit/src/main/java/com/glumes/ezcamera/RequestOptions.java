package com.glumes.ezcamera;

import android.hardware.Camera;

import com.glumes.ezcamera.base.AspectRatio;
import com.glumes.ezcamera.base.Size;

/**
 * @Author glumes
 */
public class RequestOptions {

    int mCameraId;

    AspectRatio mAspectRatio;

    int mDisplayOrientation;

    boolean mMuteMode;

    Size mSize;

    boolean mIsEnableFlash;

    int mFlashMode;

    OnPictureTaken mOnPictureTaken;

    boolean mAutoFocus;

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

    public RequestOptions callback(OnPictureTaken callback) {
        mOnPictureTaken = callback;
        return this;
    }
}
