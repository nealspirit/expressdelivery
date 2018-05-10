package com.example.express_delivery;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton add_delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        add_delivery = findViewById(R.id.add_delivery);
        add_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,addDeliveryActivity.class);
                startActivity(intent);
            }
        });
    }
}
