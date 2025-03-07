package com.example.menu.service;

import android.content.Context;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    final static String USERNAME = "13178554467@163.com";  // 你的邮箱用户名
    final static String PASSWORD = "DEsbiMTRTMuR3Dwc";  // 你的邮箱密码
    final static String RECIPIENT_MAIL = "chilltse808@gmail.com";  // 接收者邮箱
    final static String ORDER_TEMPLATE = "order_template.html";
    final static String CODE_TEMPLATE = "code_template.html";
    final static String TAG = "EmailService";
    private static final String SMTP_HOST = "smtp.163.com";
    private static final String SMTP_PORT = "465";

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    public static void sendEmail(final Context context, final String templateFileName, final String subject, final String recipient, String... replacements) {
        // 加载HTML模板，把模板中的placeholder相对应替换
        String content = loadEmailTemplate(context, templateFileName);
        for (int i = 0; i < replacements.length; i += 2) {
            content = content.replace(replacements[i], replacements[i + 1]);
        }

        try {
            Message message = new MimeMessage(createSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // 使用线程池或其他异步方式来发送邮件
            new Thread(() -> {
                try {
                    Transport.send(message);
                    Log.i(TAG, "Email sent successfully to " + recipient);
                } catch (MessagingException e) {
                    Log.e(TAG, "Failed to send email", e);
                }
            }).start();

        } catch (MessagingException e) {
            Log.e(TAG, "Failed to prepare email", e);
        }
    }

//    使用多线程处理,为了防止在Android主线程上执行网络操作而引起的 android.os.NetworkOnMainThreadException
//    public static void sendOrderEmail(final Context context, final String name, final String dishName, final String remark) {
//            // 读取HTML模板
//            String content = loadEmailTemplate(context, ORDER_TEMPLATE);  // 假设模板文件名为 order_template.html
//            content = content.replace("${name}", name);
//            content = content.replace("${dishName}", dishName);
//            content = content.replace("${remark}", remark);
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.163.com");
//            props.put("mail.smtp.port", "465");
//            props.put("mail.smtp.ssl.enable", "true");  // 163 需要 SSL
//            props.put("mail.smtp.auth", "true");
//
//
//            Session session = Session.getInstance(props,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(USERNAME, PASSWORD);  // Use your real username and password here
//                        }
//                    });
//
//            try {
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(USERNAME));  // Use your real email address here
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(RECIPIENT_MAIL));  // Use your recipient email address here
//                message.setSubject("New Order Placed");
//                message.setContent(content, "text/html; charset=utf-8");
//
//                // 使用线程异步发送邮件
//                new Thread(() -> {
//                    try {
//                        Transport.send(message);
//                    } catch (MessagingException e) {
//                        Log.e(TAG, "Email sending failed", e);
//                    }
//                }).start();
//
//            } catch (MessagingException e) {
//                Log.e(TAG, "Email sending failed", e);
//            }
//    }
//
//
//
//    public static void sendCodeEmail(final Context context, final String name, final String targetEmail, final String code) {
//        // 读取HTML模板
//        String content = loadEmailTemplate(context, CODE_TEMPLATE);  // 假设模板文件名为 order_template.html
//        content = content.replace("${name}", name);
//        content = content.replace("${code}", code);
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.163.com");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.ssl.enable", "true");  // 163 需要 SSL
//        props.put("mail.smtp.auth", "true");
//
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(USERNAME, PASSWORD);  // Use your real username and password here
//                    }
//                });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(USERNAME));  // Use your real email address here
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(targetEmail));  // Use your recipient email address here
//            message.setSubject("Chill Order New Membership");
//            message.setContent(content, "text/html; charset=utf-8");
//
//            // 使用线程异步发送邮件
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

    public static void sendOrderEmail(final Context context, final String name, final String dishName, final String remark) {
        sendEmail(context, "order_template.html", "New Order Placed", RECIPIENT_MAIL,
                "${name}", name, "${dishName}", dishName, "${remark}", remark);
    }

    public static void sendCodeEmail(final Context context, final String name, final String recipientEmail, final String code) {
        sendEmail(context, "code_template.html", "Verification Code", recipientEmail,
                "${name}", name, "${code}", code);
    }


    private static String loadEmailTemplate(Context context, String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "Read email template failed", e);
        }
        Log.d(TAG, content.toString());
        return content.toString();
    }
}
