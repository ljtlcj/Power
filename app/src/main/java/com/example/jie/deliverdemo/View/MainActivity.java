package com.example.jie.deliverdemo.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.example.jie.deliverdemo.Controller.CustomerController;
import com.example.jie.deliverdemo.R;
import com.example.jie.deliverdemo.Utils.InterfaceManger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "VideoActivity";
    private ImageView finish;//点击选择txt
    private TextView tv_edit2;//编辑栏
    private TextView choice;//点击选择txt
    private EditText wirte;//编辑栏
    private Button send;//发送控件
    Intent intent;
    private File file;//选择的文件
    private String path;//选择文件的路径

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        choice = (TextView) findViewById(R.id.choice);
        wirte = (EditText) findViewById(R.id.path);
        send = (Button) findViewById(R.id.send);
        finish = findView(R.id.finish);
        tv_edit2 = findView(R.id.tv_edit2);
        wirte.setEnabled(false);
    }

    @Override
    public void initListener() {
        send.setOnClickListener(this);
        choice.setOnClickListener(this);
        finish.setOnClickListener(this);
        tv_edit2.setOnClickListener(this);
    }

    @Override
    public void initData() {
        initrequest();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                send_click();
                break;
            case R.id.choice:
                choice_click();
                break;
            case R.id.finish:
                finish();
                break;
            case R.id.tv_edit2:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 发送文件
     **/
    private void send_click() {
        String temp = wirte.getText().toString().trim();
        if (temp.equals("")) {
            Toast.makeText(MainActivity.this, "未选择文件", Toast.LENGTH_SHORT).show();
        } else {
            //提交文件
            fanwen(file, path);
            choice.setText("打开升级文件");
            wirte.setText("");
        }
    }

    /**
     * 选择文件
     **/
    private void choice_click() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 请求权限
     **/
    private void initrequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * 根据返回选择的文件，来进行上传操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            path = uri.getPath().toString();
            file = new File(path);
            String url;
            Toast.makeText(this, "文件路径：" + uri.getPath().toString(), Toast.LENGTH_SHORT).show();
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
            String name = file.getName();
            if (!end.equals("txt")) {
                Toast.makeText(this, "选择文件不是txt文本，请重新选择", Toast.LENGTH_SHORT).show();
            } else {
                wirte.setText(name);
//                wirte.setEnabled(false);
                choice.setText("文件已选择");
                getcontent();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 读取选择txt文本内容
     **/
    private void getcontent() {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        BufferedReader br = null;
        br = new BufferedReader(isr);
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
                Log.e(TAG, "getcontent:" +"  "+ "UD"+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("onActivityResult: ", String.valueOf(sb));
    }

    /**
     * 上传文件到服务器中去
     **/
    private void fanwen(File file, String path) {
        CustomerController loginController = new CustomerController(this);
        File file1 = new File(path);
//        final List<String> photos = new ArrayList<>();
//        上传单个文件
//        List<MultipartBody.Part> parts = null;
//        parts.add(body);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("aFile", file1.getName(), requestFile);

        Map<String, RequestBody> params = new HashMap<>();
        CustomerController.login(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Toast.makeText(MainActivity.this, String.valueOf(success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
