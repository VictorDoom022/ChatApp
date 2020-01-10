package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText send_email;
    Button btn_reset;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);
        progressDialog = new ProgressDialog(this);

        firebaseAuth =FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Sending...");
                progressDialog.show();
               String email = send_email.getText().toString();

               if (email.equals("")){
                   progressDialog.dismiss();
                   Toast.makeText(ResetPasswordActivity.this,"All fields are required", Toast.LENGTH_LONG).show();

               }else{
                   firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){
                               progressDialog.dismiss();
                               Toast.makeText(ResetPasswordActivity.this,"Please check you email",Toast.LENGTH_LONG).show();
                               startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                           }else{
                               progressDialog.dismiss();
                               String error = task.getException().getMessage();
                               Toast.makeText(ResetPasswordActivity.this,error,Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }
            }
        });
    }
}
