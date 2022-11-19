package com.example.mediassist.signup;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;
import com.example.mediassist.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

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
    DocumentReference ref;
    boolean isUserExist = false;

    public void setUserExist(boolean userExist) {
        isUserExist = userExist;
    }

    public boolean getUserExist() {
        return isUserExist;
    }


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
                registerUser();

            }
        });
    }


    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean registerUser() {
        String mobileRegex = "[0-9]{10}";
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        String fullName = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        boolean isUserExist;
        boolean isPhoneExist;

        if (fullName.length() == 0) {
            editTextName.requestFocus();
            editTextName.setError("Full name is required");
            return false;
        } else if (phoneNumber.length() == 0) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone number is required");
            return false;
        } else if (editTextPhone.getText().toString().length() != 10) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Invalid phone number");
            return false;
        } else if (!mobilePattern.matcher(editTextPhone.getText().toString()).find()) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Invalid phone number");
            return false;
        } else if (editTextEmail.length() == 0) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
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
        } else if (retypePwd.length() == 0) {
            retypePwd.requestFocus();
            retypePwd.setError("Passwords must match");
            return false;
        } else if (!editTextPassword.getText().toString().equals(retypePwd.getText().toString())) {
            retypePwd.requestFocus();
            retypePwd.setError("Passwords must match");
            return false;
        } else {

            CollectionReference allUsersRef = db.collection("user");
            Query userNameQuery = allUsersRef.whereEqualTo("username", email);
            userNameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                String userName = document.getString("username");
                                if (userName.equals(email)) {
                                    setUserExist(true);
                                    System.out.println(userName + "already exists");
                                    Toast.makeText(RegisterActivity.this, "Email Already exists, please use a different email id", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "username already exists");
                                    return;
                                } else {
                                    System.out.println("Adding user details into database");
                                    //store the additional fields(signup fields) in firebase
                                    Map<String, String> user = new HashMap<>();
                                    user.put("username", email);
                                    user.put("password", password);
                                    user.put("phone", phoneNumber);
                                    user.put("role", "4");

                                    db.collection("user")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(RegisterActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    startActivity(i);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NotNull Exception e) {
                                                    if (e instanceof FirebaseAuthUserCollisionException) {
                                                        Toast.makeText(RegisterActivity.this, "User is already registered", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        //display a failure message
                                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            });

            /*
                Query userPhoneQuery = allUsersRef.whereEqualTo("phone", phoneNumber);
                userPhoneQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    String phone = document.getString("phone");
                                    if (phone.equals(phoneNumber)) {
                                        setUserExist(true);
                                        System.out.println(phone + "already exists");
                                        Toast.makeText(RegisterActivity.this, "Phone number Already exists, please use a different phone number", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Phone number already exists");
                                        return;
                                    }
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }});
                    */


             /*
            System.out.println("getUserExist : "+getUserExist());
            if(!getUserExist()) {
                System.out.println("Adding user details into database");
                //store the additional fields(signup fields) in firebase
                Map<String, String> user = new HashMap<>();
                user.put("username", email);
                user.put("password", password);
                user.put("phone", phoneNumber);
                user.put("role", "4");

                db.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegisterActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NotNull Exception e) {
                                if (e instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(RegisterActivity.this, "User is already registered", Toast.LENGTH_LONG).show();
                                } else {
                                    //display a failure message
                                    Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }*/

            /*
                Query userPhoneQuery = allUsersRef.whereEqualTo("phone", phoneNumber);
                userPhoneQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    String phone = document.getString("phone");
                                    if (phone.equals(phoneNumber)) {
                                        System.out.println(phone + "already exists");
                                        Toast.makeText(RegisterActivity.this, "Phone number Already exists, please use a different phone number", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Phone number already exists");
                                        return;
                                    }
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

            //store the additional fields(signup fields) in firebase
            Map<String, String> user = new HashMap<>();
            user.put("username", email);
            user.put("password", password);
            user.put("phone", phoneNumber);
            user.put("role", "4");

            db.collection("user")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( @NotNull Exception e) {
                            if (e instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "User is already registered", Toast.LENGTH_LONG).show();
                            } else {
                                //display a failure message
                                Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/

        }

        // after all validation return true.
        return true;
    }


}