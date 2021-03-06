package com.gzz100.Z100_HuiYi.network.entity;

import com.gzz100.Z100_HuiYi.network.ApiService;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by XieQXiong on 2016/9/28.
 */
public class UpdatePost extends BaseEntity {
    private Subscriber mSubscriber;
    private String IMEI;
    private int meetingID;

    public UpdatePost(Subscriber subscriber, String imei, int meetingID) {
        this.mSubscriber = subscriber;
        IMEI = imei;
        this.meetingID = meetingID;
    }

    @Override
    public Observable getObservable(ApiService methods) {
        return methods.checkUpdate(IMEI,meetingID);
    }

    @Override
    public Subscriber getSubscriber() {
        return mSubscriber;
    }

}
