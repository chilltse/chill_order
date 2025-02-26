package com.example.menu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.data.database.DatabaseHelper;
import com.example.menu.R;
import com.example.menu.ui.admin.AdminActivity;
import com.example.menu.ui.user.UserActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    private static final String TAG = "MainActivity";

    private TextView textViewSignUp;

    private DatabaseHelper dbHelper;

    private String loginAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default value for loginAs
        loginAs = "user";
        dbHelper = new DatabaseHelper(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        temptInitUserDb();

        // 登录选择监听器
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupLoginAs);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId 是被选中的 RadioButton 的 ID
                if (checkedId == R.id.radioButtonUser) {
                    loginAs = "user";
                    textViewSignUp.setVisibility(View.VISIBLE);  // 显示“Sign up”
                    Toast.makeText(getApplicationContext(), "Login as user", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radioButtonAdmin) {
                    loginAs = "admin";
                    textViewSignUp.setVisibility(View.GONE);  // 隐藏“Sign up”
                    Toast.makeText(getApplicationContext(), "Login as admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        注册用户监听器
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行打开注册活动或其他逻辑
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // 登录按钮监听器
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        Log.d(TAG, "username:" + username);
        Log.d(TAG, "password:" + password);
        Log.d(TAG, "loginAs:" + loginAs);

        if (dbHelper.validateUser(username, password, loginAs)) {
            // Navigate to the next Activity based on user role
            Intent intent = loginAs.equals("user") ?
                    new Intent(MainActivity.this, UserActivity.class):
                    new Intent(MainActivity.this, AdminActivity.class);
            intent.putExtra("UserName", username);
            startActivity(intent);
        } else {
            editTextPassword.setError("Invalid username or password");
        }
    }

    private void temptInitUserDb(){
        dbHelper.addUser("user3", "user3", "user");
        dbHelper.addUser("admin1", "admin1", "admin");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // Close database connection
        super.onDestroy();
    }
}

