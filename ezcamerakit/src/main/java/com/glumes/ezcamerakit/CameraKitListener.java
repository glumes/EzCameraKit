package com.glumes.ezcamerakit;


import android.hardware.Camera;

public interface CameraKitListener {

    void onPictureTaken(byte[] data);

    void onCameraOpened();

    void onCameraClosed();

    void onCameraPreview();

    void onPreviewCallback(byte[] data);
}
