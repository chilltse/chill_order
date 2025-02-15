//package com.example.menu;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.IOException;
//
//public class AdminActivity extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private EditText editTextDishName;
//    private Button buttonSelectImage, buttonAddDish;
//    private ImageView imageViewDish;
//
//    private Uri imageUri; // 选中的图片 URI
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin);
//
//        editTextDishName = findViewById(R.id.editTextDishName);
//        buttonSelectImage = findViewById(R.id.buttonSelectImage);
//        buttonAddDish = findViewById(R.id.buttonAddDish);
//        imageViewDish = findViewById(R.id.imageViewDish);
//
//        // 选择图片
//        buttonSelectImage.setOnClickListener(v -> openImagePicker());
//
//        // 添加菜品
//        buttonAddDish.setOnClickListener(v -> addDish());
//    }
//
//    // 打开相册选择图片
//    private void openImagePicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    // 处理用户选择的图片
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                imageViewDish.setImageBitmap(bitmap);
//                imageViewDish.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // 添加菜品到数据库（或存储到 Firebase）
//    private void addDish() {
//        String dishName = editTextDishName.getText().toString().trim();
//
//        if (dishName.isEmpty() || imageUri == null) {
//            Toast.makeText(this, "Please enter a dish name and select an image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // 在这里存储菜品数据
//        Toast.makeText(this, "Dish added: " + dishName, Toast.LENGTH_SHORT).show();
//    }
//}
