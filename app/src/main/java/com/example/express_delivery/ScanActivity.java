package com.example.express_delivery;

import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener{
    private Toolbar toolbar;
    private CaptureManager captureManager;
    private DecoratedBarcodeView mDBV;
    private ImageView switchLight;
    private boolean isLighton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_scan);
        setSupportActionBar(toolbar);
        mDBV = findViewById(R.id.dbv);
        switchLight = findViewById(R.id.switch_light);
        //设定toolbarMenu按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置扫描控件
        captureManager = new CaptureManager(this,mDBV);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();

        mDBV.setTorchListener(this);
        //如果没有闪光灯功能，则取消按键
        if (!hasFlash()){
            switchLight.setVisibility(View.GONE);
        }
        //设置闪光灯按钮
        switchLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLighton){
                    switchLight.setImageResource(R.drawable.light_off);
                    mDBV.setTorchOff();
                }else {
                    switchLight.setImageResource(R.drawable.light_on);
                    mDBV.setTorchOn();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTorchOn() {
        isLighton = true;
    }

    @Override
    public void onTorchOff() {
        isLighton = false;
    }

    //判断是否有闪光灯功能
    private boolean hasFlash(){
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
