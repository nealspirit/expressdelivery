package com.example.express_delivery;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ShowDeliveryItemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView location,deliveryNum,Code;
    public static final String DELIVERY_LOC = "delivery_loc";
    private String Loc,Loc_string,delivery_Num,delivery_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_delivery_item);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_addDelivery);
        setSupportActionBar(toolbar);
        location = findViewById(R.id.tv_location);
        deliveryNum = findViewById(R.id.tv_deliveryNum);
        Code = findViewById(R.id.tv_Code);
        //设定toolbarMenu按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //获取位置
        int position = getIntent().getIntExtra(ShowDeliveryItemActivity.DELIVERY_LOC,10);
        if (position >= 0 && position < 6){
            Loc = Utility.location(position);
            //设置位置坐标
            Loc_string = Utility.location_string(position);
            location.setText(Loc_string);
            List<Delivery> deliveries = DataSupport.where("location = ?",Loc).find(Delivery.class);
            if (deliveries != null && deliveries.size() == 1){
                Delivery delivery = deliveries.get(0);
                //取得运单号
                delivery_Num = delivery.getdeliveryNum();
                deliveryNum.setText(delivery_Num);
                //取得随机码
                delivery_Code = String.valueOf(delivery.getRandomCode());
                Code.setText(delivery_Code);
            }else if (deliveries.size() > 1){
                Toast.makeText(this, Loc + "数据错误，重新添加", Toast.LENGTH_SHORT).show();
                DataSupport.deleteAll(Delivery.class,"location = ?",Loc);
            }
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
}
