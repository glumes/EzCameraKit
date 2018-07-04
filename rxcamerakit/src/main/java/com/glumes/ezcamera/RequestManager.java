package com.glumes.ezcamera;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

/**
 * Created by glumes on 30/06/2018
 */

public class RequestManager<T> {


    /**
     * defaultRequestOptions
     *
     * @param cameraId
     */
    private T mDisplaySurface;


    public RequestManager(int cameraId) {
        EzCamera.getInstance().openCamera(cameraId);
    }

    public RequestManager(T surface) {
        mDisplaySurface = surface;
    }

    public RequestBuilder with(SurfaceView surface) {
        // 应该在 with 方法之前创建好 RequestBuilder ，然后把 SurfaceView 传递给 RequestBuilder， 然后返回 RequestBuilder
        // RequestBuilder 持有 RequestManager  的引用
        //
        EzCamera.getInstance().setPreviewSurface(surface);
        return new RequestBuilder(mDisplaySurface);
    }

    public RequestBuilder with(TextureView textureView) {
        EzCamera.getInstance().setPreviewTexture(textureView);
        return new RequestBuilder(mDisplaySurface);
    }

    public RequestBuilder with(SurfaceHolder holder) {
        EzCamera.getInstance().setPreviewSurface(holder);
        return new RequestBuilder(mDisplaySurface);
    }

    public RequestBuilder with(SurfaceTexture texture) {
        EzCamera.getInstance().setPreviewTexture(texture);
        return new RequestBuilder(mDisplaySurface);
    }

    public RequestBuilder apply(RequestOptions requestOptions) {
        RequestBuilder builder = new RequestBuilder(mDisplaySurface);
        builder.apply(requestOptions);
        return builder;
    }
}
