package com.example.mediassist.login;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.ActivityLoginBinding;
import com.example.mediassist.resetpassword.ForgotPasswordActivity;
import com.example.mediassist.signup.RegisterActivity;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity implements CheckForEmptyCallBack {
    public static String patientUsername;
    public static String patientUid;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    private View _bg__signin_page_ek2;
    private View rectangle_12;
    private View rectangle_7;
    private TextView sign_in_to_your_account;
    private TextView email_;
    private TextView emailError;
    private TextView pwdError;
    private EditText email;
    private View rectangle_8;
    private View rectangle_11;
    private Button signin;
    private TextView password_;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        emailError = findViewById(R.id.login_email_error_text);
        pwdError = findViewById(R.id.login_pwd_error_text);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(new CustomTextWatcher(emailError, this));
        password.addTextChangedListener(new CustomTextWatcher(pwdError, this));
    }

    public void onClickSignInButton(View view) {

        if (checkForData()) {
            loginUser(email.getText().toString().trim(), password.getText().toString().trim());
        }


    }

    public void onClickForgotPwdButton(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void loginUser(String email, String password) {
//        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//        startActivity(intent);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    patientUid = task.getResult().getUser().getUid();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("userId", patientUid);
                    startActivity(intent);
                    finish();


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                new CustomToast(getApplicationContext(), LoginActivity.this, e.getMessage(), ToastStatus.FAILURE).show();



            }
        });

    }

    @Override
    public void checkForEmpty() {
        checkForData();
    }


    private boolean checkForData() {
        String mail = email.getText().toString().trim();
        patientUsername = mail;
        String pwd = password.getText().toString().trim();


        if (!(mail.isEmpty()) && !(pwd.isEmpty())) {
            signin.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            signin.setEnabled(true);
            return true;
        }

        return false;
    }
}