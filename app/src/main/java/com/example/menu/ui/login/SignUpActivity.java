package com.example.menu.ui.login;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import java.util.Random; // 导入Random类
import android.os.CountDownTimer; // 导入CountDownTimer类

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.data.database.DatabaseHelper;
import com.example.menu.service.EmailService;
import com.example.menu.R;

public class SignUpActivity extends AppCompatActivity {
    private Button buttonFinish;
    private Button buttonSendCode;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCode;
    private String username;
    private String email;
    private String password;
    private String verificationCode;
    private DatabaseHelper dbHelper;
    private String TAG = "SignUpActivity";
    private boolean isCodeValid = false;

    private String currentVerificationCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);

        buttonFinish = findViewById(R.id.buttonFinish);
        buttonSendCode = findViewById(R.id.buttonSendCode);
        editTextUsername = findViewById(R.id.editTextNewUsername);
        editTextEmail = findViewById(R.id.editTextNewEmail);
        editTextPassword = findViewById(R.id.editTextNewPassword);
        editTextCode = findViewById(R.id.editTextNewCode);

//        sendVerificationCode
        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                username = editTextUsername.getText().toString();
                if(validateEmailAndName()){
                    Log.d(TAG, "validateEmail succeed!");
                    sendVerificationCode();
                }
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString();
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                verificationCode = editTextCode.getText().toString();

                if (validateForm()) {
//                    if (userExists(username, email)) {
                    if(verifyCode()){
                        // 存储用户信息到数据库
                        Log.d(TAG, "Succeed save user data!");
                        dbHelper.addUser(username, password, "user");
                        Toast.makeText(getApplicationContext(), "Succeed save user data!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Log.d(TAG, "Wrong code!");
                        Toast.makeText(getApplicationContext(), "Wrong code!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

//    判空
    private boolean validateForm() {
        // 检查是否信息填完整
        if (username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || verificationCode.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all fields correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return validateEmailAndName();
    }

    private boolean validateEmailAndName() {
        // 检查是否信息填完整
        if (email.isEmpty() || username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill email correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 验证用户是否存在
        if(userExists()){
            Toast.makeText(getApplicationContext(), "Error: Username \""+username+"\"already exists!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 检查邮箱格式是否正确
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email format"); // 设置错误提示
            return false;
        }

        return true;
    }

    private boolean userExists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { "id" }; // 只关心是否有结果，不需要获取具体数据
        String selection = "username = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public void sendVerificationCode() {
        // 生成一个随机的6位数验证码
        currentVerificationCode = String.format("%06d", new Random().nextInt(999999));

        isCodeValid = true;
        // 输出验证码到日志，实际应用中应发送至用户的邮箱
        Log.d(TAG, "Generated Code: " + currentVerificationCode);

        // 发送验证邮件
        EmailService.sendCodeEmail(getApplicationContext(), username, email, currentVerificationCode);

        // 设置验证码2分钟后过期
        new CountDownTimer(120000, 1000) {  // 120000毫秒 = 2分钟, 间隔1000毫秒 = 1秒
            public void onTick(long millisUntilFinished) {
                // 每秒调用一次
                String remainingSec = String.valueOf(millisUntilFinished / 1000);
                buttonSendCode.setText(remainingSec);
                buttonSendCode.setEnabled(false);
                Log.d(TAG, "seconds remaining: " + remainingSec );
            }

            public void onFinish() {
                // 计时器结束
                isCodeValid = false;  // 设置验证码为无效
                buttonSendCode.setText("Send Code");
                buttonSendCode.setEnabled(true);
                Log.d(TAG, "Verification code has expired");
            }
        }.start();
    }

    public boolean verifyCode() {
        // 检查输入的验证码是否与生成的验证码匹配且仍然有效
        if (isCodeValid && currentVerificationCode.equals(verificationCode)) {
            return true;  // 验证码正确且有效
        } else {
            return false;  // 验证码无效或不匹配
        }
    }

}