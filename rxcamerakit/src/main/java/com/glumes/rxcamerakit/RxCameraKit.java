package com.glumes.rxcamerakit;

import com.glumes.rxcamerakit.camera.CameraInternal;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by glumes on 19/06/2018
 */
public class RxCameraKit {


    private CameraInternal mCamera;

    public Observable<CameraInternal> open() {

        return Observable.create(new ObservableOnSubscribe<CameraInternal>() {
            @Override
            public void subscribe(ObservableEmitter<CameraInternal> emitter) throws Exception {
                mCamera = new CameraInternal();
                if (mCamera.openCamera()) {
                    emitter.onNext(mCamera);
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable("open camera failed"));
                }
            }
        });
    }

    public void getDefaultCamera() {

    }

    public void setRatio() {

    }

    public void switchCamera() {

    }

    public void startPreview() {

    }

    public void takePhoto() {

    }

    public void enableFlash() {

    }


}
