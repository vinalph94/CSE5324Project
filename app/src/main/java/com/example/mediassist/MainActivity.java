package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    EditText fullName, phoneNumber, email, password, retypePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullName = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        retypePwd = findViewById(R.id.retypePwd);

        fullName.setHint(Html.fromHtml("Full Name <font color =\"#cc0029\" >*</font>"));
        phoneNumber.setHint(Html.fromHtml("Phone number <font color =\"#cc0029\" >*</font>"));
        email.setHint(Html.fromHtml("Email <font color =\"#cc0029\" >*</font>"));
        password.setHint(Html.fromHtml("Password <font color =\"#cc0029\" >*</font>"));
        retypePwd.setHint(Html.fromHtml("Retype Password <font color =\"#cc0029\" >*</font>"));

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        //signUpBtn.setBackgroundColor(Color.GREEN);
        
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Toast.makeText(MainActivity.this,"Registered Successfully", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(MainActivity.this, MainActivity2.class);
                   // startActivity(i);
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

        if (fullName.length() == 0) {
            fullName.requestFocus();
            fullName.setError("This field is required");
            return false;
        }else if (phoneNumber.length() == 0) {
            phoneNumber.requestFocus();
            phoneNumber.setError("This field is required");
            return false;
        }else if(phoneNumber.getText().toString().length() != 10){
            phoneNumber.requestFocus();
            phoneNumber.setError("Please enter 10 digit phone number");
            return false;
        }else if (!mobilePattern.matcher(phoneNumber.getText().toString()).find()){
            phoneNumber.requestFocus();
            phoneNumber.setError("Enter valid phone number");
            Toast.makeText(MainActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }else if (email.length() == 0 ) {
            email.requestFocus();
            email.setError("Email is required");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.requestFocus();
            email.setError("Enter valid Email address");
            //Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() == 0) {
            password.requestFocus();
            password.setError("Password is required");
            return false;
        } else if (password.length() < 8) {
            password.requestFocus();
            password.setError("Password must be minimum 8 characters");
            return false;
        }else if (retypePwd.length() == 0) {
            retypePwd.requestFocus();
            retypePwd.setError("Password is required");
            return false;
        } else if(!password.getText().toString().equals(retypePwd.getText().toString())){
            retypePwd.requestFocus();
            retypePwd.setError("Passwords do not match");
            Toast.makeText(this, " Passwords do not match !", Toast.LENGTH_SHORT).show();
            return false;
        }

        // after all validation return true.
        return true;
    }
}