package com.glumes.ezcamerakit;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.glumes.ezcamerakit.base.AspectRatio;
import com.glumes.ezcamerakit.base.Size;
import com.glumes.ezcamerakit.base.SizeMap;
import com.glumes.ezcamerakit.helper.AutoFocusHelper;
import com.glumes.ezcamerakit.helper.FlashModeHelper;
import com.glumes.ezcamerakit.utils.CameraUtil;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by glumes on 02/07/2018
 */
public class EzCamera {

    private Camera mCamera = null;

    private Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();

    private static final String TAG = "EzCamera";

    private RequestOptions mRequestOptions;

    private Camera.Parameters mParameters;

    private final SizeMap mPreviewSizes = new SizeMap();
    private final SizeMap mPictureSizes = new SizeMap();


    private int mSurfaceType;
    private Object mDisplaySurface;

    private CameraKitListener mListener;

    private static class CameraEngineHolder {
        private static EzCamera mInstance = new EzCamera();
    }

    private EzCamera() {

    }

    static EzCamera getInstance() {
        return CameraEngineHolder.mInstance;
    }

    public <T> boolean open(RequestOptions requestOptions, T surfaceType) {

        mRequestOptions = requestOptions;
        mCamera = Camera.open(mRequestOptions.mCameraId);
        Camera.getCameraInfo(mRequestOptions.mCameraId, mCameraInfo);
        mParameters = mCamera.getParameters();
        initParameters();
        adjustCameraParameters();
        mParameters.setPreviewFormat(mRequestOptions.mPixelFormat);

        mListener = requestOptions.mListener;

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

        if (mListener != null) {
            mListener.onCameraOpened();
        }

        return true;
    }

    private void initParameters() {
        mPreviewSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPreviewSizes()) {
            mPreviewSizes.add(new Size(size.width, size.height));
        }
        mPictureSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPictureSizes()) {
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
                mRequestOptions.mCameraId, mCameraInfo.orientation, mRequestOptions.mDisplayOrientation
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
        FlashModeHelper.setFlashMode(flash, mRequestOptions, mParameters);
    }

    public void setAutoFocus(boolean autoFocus) {
        AutoFocusHelper.setAutoFocus(autoFocus, mRequestOptions, mParameters);
    }

    public void setDisplayOrientation(int degrees) {

        mParameters.setRotation(CameraUtil.calculateCameraRotation(
                mRequestOptions.mCameraId, mCameraInfo.orientation, mRequestOptions.mDisplayOrientation
        ));

        mCamera.setParameters(mParameters);

        mCamera.setDisplayOrientation(CameraUtil.calculateDisplayOrientation(mCameraInfo.facing, mCameraInfo.orientation,
                mRequestOptions.mDisplayOrientation));
    }

    public void startPreview() {
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (mListener != null) {
                    mListener.onPreviewCallback(data);
                }
            }
        });
        mCamera.startPreview();
        if (mListener != null) {
            mListener.onCameraPreview();
        }
    }

    public void stopPreview() {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        if (mListener != null) {
            mListener.onCameraClosed();
        }
    }

    public void takePicture() {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (mListener != null) {
                    mListener.onPictureTaken(data);
                }
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

    public Camera.Size getPreviewSize() {
        return mParameters.getPreviewSize();
    }

    public Camera.Size getPictureSize() {
        return mParameters.getPictureSize();
    }
}

