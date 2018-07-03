package com.glumes.ezcamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import com.glumes.ezcamera.base.AspectRatio;

import java.io.IOException;

/**
 * Created by glumes on 02/07/2018
 */
public class CameraEngine {

    private Camera mCamera = null;

    private Camera.PreviewCallback mPreviewCallback;

    private int mCameraId;

    private Camera.CameraInfo mCameraInfo;

    private static final String TAG = "CameraEngine";

    private static class CameraEngineHolder {
        private static CameraEngine mInstance = new CameraEngine();
    }

    public static CameraEngine getInstance() {
        return CameraEngineHolder.mInstance;
    }

    public boolean openCamera(int cameraId) {
        mCameraId = cameraId;
        initCameraInfo();
        mCamera = Camera.open(cameraId);
        return mCamera != null;
    }

    private void initCameraInfo() {
        int count = Camera.getNumberOfCameras();
        for (int i = 0; i < count; i++) {
            Camera.getCameraInfo(i, mCameraInfo);
            if (mCameraInfo.facing == mCameraId) {
                mCameraId = i;
            }
        }
    }

    public boolean isCameraOpened() {
        return mCamera != null;
    }


    public int getCameraId() {
        return mCameraId;
    }

    public Camera.CameraInfo getCameraInfo() {
        return mCameraInfo;
    }

    public void setPreviewSurface(SurfaceView surfaceView) {
        setPreviewSurface(surfaceView.getHolder());
    }

    public void setPreviewSurface(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void setPreviewTexture(TextureView textureView) {
        setPreviewTexture(textureView.getSurfaceTexture());
    }

    public void setPreviewTexture(SurfaceTexture surfaceTexture) {
        try {
            mCamera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void startPreview() {
        mCamera.startPreview();
    }

    public void changeCameara(int cameraId) {

        if (!isCameraOpened()) {
            return;
        }

        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();

        openCamera(cameraId);
        ConfigOptions.getCameraParameter().reset();
        startPreview();

    }

    public void setAspectRatio(AspectRatio aspectRatio) {
        ConfigOptions.getCameraParameter().setAspectRatio(aspectRatio);
    }

    public void setFlashMode(int flashMode) {
        ConfigOptions.getCameraParameter().setFlashMode(flashMode);
    }

    public void setAutoFocus(boolean autoFocus) {
        ConfigOptions.getCameraParameter().setAutoFocus(autoFocus);
    }


    public void startPreview(Camera.PreviewCallback previewCallback) {
        mPreviewCallback = previewCallback;
        mCamera.setPreviewCallback(mPreviewCallback);
        mCamera.startPreview();
    }

    public void stopPreview() {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
    }

    public void takePicture(final OnPictureTaken pictureTaken) {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (pictureTaken != null) {
                    pictureTaken.onPictureTaken(data);
                }
            }
        });
    }

    Camera.Parameters getParameters() {
        return mCamera.getParameters();
    }

    public void release() {
        mCamera = null;
    }
}

