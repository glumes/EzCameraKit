package com.glumes.rxcamerakit;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by glumes on 30/06/2018
 */

public class RequestManager {

    private Camera mCamera;

    public RequestManager(Camera mCamera) {
        this.mCamera = mCamera;
    }

    public RequestBuilder with(SurfaceHolder holder) {

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RequestBuilder();
    }

    public RequestBuilder with(SurfaceView surfaceView) {
        return new RequestBuilder();
    }


}
