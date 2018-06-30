package com.glumes.rxcamerakit;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by glumes on 30/06/2018
 */

public class RequestManager {


    public RequestBuilder with(SurfaceHolder holder) {
        return new RequestBuilder();
    }

    public RequestBuilder with(SurfaceView surfaceView) {
        return new RequestBuilder();
    }


}
