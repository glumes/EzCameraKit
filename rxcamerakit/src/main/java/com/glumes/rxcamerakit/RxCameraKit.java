package com.glumes.rxcamerakit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by glumes on 19/06/2018
 */
public class RxCameraKit {


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
    public RequestManager open(int isFront) {


        return new RequestManager(isFront);
    }


    /**
     * RxCameraKit open 创建 RequestManager，open 指打开相机操作,传入前置还是后置
     * 然后 RequestManager 里面的 with 方法 传入 Surface 或者 SurfaceHolder 或者 GLSurfaceView 的内容,返回 RequestBuilder
     * RequestBuilder 里面设置各种参数，包括分辨率，尺寸，角度，闪光灯等等
     * 最后通过 build 方法构建最终相机
     *
     *
     * @return
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
