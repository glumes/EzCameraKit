package com.glumes.ezcamerakit.utils;

import com.glumes.ezcamerakit.base.AspectRatio;

/**
 * Created by glumes on 02/07/2018
 */
public class Constants {

    public static AspectRatio DEFAULT_ASPECT_RATIO = AspectRatio.of(16, 9);//如果是16:9的话显示图片的时候可以填充整个屏幕
    public static AspectRatio SECOND_ASPECT_RATIO = AspectRatio.of(4, 3);//如果是4:3的话显示图片的时候会上下留黑很多空间


    public static final int LANDSCAPE_90 = 0;
    public static final int LANDSCAPE_270 = 270;

    public static final int FLASH_OFF = 0;
    public static final int FLASH_ON = 1;
    public static final int FLASH_TORCH = 2;
    public static final int FLASH_AUTO = 3;
    public static final int FLASH_RED_EYE = 4;
}
