package com.glumes.rxcamerakit;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import com.glumes.rxcamerakit.base.AspectRatio;

import java.io.IOException;

/**
 * Created by glumes on 02/07/2018
 */
public class CameraEngine {

    private Camera mCamera = null;

    private Camera.PreviewCallback mPreviewCallback;


    private static final String TAG = "CameraEngine";

    private static class CameraEngineHolder {
        private static CameraEngine mInstance = new CameraEngine();
    }

    public static CameraEngine getInstance() {
        return CameraEngineHolder.mInstance;
    }

    public boolean openCamera(int cameraId) {
        mCamera = Camera.open(cameraId);
        return mCamera != null;
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

