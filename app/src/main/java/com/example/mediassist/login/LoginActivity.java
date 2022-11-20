package com.example.mediassist.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.appointment.MakeAppoinmentFragment;
import com.example.mediassist.appointment.ScheduleAppointmentActivity;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.resetpassword.ForgotPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    private View _bg__signin_page_ek2;
    private View rectangle_12;
    private View rectangle_7;
    private TextView sign_in_to_your_account;
    private TextView email_;
    private EditText email;
    private View rectangle_8;
    private View rectangle_11;
    private Button signin;
    private TextView password_;
    private EditText password;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    public static String patientUsername;
    private FirebaseAuth mAuth;


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
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void onClickSignInButton(View view) {

        String mail = email.getText().toString().trim();
        patientUsername = mail;
        String pwd = password.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();


        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
        } else {
            loginUser(mail, pwd);
        }


    }

    public void onClickForgotPwdButton(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void loginUser(String email, String password) {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(LoginActivity.this, "Update the profile " +
//                            "for better expereince", Toast.LENGTH_SHORT).show();
//                    //navigate to dashboard and send role id in the intent so that in dashboard activity we
//                    // can get that role id and check which fragment to laod
//                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}