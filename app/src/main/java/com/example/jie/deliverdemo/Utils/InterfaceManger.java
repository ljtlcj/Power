package com.example.jie.deliverdemo.Utils;

/**
 * =====作者=====
 * lcj
 * =====时间=====
 * 2018/4/14.
 */

public class InterfaceManger {

    public interface OnRequestListener {
        void onSuccess(Object success);

        void onError(String error);

        void onComplete();
    }
}
