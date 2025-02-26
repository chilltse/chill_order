package com.example.menu.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.data.database.DatabaseHelper;
import com.example.menu.R;

public class AdminActivity extends AppCompatActivity {
    private EditText editTextDishName;
    private Button buttonAddDish;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextDishName = findViewById(R.id.editTextDishName);
        buttonAddDish = findViewById(R.id.buttonAddDish);
        dbHelper = new DatabaseHelper(this);

        buttonAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDish();
            }
        });
    }

    private void addDish() {
        String dishName = editTextDishName.getText().toString().trim();
        if (!dishName.isEmpty()) {
            long result = dbHelper.addDish(dishName);
            if (result == -1) {
                Toast.makeText(this, "Failed to add dish", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Dish added successfully", Toast.LENGTH_SHORT).show();
                editTextDishName.setText(""); // Clear the input field
            }
        } else {
            Toast.makeText(this, "Dish name cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // Close database connection
        super.onDestroy();
    }

}
