package com.example.ai_news_summary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText codeEditText;
    private EditText newPasswordEditText;
    private Button sendCodeButton;
    private Button resetPasswordButton;
    private ImageView toggleNewPassword;
    private boolean isNewPasswordVisible = false;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // 沉浸式状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));

        // 初始化控件
        emailEditText = findViewById(R.id.et_email);
        codeEditText = findViewById(R.id.et_code);
        newPasswordEditText = findViewById(R.id.et_new_password);
        sendCodeButton = findViewById(R.id.btn_send_code);
        resetPasswordButton = findViewById(R.id.btn_reset_password);
        toggleNewPassword = findViewById(R.id.iv_toggle_new_password);

        // 新密码可见性切换
        toggleNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewPasswordVisible) {
                    newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    toggleNewPassword.setImageResource(android.R.drawable.ic_menu_view);
                } else {
                    newPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    toggleNewPassword.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                }
                isNewPasswordVisible = !isNewPasswordVisible;
                newPasswordEditText.setSelection(newPasswordEditText.getText().length());
            }
        });

        // 发送验证码点击事件
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 模拟发送验证码
                Toast.makeText(ForgotPasswordActivity.this, "验证码已发送到" + email, Toast.LENGTH_SHORT).show();

                // 启动60秒倒计时
                startCountDown();
            }
        });

        // 重置密码点击事件
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String code = codeEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();

                if (email.isEmpty() || code.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(ForgotPasswordActivity.this, R.string.reset_success, Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // 验证码倒计时
    private void startCountDown() {
        sendCodeButton.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendCodeButton.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                sendCodeButton.setEnabled(true);
                sendCodeButton.setText(R.string.send_code);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}