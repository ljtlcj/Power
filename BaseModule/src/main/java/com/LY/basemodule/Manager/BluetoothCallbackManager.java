package com.LY.basemodule.Manager;

/**
 * ====== 作者 ======
 * Diko（柯东煜）
 * ====== 时间 ======
 * 2018-03-09.
 */

public interface BluetoothCallbackManager {
    void readCallback(String result);
    void writeCallback(String result);
    void connectCallback();
    void unConnectCallback();
}
