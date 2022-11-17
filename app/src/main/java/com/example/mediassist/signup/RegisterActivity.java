package com.example.mediassist.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mediassist.MainActivity;
import com.example.mediassist.R;
import com.example.mediassist.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import org.jetbrains.annotations.NotNull;

import java.lang.ref.PhantomReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;
    //ProgressDialog progressDialog;

    EditText editTextName, editTextPhone, editTextEmail, editTextPassword, retypePwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.phoneNumber);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        retypePwd = findViewById(R.id.retypePwd);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        //progressDialog = new ProgressDialog(this);

       //fullName.setHint(Html.fromHtml("  &nbsp; Full Name <font color =\"#cc0029\" >*</font>"));

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Toast.makeText(RegisterActivity.this,"Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }


    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        String mobileRegex = "[0-9]{10}";
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        final String fullName = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String phoneNumber = editTextPhone.getText().toString().trim();

        if (fullName.length() == 0) {
            editTextName.requestFocus();
            editTextName.setError("Full name is required");
            return false;
        }else if (phoneNumber.length() == 0) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone number is required");
            return false;
        }else if(editTextPhone.getText().toString().length() != 10){
            editTextPhone.requestFocus();
            editTextPhone.setError("Invalid phone number");
            return false;
        }else if (!mobilePattern.matcher(editTextPhone.getText().toString()).find()){
            editTextPhone.requestFocus();
            editTextPhone.setError("Invalid phone number");
            return false;
        }else if (editTextEmail.length() == 0 ) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email is required");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()){
            editTextEmail.requestFocus();
            editTextEmail.setError("Invalid email");
            //Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editTextPassword.length() == 0) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Password is required");
            return false;
        } else if (password.length() < 8) {
            editTextPassword.requestFocus();
            editTextPassword.setError("Password must be minimum 8 characters");
            return false;
        }else if (retypePwd.length() == 0) {
            retypePwd.requestFocus();
            retypePwd.setError("Passwords must match");
            return false;
        } else if(!editTextPassword.getText().toString().equals(retypePwd.getText().toString())){
            retypePwd.requestFocus();
            retypePwd.setError("Passwords must match");
            return false;
        }

        //store the additional fields(signup fields) in firebase
        Map<String,Object> user = new HashMap<>();
        user.put("Name",fullName);
        user.put("Email",email);
        user.put("Password",password);
        user.put("Phone Number",phoneNumber);

        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterActivity.this,"User Registered successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        if(e instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(RegisterActivity.this, "User is already registered", Toast.LENGTH_LONG).show();
                        }else {
                            //display a failure message
                            Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // after all validation return true.
        return true;
    }
}