package com.glumes.ezcamera;


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

//    public RequestBuilder apply(ConfigOptions configOptions) {
//        mConfigOptions = configOptions;
//        return this;
//    }

    public RequestBuilder apply(RequestOptions requestOptions) {
        mRequestOptions = requestOptions;
        return this;
    }


    public EzCamera build() {
        return EzCamera.getInstance();
    }

    public EzCamera open() {
        if (EzCamera.getInstance().open(mRequestOptions)) {
            return EzCamera.getInstance();
        }
        return null;
    }
}
