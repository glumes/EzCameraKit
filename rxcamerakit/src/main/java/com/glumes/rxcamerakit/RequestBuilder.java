package com.glumes.rxcamerakit;

import android.hardware.Camera;

/**
 * Created by glumes on 30/06/2018
 */
public class RequestBuilder {

    public RequestBuilder apply() {
        return new RequestBuilder();
    }

    public RequestBuilder setAspect() {
        // camera set aspect
        return this;
    }

    public RequestBuilder enableFlash() {
        // camera enable flash
        return this;
    }

    public Camera build() {
        return Camera.open();
    }
}
