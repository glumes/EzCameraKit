package com.glumes.ezcamerakit;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;


public class EzCameraKit {


    public static <T> RequestManager with(T surface) {
        if (surface instanceof SurfaceHolder) {
            return new RequestManager<>((SurfaceHolder) surface);
        } else {
            return new RequestManager<>((SurfaceTexture) surface);
        }
    }


}
