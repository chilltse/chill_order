package com.example.menu.ui.user;

import android.content.Intent;

import com.example.menu.data.database.DatabaseHelper;
import com.example.menu.R;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    ////NavBar相关设定
    private BottomNavigationView navView;

    private HomeFragment homeFragment;
    private CartFragment cartFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;
    ////

    ListView listViewDishes;
    DatabaseHelper dbHelper;

    private List<String> dishes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listViewDishes = findViewById(R.id.listViewDishes);
        dbHelper = new DatabaseHelper(this);

        loadDishes();

        listViewDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取菜品名称
                String dishName = dishes.get(position);

                // 传递菜品名称到 OrderDetailActivity
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra("DishName", dishName);
                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                startActivity(intent);
            }
        });


        // navbar
        navView = findViewById(R.id.user_nav);
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_mine) {
                selectedFragment = new MineFragment();
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.nav_order) {
                selectedFragment = new OrderFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, selectedFragment).commit();
            return true; // true will display the item as selected to the user
        });

        // 默认选择首页
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, new HomeFragment()).commit();
        }
    }

    private void loadDishes() {
        Cursor cursor = dbHelper.getAllDishes();
//        List<String> dishes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DISH_NAME);
                if (columnIndex >= 0) {
                    String dishName = cursor.getString(columnIndex);
                    dishes.add(dishName);
                } else {
                    // Handle the error or log it
//                    Log.e("DatabaseHelper", "Column not found: " + DatabaseHelper.COLUMN_DISH_NAME);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dishes);
        listViewDishes.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // Ensure to close the database connection
        super.onDestroy();
    }
}
