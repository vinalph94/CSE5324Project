package com.example.mediassist.signup;


import android.content.res.ColorStateList;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;

import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;

import com.example.mediassist.databinding.ActivityRegisterBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



public class RegisterActivity extends AppCompatActivity implements CheckForEmptyCallBack {

    private ActivityRegisterBinding binding;

    boolean isAllFieldsChecked = false;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;
    DocumentReference ref;
    boolean isUserExist = false;

    public void setUserExist(boolean userExist) {
        isUserExist = userExist;
    }

    public boolean getUserExist() {
        return isUserExist;
    }


    //ProgressDialog progressDialog;

    private EditText editTextName, editTextPhone, editTextEmail, editTextPassword, editTextRetypePwd;
    private TextView userNameError, userEmailError,userPhoneError, userPwdError, userRePwdError;
    private Button signUpBtn;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String retypepassword;
    private RegisterUserModel registerUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_register);
     
        editTextName = binding.registerNameText;
        editTextPhone = binding.registerPhoneNumberText;
        editTextEmail = binding.registerEmailText;
        editTextPassword = binding.registerPwdText;
        editTextRetypePwd =  binding.registerRepwdText;
        signUpBtn = binding.signupButton;
        userNameError = binding.registerNameErrorText;
        userEmailError = binding.registerEmailErrorText;
        userPhoneError = binding.registerPhoneNumberErrorText;
        userPwdError = binding.registerPwdErrorText;
        userRePwdError = binding.registerRepwdErrorText;
       
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
     



        editTextName.addTextChangedListener(new CustomTextWatcher(userNameError, this));
        editTextPhone.addTextChangedListener(new CustomTextWatcher(userPhoneError, this));
        editTextEmail.addTextChangedListener(new CustomTextWatcher(userEmailError, this));

        checkRegisterData();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegisterData();
                registerUserModel = new RegisterUserModel(name, phoneNumber, email, password);
                Toast.makeText(RegisterActivity.this, "succesfull", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void checkRegisterData() {
        name = editTextName.getText().toString();
        phoneNumber = editTextPhone.getText().toString();
        email = editTextEmail.getText().toString();
        password =editTextPassword.getText().toString();
        retypepassword = editTextRetypePwd.getText().toString();
        if (!(name.isEmpty()) && !(phoneNumber.isEmpty()) && !(email.isEmpty()) && !(password.isEmpty()) && !(retypepassword.isEmpty())) {
            signUpBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            signUpBtn.setEnabled(true);
        }
    }
    @Override
    public void checkForEmpty() {
        checkRegisterData();
    }

}