package com.glumes.ezcamerakit.helper;

import android.hardware.Camera;
import android.support.v4.util.SparseArrayCompat;

import com.glumes.ezcamerakit.RequestOptions;
import com.glumes.ezcamerakit.utils.Constants;

import java.util.List;

public class FlashModeHelper {

    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();

    static {
        FLASH_MODES.put(Constants.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        FLASH_MODES.put(Constants.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        FLASH_MODES.put(Constants.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        FLASH_MODES.put(Constants.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        FLASH_MODES.put(Constants.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }

    public static void setFlashMode(int flash, RequestOptions requestOptions, Camera.Parameters parameters) {
        requestOptions.mFlashMode = flash;
        List<String> modes = parameters.getSupportedFlashModes();
        String mode = FLASH_MODES.get(flash);
        if (modes != null && modes.contains(mode)) {
            parameters.setFlashMode(mode);
            requestOptions.mFlashMode = flash;
        }
        String currentMode = FLASH_MODES.get(requestOptions.mFlashMode);
        if (modes == null || !modes.contains(currentMode)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            requestOptions.mFlashMode = Constants.FLASH_OFF;
        }
    }
}
