package com.example.menu.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OrderApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DatabaseHelper";

    // 用户表
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ROLE = "role";

    // 菜品表
    private static final String TABLE_DISHES = "dishes";
    private static final String COLUMN_DISH_ID = "id";
    public static final String COLUMN_DISH_NAME = "name";
    public static final String COLUMN_DISH_IMAGE = "image_path";  // 新增字段

    // 订单表
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_USER_ID = "user_id";
    private static final String COLUMN_ORDER_DISH_ID = "dish_id";
    private static final String COLUMN_ORDER_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_REMARKS = "remarks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        db.execSQL("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_USERNAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_ROLE + " TEXT)");

        // 创建菜品表
        db.execSQL("CREATE TABLE " + TABLE_DISHES + "("
                + COLUMN_DISH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DISH_NAME + " TEXT)");

        // 创建订单表
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_USER_ID + " INTEGER,"
                + COLUMN_ORDER_DISH_ID + " INTEGER,"
                + COLUMN_ORDER_QUANTITY + " INTEGER,"
                + COLUMN_ORDER_REMARKS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // 添加用户
    public long addUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_PASSWORD, BCrypt.hashpw(password, BCrypt.gensalt()));
        values.put(COLUMN_USER_ROLE, role);

//        return db.insert(TABLE_USERS, null, values);
        // 覆盖更新
        return db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // 获取所有菜品
    public Cursor getAllDishes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DISHES, null);
    }

    // 添加菜品
    public long addDish(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISH_NAME, name);
        return db.insert(TABLE_DISHES, null, values);
    }

    public void deleteDb(String DbName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DbName); // 删除所有数据
        db.close();
    }
    // 添加菜品（包括图片路径）
//    public long addDish(String name, String imagePath) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_DISH_NAME, name);
//        values.put(COLUMN_DISH_IMAGE, imagePath); // 存储图片路径
//        return db.insert(TABLE_DISHES, null, values);
//    }
//
//    // 获取所有菜品（带图片路径）
//    public Cursor getAllDishes() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + TABLE_DISHES, null);
//    }

    // 添加订单
    public long addOrder(int userId, int dishId, int quantity, String remarks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_USER_ID, userId);
        values.put(COLUMN_ORDER_DISH_ID, dishId);
        values.put(COLUMN_ORDER_QUANTITY, quantity);
        values.put(COLUMN_ORDER_REMARKS, remarks);
        return db.insert(TABLE_ORDERS, null, values);
    }


    public boolean validateUser(String username, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 给下面变成变量的表示方式
        Cursor cursor = db.rawQuery(
                "SELECT password, role FROM " + TABLE_USERS +
                        " WHERE username = ?",
                new String[]{username});

        if (cursor.moveToFirst()) {
            Log.d(TAG, "#############moveToFirst了！#############");
            String storedPassword = cursor.getString(0);
            String storedRole = cursor.getString(1);

            Log.d(TAG, "storedPassword:" + storedPassword );
            Log.d(TAG, "storedRole:" + storedRole );

            // Verify the hashed password
            if (BCrypt.checkpw(password, storedPassword) && role.equals(storedRole)) {
                cursor.close();
                db.close();
                // Handle user role
                return true;
            }
        }

        Log.d(TAG, "#############没有moveToFirst！############");

        cursor.close();
        db.close();
        return false;
    }
}
