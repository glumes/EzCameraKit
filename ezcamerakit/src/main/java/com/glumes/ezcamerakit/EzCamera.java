package com.glumes.ezcamerakit;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import com.glumes.ezcamerakit.base.AspectRatio;
import com.glumes.ezcamerakit.base.Size;
import com.glumes.ezcamerakit.base.SizeMap;
import com.glumes.ezcamerakit.utils.CameraUtil;
import com.glumes.ezcamerakit.utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by glumes on 02/07/2018
 */
public class EzCamera {

    private Camera mCamera = null;

    private Camera.PreviewCallback mPreviewCallback;

    private int mCameraId;

    private Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();

    private static final String TAG = "EzCamera";

    private RequestOptions mRequestOptions;

    private Camera.Parameters mParameters;

    private final SizeMap mPreviewSizes = new SizeMap();
    private final SizeMap mPictureSizes = new SizeMap();

    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();

    private int mSurfaceType;
    private Object mDisplaySurface;

    static {
        FLASH_MODES.put(Constants.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        FLASH_MODES.put(Constants.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        FLASH_MODES.put(Constants.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        FLASH_MODES.put(Constants.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        FLASH_MODES.put(Constants.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }

    private static class CameraEngineHolder {
        private static EzCamera mInstance = new EzCamera();
    }

    private EzCamera() {

    }

    static EzCamera getInstance() {
        return CameraEngineHolder.mInstance;
    }

//    public boolean openCamera(int cameraId) {
//        mCameraId = cameraId;
//        initCameraInfo();
//        mCamera = Camera.open(cameraId);
//        return mCamera != null;
//    }

//    public boolean open(RequestOptions requestOptions) {
//        mRequestOptions = requestOptions;
//        mCamera = Camera.open(mRequestOptions.mCameraId);
//        return true;
//    }

    public <T> boolean open(RequestOptions requestOptions, T surfaceType) {

        mRequestOptions = requestOptions;
        mCamera = Camera.open(mRequestOptions.mCameraId);
        Camera.getCameraInfo(mCameraId, mCameraInfo);
        mParameters = mCamera.getParameters();
        initParameters();
        adjustCameraParameters();

        mDisplaySurface = surfaceType;

        try {
            if (surfaceType instanceof SurfaceHolder) {
                mSurfaceType = 0;
                mCamera.setPreviewDisplay((SurfaceHolder) mDisplaySurface);
            } else {
                mSurfaceType = 1;
                mCamera.setPreviewTexture((SurfaceTexture) mDisplaySurface);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    private void initParameters() {
        mPreviewSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPreviewSizes()) {
//            Log.d("EzCamera", "mPreviewSizes width is " + size.width + " height is " + size.height);
            mPreviewSizes.add(new Size(size.width, size.height));
        }
        mPictureSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPictureSizes()) {
//            Log.d("EzCamera", "mPictureSizes width is " + size.width + " height is " + size.height);
            mPictureSizes.add(new Size(size.width, size.height));
        }
        CameraUtil.adjustPreviewSizes(mPreviewSizes, mPictureSizes);
    }


    private void adjustCameraParameters() {
        SortedSet<Size> sizes = CameraUtil.calculateSizesOfAspect(mRequestOptions.mAspectRatio, mPreviewSizes, mPictureSizes);

        Size previewSize = CameraUtil.calculatePreviewSize(sizes, mRequestOptions.mSize.getWidth(), mRequestOptions.mSize.getHeight(), mRequestOptions.mDisplayOrientation);

        Size pictureSize = CameraUtil.calculatePictureSize(mPictureSizes, mRequestOptions.mAspectRatio);

        mParameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        mParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        mParameters.setRotation(CameraUtil.calculateCameraRotation(
                mCameraId, mCameraInfo.orientation, mRequestOptions.mDisplayOrientation
        ));
        setAutoFocus(mRequestOptions.mAutoFocus);
        setFlashMode(mRequestOptions.mFlashMode);
        setMuteMode(mRequestOptions.mMuteMode);
        mCamera.setDisplayOrientation(CameraUtil.calculateDisplayOrientation(mCameraInfo.facing, mCameraInfo.orientation,
                mRequestOptions.mDisplayOrientation));
    }

    public void setMuteMode(boolean muteMode) {
        mCamera.enableShutterSound(true);
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


    public void changeCamera(int cameraId) {

        if (!isCameraOpened()) {
            return;
        }
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;

        mRequestOptions.mCameraId = cameraId;

        if (mSurfaceType == 0) {
            open(mRequestOptions, (SurfaceHolder) mDisplaySurface);
        } else {
            open(mRequestOptions, (SurfaceTexture) mDisplaySurface);
        }

        startPreview();
    }

    public void setAspectRatio(AspectRatio aspectRatio) {
        mRequestOptions.mAspectRatio = aspectRatio;
        adjustCameraParameters();
    }

    public void setFlashMode(int flash) {
        mRequestOptions.mFlashMode = flash;
        List<String> modes = mParameters.getSupportedFlashModes();
        String mode = FLASH_MODES.get(flash);
        if (modes != null && modes.contains(mode)) {
            mParameters.setFlashMode(mode);
            mRequestOptions.mFlashMode = flash;
        }
        String currentMode = FLASH_MODES.get(mRequestOptions.mFlashMode);
        if (modes == null || !modes.contains(currentMode)) {
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mRequestOptions.mFlashMode = Constants.FLASH_OFF;
        }
    }

    public void setAutoFocus(boolean autoFocus) {
        mRequestOptions.mAutoFocus = autoFocus;
        final List<String> modes = mParameters.getSupportedFocusModes();
        if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            mParameters.setFocusMode(modes.get(0));
        }
    }

    public void setDisplayOrientation(int degrees) {
        mCamera.setDisplayOrientation(CameraUtil.calculateDisplayOrientation(mCameraInfo.facing, mCameraInfo.orientation,
                ConfigOptions.mDisplayOrientation));
    }

    public void startPreview() {
        mCamera.startPreview();
    }

    public void stopPreview() {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
//        mCamera.release();
//        mCamera = null;
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

    public void takePicture() {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d(TAG, "onPictureTaken");

                mCamera.startPreview();
            }
        });
    }

    public Set<AspectRatio> getSupportedAspectRatios() {
        return mPreviewSizes.ratios();
    }

    public AspectRatio getAspectRatio() {
        return mRequestOptions.mAspectRatio;
    }
}

