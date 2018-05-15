package com.example.express_delivery;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class addDeliveryActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView iv_scanQR;
    private EditText DeliveryNum,phoneNum;
    private Button addDelivery;
    private TextView tv_location;
    private String Loc;//记录货物地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_addDelivery);
        setSupportActionBar(toolbar);
        tv_location = findViewById(R.id.delivery_location);
        iv_scanQR = findViewById(R.id.scanQR);
        iv_scanQR.setOnClickListener(this);
        DeliveryNum = findViewById(R.id.et_deliveryNum);
        addDelivery = findViewById(R.id.add_delivery);
        addDelivery.setOnClickListener(this);
        phoneNum = findViewById(R.id.et_delivery_PhoneNum);
        //设定toolbarMenu按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设定货架号
        int position = getIntent().getIntExtra(ShowDeliveryItemActivity.DELIVERY_LOC,10);
        if (position >= 0 && position < 6){
            Loc = Utility.location(position);
            String loc_string = Utility.location_string(position);
            tv_location.setText(loc_string);
        }else if (position > 6){
            Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
            finish();
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
            case R.id.add_delivery:
                String delivery_Num = DeliveryNum.getText().toString();
                String delivery_PhoneNum = phoneNum.getText().toString();
                if (delivery_Num == null || delivery_Num.equals("")){
                    Toast.makeText(this, "运单号不能为空", Toast.LENGTH_SHORT).show();
                }else if (delivery_PhoneNum == null || delivery_PhoneNum.equals("")){
                    Toast.makeText(this, "手机号不能为空或不符合要求", Toast.LENGTH_SHORT).show();
                }else {
                    Delivery delivery = new Delivery();
                    delivery.setLocation(Loc);
                    delivery.setdeliveryNum(delivery_Num);
                    delivery.setPhoneNum(delivery_PhoneNum);
                    delivery.setRandomCode(Utility.initcode());
                    delivery.save();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空", Toast.LENGTH_LONG).show();
            } else {
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                DeliveryNum.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
