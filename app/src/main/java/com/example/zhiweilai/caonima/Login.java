package com.example.zhiweilai.caonima;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin,bLogout;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;

    //progress dialog
    ProgressDialog progressDialog;
    // firebase auth object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not return null
        if(firebaseAuth.getCurrentUser()!=null){
            //means the user has already logged in
            finish();
            //open another activity
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogout =(Button) findViewById(R.id.bLogout);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        progressDialog = new ProgressDialog(this);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    private void userLogin(){
        String userName = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //checking if them emtpy
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Please enter your user name.",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show();
            return;
        }
        // username and password are not empty, display progress dialog
        progressDialog.setMessage("Logging...");
        progressDialog.show();

        //remind wrong information





        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(userName,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Profile.class));
                        }
                    }
                });
    }

    private void showError(){
        etPassword.setError("Password and email didn't match");
    }


    @Override
    public void onClick(View view) {
        if(view == bLogin){
            userLogin();
        }
        if(view == tvRegisterLink){
            finish();
            startActivity(new Intent(this,Register.class));
        }
    }
}
