package com.glumes.ezcamera;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

/**
 * Created by glumes on 30/06/2018
 */

public class RequestManager<T> {

    private T mDisplaySurface;

    private RequestOptions mDefaultRequestOptions;

    RequestManager(T surface) {
        mDisplaySurface = surface;
    }

    public RequestBuilder apply(RequestOptions requestOptions) {
        RequestBuilder builder;
        if (mDisplaySurface instanceof SurfaceHolder) {
            builder = new RequestBuilder<>((SurfaceHolder) mDisplaySurface);
            builder.apply(requestOptions);
        } else {
            builder = new RequestBuilder<>((SurfaceTexture) mDisplaySurface);
            builder.apply(requestOptions);
        }
        return builder;
    }
}
