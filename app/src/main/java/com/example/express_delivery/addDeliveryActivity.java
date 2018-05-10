package com.example.express_delivery;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Random;

public class addDeliveryActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView iv_scanQR;
    private EditText DeliveryNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_addDelivery);
        setSupportActionBar(toolbar);
        iv_scanQR = findViewById(R.id.scanQR);
        iv_scanQR.setOnClickListener(this);
        DeliveryNum = findViewById(R.id.et_deliveryNum);
        //设定toolbarMenu按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scanQR:
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class)
                        .initiateScan();
                break;
            default:
                break;
        }
    }

    private int initcode() {
        Random random = new Random();
        int code = random.nextInt(9999);
        return code;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                DeliveryNum.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
