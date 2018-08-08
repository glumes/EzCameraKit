package com.glumes.ezcamerakit.helper;

import android.hardware.Camera;

import com.glumes.ezcamerakit.RequestOptions;

import java.util.List;

/**
 * @Author glumes
 */
public class AutoFocusHelper {

    public static void setAutoFocus(boolean autoFocus, RequestOptions requestOptions, Camera.Parameters parameters) {
        requestOptions.mAutoFocus = autoFocus;
        final List<String> modes = parameters.getSupportedFocusModes();
        if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            parameters.setFocusMode(modes.get(0));
        }
    }
}
