package com.glumes.ezcamerakit;


public class RequestBuilder<T> {

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

        boolean result = EzCamera.getInstance().open(mRequestOptions, mDisplaySurface);

        if (result) {
            return EzCamera.getInstance();
        } else {
            return null;
        }
    }
}
