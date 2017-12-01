package com.example.zhiweilai.caonima;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.String;


public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister,returnLogin;
    EditText etEmail, etRole, etUsername, etPassword;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etRole = (EditText) findViewById(R.id.etRole);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        returnLogin = (Button) findViewById(R.id.returnLogin);

        bRegister.setOnClickListener(this);
        returnLogin.setOnClickListener(this);
    }

    private void registerUser(){
        String email=etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
//            email is empty
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
//            stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
//            password is empty
            Toast.makeText(this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            return;
        }

//        progressDialog.setMessage("Registering...");
//        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            user is successfully registered and log in
//                            start the profile activity here
                            Toast.makeText(Register.this,"Registered successfully! \n Log again",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Register.this,"Try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == bRegister) {
            registerUser();
        }
        if(view == returnLogin){
            startActivity(new Intent(this,Login.class));
        }
    }
}
