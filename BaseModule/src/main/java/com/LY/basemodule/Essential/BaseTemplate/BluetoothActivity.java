package com.LY.basemodule.Essential.BaseTemplate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.basemodule.Utils.BluetoothGattCallBackUtils;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
//import com.vise.baseble.ViseBle;
//import com.vise.baseble.callback.IConnectCallback;
//import com.vise.baseble.core.DeviceMirror;
//import com.vise.baseble.exception.BleException;

import java.util.UUID;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;

/**
 * ====== 作者 ======
 * lcj yx
 * ====== 时间 ======
 * 2018-03-09.
 */

public abstract class BluetoothActivity extends BaseActivity {
    //获取远程蓝牙设备

    public   BluetoothDevice device;
    public  BluetoothGattCallBackUtils bluetoothGattCallback;
    public  BluetoothGatt gatt;
    private Ble<BleDevice> mBle;
    BleDevice bleDevice = new BleDevice();
    public void getBluetooth(String bluetoothAddress, BluetoothCallbackManager manager) {
        //检查蓝牙地址
        if (BluetoothAdapter.getDefaultAdapter().checkBluetoothAddress(bluetoothAddress)) {
            device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bluetoothAddress);
            if (device == null) {
                showToast("Can not find this Bluetooth");
                finish();
            } else {
                bluetoothGattCallback = new BluetoothGattCallBackUtils(this, manager);
                gatt = device.connectGatt(this, false, bluetoothGattCallback);
                if (gatt == null) {
                    Toast.makeText(this, "bluetoothGatt is null", Toast.LENGTH_SHORT).show();
                } else {
//                    gatt.connect();
//                    蓝牙相关配置修改
//                    ViseBle.config()
//                            .setScanTimeout(-1)//扫描超时时间，这里设置为永久扫描
//                            .setConnectTimeout(14 * 1000)//连接超时时间
//                            .setOperateTimeout(5 * 1000)//设置数据操作超时时间
//                            .setConnectRetryCount(3)//设置连接失败重试次数
//                            .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
//                            .setOperateRetryCount(3)//设置数据操作失败重试次数
//                            .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
//                            .setMaxConnectCount(3);//设置最大连接设备数量
////蓝牙信息初始化，全局唯一，必须在应用初始化时调用
//                    ViseBle.getInstance().init(this);
//                    ViseBle.getInstance().connectByMac(bluetoothAddress, new IConnectCallback() {
//                        @Override
//                        public void onConnectSuccess(DeviceMirror deviceMirror) {
//                            showToast("啊哈哈哈或或或");
//                            Log.e("processClick:123123","deviceMirror" );
//
//                        }
//
//                        @Override
//                        public void onConnectFailure(BleException exception) {
//                            Log.e("processClick:123123","BleException" );
//                        }
//
//                        @Override
//                        public void onDisconnect(boolean isActive) {
//                            Log.e("processClick:123123","isActive" );
//                        }
//                    });
                    //---------------------------------------------------------
                    bleDevice.setBleAddress(bluetoothAddress);
                    bleDevice.setBleName("WL96B385AB67C1");
                    mBle = Ble.getInstance();
                    initBle();
                    gatt.connect();
                    mBle.connect(bleDevice, connectCallback);
//                    Log.e("processClick:123123:", String.valueOf(a));
                }
            }
        } else {
            showToast("The bluetooth address was illegal");
            finish();
        }
    }
    private BleConnCallback<BleDevice> connectCallback = new BleConnCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(BleDevice device) {
            if (device.isConnected()) {
                Toast.makeText(BluetoothActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                mBle.disconnect(bleDevice);

                if(gatt!=null){
//                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
            }else{
                mBle = null;
                if(gatt!=null){
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
                Log.e("processClick:123123:","12312312312" );
            }
        }

        @Override
        public void onConnectException(BleDevice device, int errorCode) {
            super.onConnectException(device, errorCode);
            mBle = null;
            if(gatt!=null){
                gatt.disconnect();
                gatt.close();
                gatt = null;
            }
//            if(errorCode==2510){
//                showToast("连接异常，请重新连接");
//            }
//            Toast.makeText(BluetoothActivity.this, "连接异常，异常状态码:" + errorCode, Toast.LENGTH_SHORT).show();
        }
    };
    private void initBle() {
        Ble.Options options = new Ble.Options();
        options.logBleExceptions = true;//设置是否输出打印蓝牙日志
        options.throwBleException = true;//设置是否抛出蓝牙异常
        options.autoConnect = false;//设置是否自动连接
        options.scanPeriod = 12 * 1000;//设置扫描时长
        options.connectTimeout = 15 * 1000;//设置连接超时时长
        options.uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");//设置主服务的uuid
        options.uuid_write_cha = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");//设置可写特征的uuid
//        options.uuid_read_cha = UUID.fromString("d44bc439-abfd-45a2-b575-925416129601");//设置可读特征的uuid
        mBle.init(getApplicationContext(), options);
    }
    public void getBluetooth(BluetoothDevice device, BluetoothCallbackManager manager) {
        bluetoothGattCallback = new BluetoothGattCallBackUtils(this, manager);
        gatt = device.connectGatt(this, false, bluetoothGattCallback);
        if (gatt == null) {
            Toast.makeText(this, "bluetoothGatt is null", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("YXgetBluetooth", "gatt不为空");

            boolean a = gatt.connect();

            Log.e("YXgetBluetooth", String.valueOf(a));
        }
    }
}
