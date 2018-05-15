package com.example.express_delivery;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.List;

public class TableActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private List<Delivery> deliveryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        toolbar = findViewById(R.id.toolbar_showTable);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = findViewById(R.id.show_table);
        deliveryList = DataSupport.findAll(Delivery.class);
        if (deliveryList.size() > 0){
            for (Delivery delivery : deliveryList){
                textView.append(delivery.getLocation() + " | " + delivery.getdeliveryNum() + " | " + delivery.getRandomCode() + "\n");
            }
        }else {
            textView.setText("无数据");
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
