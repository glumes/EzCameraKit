package com.glumes.ezcamera;

import android.hardware.Camera;
import android.support.v4.util.SparseArrayCompat;

import com.glumes.ezcamera.base.AspectRatio;
import com.glumes.ezcamera.base.Size;
import com.glumes.ezcamera.base.SizeMap;
import com.glumes.ezcamera.utils.CameraUtil;
import com.glumes.ezcamera.utils.Constants;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;


/**
 * 主要就是针对 Parameters 进行参数设置，设置结束之后，直接传递给相机设置 setParameters 就行了
 */

public class ConfigOptions {

    private static Camera.Parameters mParameters;

    private int mWidth = 1080;
    private int mHeight = 1920;

    private final SizeMap mPreviewSizes = new SizeMap();
    private final SizeMap mPictureSizes = new SizeMap();

    private AspectRatio mAspectRatio = AspectRatio.of(4, 3);
    private boolean isAutoFocus;
    private int mFlashMode;
    public static int mDisplayOrientation = Constants.LANDSCAPE_90;

    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();

    static {
        FLASH_MODES.put(Constants.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        FLASH_MODES.put(Constants.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        FLASH_MODES.put(Constants.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        FLASH_MODES.put(Constants.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        FLASH_MODES.put(Constants.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }


    public static ConfigOptions getCameraParameter() {
        return new ConfigOptions();
    }

    private static class ConfigOptionsHolder {
        private static ConfigOptions mInstance = new ConfigOptions();
    }


    static ConfigOptions with() {
        return ConfigOptions.ConfigOptionsHolder.mInstance;
    }


    private ConfigOptions() {
        mPreviewSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPreviewSizes()) {
            mPreviewSizes.add(new Size(size.width, size.height));
        }
        mPictureSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPictureSizes()) {
            mPictureSizes.add(new Size(size.width, size.height));
        }

        adjustPreviewSizes();
    }

    public Set<AspectRatio> getSupportedAspectRatios() {
        CameraUtil.adjustPreviewSizes(mPreviewSizes, mPictureSizes);
        return mPictureSizes.ratios();
    }


    private void adjustPreviewSizes() {
        CameraUtil.adjustPreviewSizes(mPreviewSizes, mPictureSizes);
    }


    public ConfigOptions setAspectRatio(AspectRatio ratio) {
        mAspectRatio = ratio;
        adjustCameraParameters();
        return this;
    }

    void adjustCameraParameters() {

        SortedSet<Size> sizes = CameraUtil.calculateSizesOfAspect(mAspectRatio, mPreviewSizes, mPictureSizes);

        Size previewSize = CameraUtil.calculatePreviewSize(sizes, mWidth, mHeight, mDisplayOrientation);

        Size pictureSize = CameraUtil.calculatePictureSize(mPictureSizes, mAspectRatio);

        mParameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        mParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());

        mParameters.setRotation(CameraUtil.calculateCameraRotation(
                EzCamera.getInstance().getCameraId(), EzCamera.getInstance().getCameraInfo().orientation, mDisplayOrientation
        ));

    }


    public void setDisplayOrientation(int displayOrientation) {
        mDisplayOrientation = displayOrientation;
    }


    ConfigOptions setFlashMode(int flash) {
        mFlashMode = flash;
        List<String> modes = mParameters.getSupportedFlashModes();
        String mode = FLASH_MODES.get(flash);
        if (modes != null && modes.contains(mode)) {
            mParameters.setFlashMode(mode);
            mFlashMode = flash;
        }
        String currentMode = FLASH_MODES.get(mFlashMode);
        if (modes == null || !modes.contains(currentMode)) {
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mFlashMode = Constants.FLASH_OFF;
        }
        return this;
    }

    ConfigOptions setAutoFocus(boolean autoFocus) {

        isAutoFocus = autoFocus;

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
        return this;
    }

    public ConfigOptions with(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    Camera.Parameters build() {
        return mParameters;
    }

    void reset() {
        setAspectRatio(mAspectRatio);
    }

    void apply() {

    }
}
