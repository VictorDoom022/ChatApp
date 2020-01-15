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

import com.example.chatapp.Fragments.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ResetUsernameActivity extends AppCompatActivity {

    EditText username_text;
    Button change_username;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_username);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Username");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username_text = findViewById(R.id.usernameTxt);
        change_username = findViewById(R.id.btn_change);

        change_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = username_text.getText().toString();

                if (TextUtils.isEmpty(newUsername)){
                    Toast.makeText(ResetUsernameActivity.this,"Please enter a new username",Toast.LENGTH_LONG);
                }else{
                    changeUsername(newUsername);
                }
            }
        });
    }

    private void changeUsername(String newUsername) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",newUsername);
        map.put("search",newUsername.toLowerCase());
        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetUsernameActivity.this,"Success!",Toast.LENGTH_LONG);
                    startActivity(new Intent(ResetUsernameActivity.this, ProfileFragment.class));
                }else{
                    Toast.makeText(ResetUsernameActivity.this,"Failed To Change Username",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
