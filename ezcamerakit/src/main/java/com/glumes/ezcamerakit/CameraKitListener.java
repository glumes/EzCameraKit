package com.glumes.ezcamerakit;

public interface CameraKitListener {

    void onPictureTaken(byte[] data);

    void onCameraOpened();

    void onCameraClosed();

    void onCameraPreview();

    void onPreviewCallback(byte[] data);
}
