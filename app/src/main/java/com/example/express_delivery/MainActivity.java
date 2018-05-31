package com.example.express_delivery;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DeliveryAdapter adapter;
    private List<Delivery> deliveryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //设置recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DeliveryAdapter();
        recyclerView.setAdapter(adapter);
        //设定动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(scaleAnimation,0.1f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        recyclerView.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.showTable:
                Intent intent = new Intent(MainActivity.this,TableActivity.class);
                startActivity(intent);
                break;
            case R.id.update:
                updateToWeb();
                break;
        }
        return true;
    }

    private void updateToWeb() {
        new Thread(new Runnable() {
            private Connection connection = null;

            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = (Connection) DriverManager.getConnection("jdbc:mysql://39.108.211.170:3306/jsp_db", "root", "580420");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "出现未知错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "连接服务器失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                try{
                    Statement stmt = connection.createStatement();
                    //删除数据库中所有数据
                    String sql = "delete from delivery_data";
                    stmt.executeUpdate(sql);
                    //增加一条数据
                    deliveryList = DataSupport.findAll(Delivery.class);
                    if (deliveryList.size() > 0){
                        for (Delivery delivery : deliveryList){
                            sql = "insert into delivery_data (delivery_num,delivery_phone,delivery_location,random_num) values ('" + delivery.getdeliveryNum() + "','" + delivery.getPhoneNum() + "','" + delivery.getLocation() + "','" + delivery.getRandomCode() + "')";
                            stmt.executeUpdate(sql);
                        }
                    }
                    stmt.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "上传数据成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "上传数据出现错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }finally {
                    if (connection != null){
                        try{
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
