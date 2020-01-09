package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email,password;
    Button btn_regiser;
    private FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_regiser = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernametxt = username.getText().toString().trim();
                String emailtxt =  email.getText().toString().trim();
                String passwordtxt = password.getText().toString().trim();

                if (TextUtils.isEmpty(usernametxt)||TextUtils.isEmpty(emailtxt)|| TextUtils.isEmpty(passwordtxt)){
                    Toast.makeText(RegisterActivity.this,"Please fill in all shits",Toast.LENGTH_LONG).show();
                }else if (emailtxt.length()<6){
                    Toast.makeText(RegisterActivity.this,"The password must not be less than 6 characters",Toast.LENGTH_LONG).show();
                }else{
                    register(usernametxt,emailtxt,passwordtxt);
                }
            }
        });
    }

    private void register(final String username, String email, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser !=null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",username);
                    hashMap.put("imageURL","default");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"You can't register with this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
