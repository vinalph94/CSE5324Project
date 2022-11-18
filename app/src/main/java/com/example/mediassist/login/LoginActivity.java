package com.example.mediassist.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.resetpassword.ForgotPasswordActivity;


public class LoginActivity extends AppCompatActivity {
    private View _bg__signin_page_ek2;
    private View rectangle_12;
    private View rectangle_7;
    private TextView sign_in_to_your_account;
    private TextView email_;
    private TextView login_email_error;
    private TextView login_pwd_error;
    private EditText email;
    private View rectangle_8;
    private View rectangle_11;
    private Button signin;
    private TextView password_;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rectangle_7 = (View) findViewById(R.id.rectangle_7);
        sign_in_to_your_account = (TextView) findViewById(R.id.sign_in_to_your_account);
        email_ = (TextView) findViewById(R.id.email_);
        email = (EditText) findViewById(R.id.email);
        email.setSelection(0);
        rectangle_8 = (View) findViewById(R.id.rectangle_8);
        password_ = (TextView) findViewById(R.id.password_);
        password = (EditText) findViewById(R.id.password);
        password.setSelection(0);
        signin = (Button) findViewById(R.id.sign_in);
        login_email_error = findViewById(R.id.login_email_error_text);
        login_pwd_error = findViewById(R.id.login_pwd_error_text);
        signin.setEnabled(true);

    }

    public void onClickSignInButton(View view) {

        login_email_error.setVisibility(View.GONE);
        login_pwd_error.setVisibility(View.GONE);

//        String mail = email.getText().toString();
//        String pwd = password.getText().toString();
//        if (TextUtils.isEmpty(mail)) {
//            login_email_error.setVisibility(View.VISIBLE);
//        } else if (TextUtils.isEmpty(pwd)) {
//            login_pwd_error.setVisibility(View.VISIBLE);
//        } else {
//            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//            startActivity(intent);
//        }


        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);

    }

    public void onClickForgotPwdButton(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}