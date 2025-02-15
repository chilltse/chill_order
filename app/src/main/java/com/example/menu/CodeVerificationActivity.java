//package com.example.menu;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.util.Log;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class CodeVerificationActivity extends AppCompatActivity {
//
//    private String TAG = "CodeVerification";
//    private boolean isCodeValid = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_code_verification);
//
//
//    }
//
//    public boolean verifyCode(String inputCode) {
//        // 设置验证码2分钟后过期
//        new CountDownTimer(120000, 1000) {  // 120000毫秒 = 2分钟, 间隔1000毫秒 = 1秒
//            public void onTick(long millisUntilFinished) {
//                // 每秒调用一次
//                Log.d(TAG, "seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                // 计时器结束
//                isCodeValid = false;  // 设置验证码为无效
//                Log.d(TAG, "Verification code has expired");
//            }
//        }.start();
//
//
//        // 检查输入的验证码是否与生成的验证码匹配且仍然有效
//        if (isCodeValid && currentVerificationCode.equals(inputCode)) {
//            return true;  // 验证码正确且有效
//        } else {
//            return false;  // 验证码无效或不匹配
//        }
//    }
//}