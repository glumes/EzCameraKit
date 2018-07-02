package com.glumes.rxcamerakit;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import java.io.IOException;

/**
 * Created by glumes on 30/06/2018
 */

public class RequestManager {


    public RequestManager(int cameraId) {
        CameraEngine.getInstance().openCamera(cameraId);
    }

    public RequestBuilder with(SurfaceView surface) {
        CameraEngine.getInstance().setPreviewSurface(surface);
        return new RequestBuilder();
    }

    public RequestBuilder with(TextureView textureView) {
        CameraEngine.getInstance().setPreviewTexture(textureView);
        return new RequestBuilder();
    }

    public RequestBuilder with(SurfaceHolder holder) {
        CameraEngine.getInstance().setPreviewSurface(holder);
        return new RequestBuilder();
    }

    public RequestBuilder with(SurfaceTexture texture) {
        CameraEngine.getInstance().setPreviewTexture(texture);
        return new RequestBuilder();
    }

}
