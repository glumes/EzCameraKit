package com.glumes.ezcamera;


/**
 * Created by glumes on 30/06/2018
 */
public class RequestBuilder {


    private ConfigOptions mConfigOptions;

    public RequestBuilder apply(ConfigOptions configOptions) {
        mConfigOptions = configOptions;
        return new RequestBuilder();
    }

    public EzCamera build() {
        return EzCamera.getInstance();
    }

}
