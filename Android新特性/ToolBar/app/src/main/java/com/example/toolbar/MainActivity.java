package com.example.toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressLint({"NonConstantResourceId", "RtlHardcoded"})
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("ToolBar");
        //设置菜单
        toolbar.inflateMenu(R.menu.custom_tool_bar_menu);
        //设置菜单监听
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_search:
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_setting:
                    Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_share:
                    Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
        //设置返回图标
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        //设置返回监听
        toolbar.setNavigationOnClickListener(view -> Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show());

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        findViewById(R.id.close).setOnClickListener(view -> drawerLayout.closeDrawer(Gravity.LEFT));
    }
}