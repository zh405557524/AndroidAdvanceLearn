package com.soul.materialdesign;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "snack Action", 1000)//snack 显示的文字
                        .setAction("Toast", new View.OnClickListener() {// snack 的点击事件
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, " to do ", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
            }
        });

        //Toolbar关联侧滑菜单
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();//关联


        //侧滑菜单中使用navigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//设置监听

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        final int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_camera:
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
                break;
            case R.id.nav_gallery:
                startActivity(new Intent(MainActivity.this, MovieDetailActivity.class));
                break;
            case R.id.nav_slideshow:
                startActivity(new Intent(MainActivity.this, FloatTabActivity.class));
                break;
            case R.id.nav_vip:
                //过度动画
                final Intent intent = new Intent(this, VipActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.nav_bottom_navigation:
                startActivity(new Intent(MainActivity.this, BottomNavigationViewActivity.class));
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Toolbar 添加action
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toolbar 响应action
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
