package com.glumes.ezcamera;


import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by glumes on 30/06/2018
 */
public class RequestBuilder<T> {


    private ConfigOptions mConfigOptions;

    private RequestOptions mRequestOptions;

    private T mDisplaySurface;

    public RequestBuilder(T displaySurface) {
        mDisplaySurface = displaySurface;

    }

    public RequestBuilder apply(RequestOptions requestOptions) {

        mRequestOptions = requestOptions;
        return this;
    }


    public EzCamera open() {
        // EzCamera.getInstance.setPreview()
//        if () {
//            return EzCamera.getInstance();
//        }

        boolean result = EzCamera.getInstance().open(mRequestOptions, mDisplaySurface);


        return EzCamera.getInstance();
    }
}
