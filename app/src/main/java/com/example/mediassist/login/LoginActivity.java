package com.example.mediassist.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediassist.R;


public class LoginActivity extends AppCompatActivity {
    private View _bg__signin_page_ek2;
    private View rectangle_12;
    private View rectangle_7;
    private TextView sign_in_to_your_account;
    private TextView email_;
    private EditText email;
    private View rectangle_8;
    private View rectangle_11;
    // rectangle_11 = (View) findViewById(R.id.rectangle_11);
    private Button signin;
    private TextView password_;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _bg__signin_page_ek2 = (View) findViewById(R.id._bg__signin_page_ek2);
        rectangle_12 = (View) findViewById(R.id.rectangle_12);
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
    }
    public void onClick(View view) {

        String mail = email.getText().toString();
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "Email and password is required", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "login succesfully", Toast.LENGTH_SHORT).show();
        }
    }


}