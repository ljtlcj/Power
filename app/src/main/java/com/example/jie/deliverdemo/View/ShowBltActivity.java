package com.example.jie.deliverdemo.View;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.example.jie.deliverdemo.R;

/**
 * Created by jie on 2018/5/31.
 */

public class ShowBltActivity extends BaseActivity {
    private TextView tv_temp;//临时控件
    private ListView lv_blt_list;//蓝牙列表
    private BluetoothAdapter bluetooth;//蓝牙设配器
    @Override
    public int getLayoutId() {
        return R.layout.activity_blt_list;
    }

    @Override
    public void initViews() {
        tv_temp = findView(R.id.tv_temp);
        lv_blt_list = findView(R.id.lv_blt_list);
    }

    @Override
    public void initListener() {
        tv_temp.setOnClickListener(this);
    }

    @Override
    public void initData() {
        requestPermissions(Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
        openBluetooth();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.tv_temp:
                startActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 查看蓝牙是否被打开
     * 若没有被打开就请求打开蓝牙权限
     * @return
     */
    private boolean openBluetooth()
    {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        if(bluetooth==null) {
            Toast.makeText(getApplicationContext(), "No bluetooth Adapter!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!bluetooth.isEnabled())
        {
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, 1);
            return true;
        }
        return bluetooth.isEnabled();
    }
}
