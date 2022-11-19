package com.example.mediassist.login;



import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.ClinicAdminDashboardFragment;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.dashboard.DoctorDashboardFragment;
import com.example.mediassist.dashboard.PatientDashboardFragment;
import com.example.mediassist.dashboard.SuperAdminDashboardFragment;
import com.example.mediassist.resetpassword.ForgotPasswordActivity;
import com.example.mediassist.signup.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


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
        firebaseFirestore=FirebaseFirestore.getInstance();

    }
    public void onClickSignInButton(View view) {

        String mail = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if(mail.toString().equals("")){
            Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }else if( pwd.equals("")){
            Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
            return;
        }
/*
        firebaseFirestore.collection("user").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        System.out.println("Login query is successfull ---------");

                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            System.out.println("Login snapshot is not empty");
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                String dbName = d.getString("Username");
                                System.out.println("dbName " +dbName);
                            }
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(LoginActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(LoginActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
        */


        CollectionReference allUsersRef = firebaseFirestore.collection("user");
        Query userNameQuery = allUsersRef.whereEqualTo("username", mail);
        userNameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("Login query is successfull ---------");
                    System.out.println("Login detals" + task.getResult());
                    if (task.getResult().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                    }else{
                    for (DocumentSnapshot document : task.getResult()) {
                        System.out.println("Login document is successfull ---------" + document.getData());
                        if (document.exists()) {
                            String dbName = document.getString("username");
                            String dbPwd = document.getString("password");
                            String role = document.getString("role");
                            System.out.println("dbName value :" + dbName);
                            System.out.println("dbPwd value :" + dbPwd);
                            System.out.println("mail value :" + mail);
                            System.out.println("pwd value :" + pwd);
                            System.out.println("ROLE value :" + role);

                            if (dbName.equals(mail) && dbPwd.equals(pwd)) {
                                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                                dashboard.putExtra("role", role);
                                startActivity(dashboard);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                } else {
                    //Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(LoginActivity.this, "Login failed. Please try again", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
 /*
        firebaseFirestore.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String role = doc.getString("role");
                                System.out.println("role:"+ role);
                                System.out.println("name:"+ doc.getString("username"));


                                if(doc.getString("username").equals(mail) && doc.getString("password").equals(pwd)) {
                                    Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                                    dashboard.putExtra("Role ID", role);
                                    startActivity(dashboard);

                                }else
                                    Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });
                */

//        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pwd)) {
//            Toast.makeText(this, "Email and password is required", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
//        }
        /*
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);*/
    }

    public void onClickForgotPwdButton(View view) {
       Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}