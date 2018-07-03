package com.glumes.ezcamera.utils;

import android.hardware.Camera;

import com.glumes.ezcamera.base.AspectRatio;
import com.glumes.ezcamera.base.Size;
import com.glumes.ezcamera.base.SizeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by glumes on 02/07/2018
 */
public class CameraUtil {


    public static void adjustPreviewSizes(SizeMap previewSizes, SizeMap pictureSizes) {
        List<AspectRatio> ratiosToDelete = new ArrayList<>();
        for (AspectRatio ratio : previewSizes.ratios()) {
            if (!pictureSizes.ratios().contains(ratio)) {
                ratiosToDelete.add(ratio);
            }
        }
        for (AspectRatio ratio : ratiosToDelete) {
            previewSizes.remove(ratio);
        }
    }

    public static Size calculatePreviewSize(SortedSet<Size> sizes, int surfaceWidth, int surfaceHeight, int displayOrientation) {
        int desiredWidth = surfaceWidth;
        int desiredHeight = surfaceHeight;
        if (isLandscape(displayOrientation)) {
            desiredWidth = surfaceHeight;
            desiredHeight = surfaceWidth;
        }
        Size result = null;
        for (Size size : sizes) { // Iterate from small to large
            if (desiredWidth <= size.getWidth() && desiredHeight <= size.getHeight()) {
                return size;
            }
            result = size;
        }
        return result;
    }


    public static Size calculatePictureSize(SizeMap mPictureSizes, AspectRatio mAspectRatio) {
        if (mAspectRatio.equals(Constants.DEFAULT_ASPECT_RATIO)) {
            SortedSet<Size> sizes = mPictureSizes.sizes(mAspectRatio);
            Size[] preferedSizes = new Size[]{new Size(1920, 1080), new Size(1280, 720)};//几个比较合适的输出大小
            for (Size size : preferedSizes) {
                if (sizes.contains(size)) {
                    return size;
                }
            }
            //前面几个合适的大小都没有的话，那么就使用中间那个大小
            return getMiddleSize(sizes);
        } else if (mAspectRatio.equals(Constants.SECOND_ASPECT_RATIO)) {
            SortedSet<Size> sizes = mPictureSizes.sizes(mAspectRatio);
            Size[] preferedSizes = new Size[]{new Size(1440, 1080), new Size(1280, 960), new Size(1024, 768), new Size(800, 600)};//几个比较合适的输出大小
            for (Size size : preferedSizes) {
                if (sizes.contains(size)) {
                    return size;
                }
            }
            //前面几个合适的大小都没有的话，那么就使用中间那个大小
            return getMiddleSize(sizes);
        } else {
            SortedSet<Size> sizes = mPictureSizes.sizes(mAspectRatio);
            return getMiddleSize(sizes);
        }
    }

    private static boolean isLandscape(int orientationDegrees) {
        return (orientationDegrees == Constants.LANDSCAPE_90 || orientationDegrees == Constants.LANDSCAPE_270);
    }

    //前面几个合适的大小都没有的话，那么就使用中间那个大小 (即使是中间这个大小也并不能保证它满足我们的需求，比如得到的图片还是很大，但是这种情况实在太少了)
    private static Size getMiddleSize(SortedSet<Size> sizes) {
        int length = sizes.size() / 2, i = 0;
        for (Size item : sizes) {
            if (i == length) {
                return item;
            }
            i++;
        }
        return sizes.last();
    }

    public static SortedSet<Size> calculateSizesOfAspect(AspectRatio aspectRatio, SizeMap previewSizes, SizeMap pictureSizes) {
        SortedSet<Size> sizes = previewSizes.sizes(aspectRatio);
        if (sizes == null) { // Not supported
            AspectRatio mAspectRatio = calculateAspectRatio(previewSizes, pictureSizes);
            sizes = previewSizes.sizes(mAspectRatio);
        }
        return sizes;
    }

    private static AspectRatio calculateAspectRatio(SizeMap previewSizes, SizeMap pictureSizes) {
        AspectRatio aspectRatio = null;
        if (previewSizes.ratios().contains(Constants.DEFAULT_ASPECT_RATIO)) {//首先看16:9是否支持
            aspectRatio = Constants.DEFAULT_ASPECT_RATIO;
        } else if (pictureSizes.ratios().contains(Constants.SECOND_ASPECT_RATIO)) {//再看4:3是否支持
            aspectRatio = Constants.SECOND_ASPECT_RATIO;
        } else {//两个都不支持的话就取它支持的第一个作为当前的宽高比
            aspectRatio = previewSizes.ratios().iterator().next();
        }
        return aspectRatio;
    }


    public static int calculateCameraRotation(int cameraFacing, int orientation, int screenOrientationDegrees) {
        if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            return (orientation + screenOrientationDegrees) % 360;
        } else {
            final int landscapeFlip = isLandscape(screenOrientationDegrees) ? 180 : 0;
            return (orientation + screenOrientationDegrees + landscapeFlip) % 360;
        }
    }
}
