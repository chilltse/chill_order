package com.example.menu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
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
