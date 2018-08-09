# EzCameraKit

对 Android Camera API 的封装。

 打开相机并开始预览：

```kotlin
    // 相机类
    var engine: EzCamera? = null
    // 打开相机
    engine = EzCameraKit.with(mSurfaceView.holder)
            .apply(RequestOptions
                    .openFrontCamera()
                    .size(Size(width, height))
                    .setListener(CameraKitListenerAdapter())
            ).open()
   // 开始预览
   engine?.startPreview()
```

在 RequestOptions 中提供相应的配置信息：

-  相机的选择，前置还是后置
-  预览的 SurfaceView 的宽和高
-  相机预览和拍摄使用的宽高比
-  屏幕旋转的方向
-  自动对焦、静音、闪光灯配置
-  相机打开、关闭、预览、拍摄的回调接口
-  相机预览时的 YUV 像素格式


关闭相机操作

```kotlin
    engine?.stopPreview()
```


## 更新日志

[CHANGELOG](https://github.com/glumes/EzCameraKit/blob/master/CHANGELOG.md)


## TODO

1. 提供对 Android Camera 2.0 API 的支持。


## 参考

1. [CameraView](https://github.com/hujiaweibujidao/CameraView)