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
    private EditText DeliveryNum;
    private Button deliveryLoc,addDelivery;
    private List<String> optionsItem = new ArrayList<>();
    private List<String> optionsRow = new ArrayList<>();
    private List<List<String>> optionslist = new ArrayList<>();
    private OptionsPickerView pvOptions;

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
        addDelivery = findViewById(R.id.add_delivery);
        deliveryLoc = findViewById(R.id.delivery_location);
        deliveryLoc.setOnClickListener(this);
        initPicker();
        //设定toolbarMenu按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /*
    * 初始化滚轮选择器
    * */
    private void initPicker() {
        optionsItem.add("01");
        optionsRow.add("01");
        optionsRow.add("02");
        List<String> optionslistItems_01 = new ArrayList<>();
        optionslistItems_01.add("01");
        optionslistItems_01.add("02");
        optionslistItems_01.add("03");
        List<String> optionslistItems_02 = new ArrayList<>();
        optionslistItems_02.add("01");
        optionslistItems_02.add("02");
        optionslistItems_02.add("03");
        optionslist.add(optionslistItems_01);
        optionslist.add(optionslistItems_02);

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                deliveryLoc.setText(optionsRow.get(options1) + "行" + optionslist.get(options1).get(options2) + "列");
            }
        })
                .isDialog(true)
                .setTitleText("位置选择")
                .setLabels("行","列","")
                .build();
        pvOptions.setPicker(optionsRow,optionslist);
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
            case R.id.delivery_location:
                pvOptions.show();
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
