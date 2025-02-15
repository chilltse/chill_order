//package com.example.menu;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import androidx.appcompat.app.AppCompatActivity;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserActivity extends AppCompatActivity {
//    ListView listViewDishes;
//    DatabaseHelper dbHelper;
//    List<Dish> dishList;
//    DishAdapter dishAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);
//
//        listViewDishes = findViewById(R.id.listViewDishes);
//        dbHelper = new DatabaseHelper(this);
//        dishList = new ArrayList<>();
//
//        dishAdapter = new DishAdapter(this, dishList);
//        listViewDishes.setAdapter(dishAdapter);
//
//        loadDishes();
//
//        listViewDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Dish selectedDish = dishList.get(position);
//
//                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
//                intent.putExtra("DishName", selectedDish.getName());
//                intent.putExtra("ImagePath", selectedDish.getImagePath());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void loadDishes() {
//        Cursor cursor = dbHelper.getAllDishes();
//        dishList.clear();
//
//        if (cursor.moveToFirst()) {
//            do {
//                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DISH_NAME);
//                int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DISH_IMAGE);
//
//                if (nameIndex >= 0 && imageIndex >= 0) {
//                    String dishName = cursor.getString(nameIndex);
//                    String imagePath = cursor.getString(imageIndex);
//
////                    Log.d("Database", "Dish: " + dishName + ", ImagePath: " + imagePath);
//
//                    // 处理 imagePath 为空的情况
//                    if (imagePath == null || imagePath.isEmpty()) {
//                        imagePath = "android.resource://" + getPackageName() + "/drawable/placeholder_image";
//                    }
//
//                    dishList.add(new Dish(dishName, imagePath));
//                }
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        dishAdapter.notifyDataSetChanged(); // 更新列表
//    }
//
//    @Override
//    protected void onDestroy() {
//        dbHelper.close();
//        super.onDestroy();
//    }
//}
