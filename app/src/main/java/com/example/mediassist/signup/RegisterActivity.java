package com.example.mediassist.signup;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityRegisterBinding;
import com.example.mediassist.login.LoginActivity;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity implements CheckForEmptyCallBack {

    boolean isAllFieldsChecked = false;
    FirebaseUser mUser;
    FirebaseFirestore db;
    DocumentReference ref;
    boolean isUserExist = false;
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextPhone, editTextEmail, editTextPassword, editTextRetypePwd;
    private TextView userNameError;


    //ProgressDialog progressDialog;
    private TextView userEmailError;
    private TextView userEmailError2;
    private TextView userPhoneError;
    private TextView userPhoneError2;
    private TextView userRePwdError;
    private TextView userPwdError;
    private Button signUpBtn;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String retypepassword;
    private RegisterUserModel registerUserModel;

    public boolean getUserExist() {
        return isUserExist;
    }

    public void setUserExist(boolean userExist) {
        isUserExist = userExist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextName = binding.registerNameText;
        editTextPhone = binding.registerPhoneNumberText;
        editTextEmail = binding.registerEmailText;
        editTextPassword = binding.registerPwdText;
        editTextRetypePwd = binding.registerRepwdText;
        signUpBtn = binding.signupButton;

        userNameError = binding.registerNameErrorText;
        userEmailError = binding.registerEmailErrorText;
        userEmailError2 = binding.registerEmailErrorText2;
        userPhoneError = binding.registerPhoneNumberErrorText;
        userPhoneError2 = binding.registerPhoneNumberErrorText2;
        userPwdError = binding.registerPwdErrorText;
        userRePwdError = binding.registerRepwdErrorText;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        editTextName.addTextChangedListener(new CustomTextWatcher(userNameError, this));
        editTextPhone.addTextChangedListener(new CustomTextWatcher(userPhoneError, this));
        editTextEmail.addTextChangedListener(new CustomTextWatcher(userEmailError, this));
        editTextPassword.addTextChangedListener(new CustomTextWatcher(userEmailError, this));
        editTextRetypePwd.addTextChangedListener(new CustomTextWatcher(userEmailError, this));

        checkRegisterData();
    }

    public void onClickSignUpButton(View view) {
        boolean validateData = registerUser();
        if (validateData) {
            checkRegisterData();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        registerUserModel = new RegisterUserModel(name, email, phoneNumber, password, "4");
                        DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        documentReference.set(registerUserModel).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {
                                new CustomToast(getApplicationContext(), RegisterActivity.this, "user registered", ToastStatus.SUCCESS).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                new CustomToast(getApplicationContext(),RegisterActivity.this, "fail to register user" + e, ToastStatus.FAILURE).show();

                            }
                        });


                    } else {
//                        new CustomToast(getApplicationContext(),RegisterActivity.this, "fail" + Objects.requireNonNull(task.getException()).getMessage(), ToastStatus.FAILURE).show();

                    }

                }
            });
        } else {
            new CustomToast(getApplicationContext(),RegisterActivity.this, "Please check data again", ToastStatus.FAILURE).show();

        }

    }

    private void checkRegisterData() {
        name = editTextName.getText().toString();
        phoneNumber = editTextPhone.getText().toString();
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        retypepassword = editTextRetypePwd.getText().toString();

        if (!(name.isEmpty()) && !(phoneNumber.isEmpty()) && !(email.isEmpty()) && !(password.isEmpty()) && !(retypepassword.isEmpty())) {
            signUpBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            signUpBtn.setEnabled(true);
        }
    }

    private boolean registerUser() {
        String mobileRegex = "[0-9]{10}";
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        String fullName = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();

        if (fullName.length() == 0) {
            userNameError.setVisibility(View.VISIBLE);
            return false;
        } else if (phoneNumber.length() == 0) {
            userPhoneError.setVisibility(View.VISIBLE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else if (editTextPhone.getText().toString().length() != 10 || (!mobilePattern.matcher(editTextPhone.getText().toString()).find())) {
            userPhoneError2.setVisibility(View.VISIBLE);
            userPhoneError.setVisibility(View.GONE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else if (email.length() == 0) {
            userEmailError.setVisibility(View.VISIBLE);
            userPhoneError2.setVisibility(View.GONE);
            userPhoneError.setVisibility(View.GONE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
            userEmailError2.setVisibility(View.VISIBLE);
            userEmailError.setVisibility(View.GONE);
            userPhoneError2.setVisibility(View.GONE);
            userPhoneError.setVisibility(View.GONE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else if (password.length() < 8) {
            userPwdError.setVisibility(View.VISIBLE);
            userEmailError2.setVisibility(View.GONE);
            userEmailError.setVisibility(View.GONE);
            userPhoneError2.setVisibility(View.GONE);
            userPhoneError.setVisibility(View.GONE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else if (!editTextPassword.getText().toString().equals(editTextRetypePwd.getText().toString())) {
            userRePwdError.setVisibility(View.VISIBLE);
            userPwdError.setVisibility(View.GONE);
            userEmailError2.setVisibility(View.GONE);
            userEmailError.setVisibility(View.GONE);
            userPhoneError2.setVisibility(View.GONE);
            userPhoneError.setVisibility(View.GONE);
            userNameError.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void checkForEmpty() {
        checkRegisterData();
    }

}