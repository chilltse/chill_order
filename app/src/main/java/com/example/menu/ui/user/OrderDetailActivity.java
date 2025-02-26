package com.example.menu.ui.user;  // Adjust this package name as per your project structure

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 邮件
import com.example.menu.service.EmailService;
import com.example.menu.R;

public class OrderDetailActivity extends AppCompatActivity {
    // Declare UI components
    private EditText editTextRemark;
    private Button buttonOrder;
    private TextView textViewDishName;
    private final static String TAG = "OrderDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);  // Set the content view to the layout defined in XML

        // Initialize UI components by finding them by their ID
        textViewDishName = findViewById(R.id.textViewDishName);
        editTextRemark = findViewById(R.id.editTextRemark);
        buttonOrder = findViewById(R.id.buttonOrder);

        // Retrieve the dish name passed from MainActivity
        String dishName = getIntent().getStringExtra("DishName");
        textViewDishName.setText(dishName);  // Set the text of TextView to the received dish name

        // Set an onClickListener on the order button to handle order placement
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here you can add logic to handle the order process
                Toast.makeText(OrderDetailActivity.this, "Order placed for " + dishName + " with remark: " + editTextRemark.getText().toString(), Toast.LENGTH_SHORT).show();
                // The above Toast message will display a confirmation with the dish name and the remark entered
                String dishName = textViewDishName.getText().toString();
                String remark = editTextRemark.getText().toString();
                String userName = getIntent().getStringExtra("UserName");
                EmailService.sendOrderEmail(getApplicationContext(), userName , dishName, remark);
//                sendEmail(dishName, remark);
                Toast.makeText(OrderDetailActivity.this, "Email sent for " + dishName + " with remark: " + remark, Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void sendEmail(String dishName, String remark) {
//        final String username = "13178554467@163.com";  // Your email
//        final String receiverMail = "chilltse808@gmail.com";  // Your email
////        final String password = "123ANUanu";  // Your email password
//        final String password = "DEsbiMTRTMuR3Dwc";  // Your email password
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.163.com");
//        props.put("mail.smtp.port", "465");  // 或者 994
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");  // 163 需要 SSL
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverMail));
//            message.setSubject("New Order Placed");
//            message.setText("Dish Name: " + dishName + "\nRemark: " + remark);
//
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        Transport.send(message);
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
}
