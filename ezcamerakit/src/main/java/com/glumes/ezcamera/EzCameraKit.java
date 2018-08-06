package com.glumes.ezcamera;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by glumes on 19/06/2018
 */
public class EzCameraKit {


    /**
     * 1 surfaceBuilder
     * 2 camera parameter builder
     * 3 orientation builder
     * 4
     *
     * @param holder
     * @param width
     * @param height
     * @return
     */

    /**
     * 通过 into 来提供最终的 surface 和 width 和 height
     *
     * @return
     */
//    public static RequestManager open(int isFront) {
//
//        return new RequestManager(isFront);
//    }
//
//
//    public static RequestManager with(SurfaceView surface) {
//
//        return new RequestManager(0);
//    }
//
//    public static RequestManager with(TextureView textureView) {
//
//        return new RequestManager(0);
//    }

    public static <T> RequestManager with(T surface) {
        if (surface instanceof SurfaceHolder) {
            return new RequestManager<>((SurfaceHolder) surface);
        } else {
            return new RequestManager<>((SurfaceTexture) surface);
        }
    }


//    public static <T> RequestBuilder with(T surface) {
//
//        return new RequestBuilder<>((SurfaceHolder) surface);
//    }
//

    /**
     * 泛型方法，检查设置的类型，类型错误就抛出异常。
     *
     * @return
     */
    private boolean checkType() {
        return false;
    }
    /**
     * EzCameraKit open 创建 RequestManager，open 指打开相机操作,传入前置还是后置
     * 然后 RequestManager 里面的 with 方法 传入 Surface 或者 SurfaceHolder 或者 GLSurfaceView 的内容,返回 RequestBuilder
     * RequestBuilder 里面设置各种参数，包括分辨率，尺寸，角度，闪光灯等等
     * 最后通过 build 方法构建最终相机
     *
     *
     * @return
     */


    /**
     * RequestBuilder 里面包含有 RequestManager ,RequestManager 里面设置有默认的请求选项 RequestOptions
     *
     */

    /**
     * 相机对象 又包含一些设置方法
     * 比如：调整上面的设置的参数内容，前后摄像头，分辨率等等
     * 还有 开始预览，停止预览，拍摄等功能。
     *
     * @return
     */


    /**
     * RequestManager 设置的一些参数如果传递给最终构建的 camera
     * 然后 camera 还要可以改变调整那些 camera
     *
     * @return
     */


}
