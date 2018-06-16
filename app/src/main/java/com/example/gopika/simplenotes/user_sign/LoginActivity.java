package com.example.gopika.simplenotes.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gopika.simplenotes.MainActivity;
import com.example.gopika.simplenotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout inputEmail,inputPass;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail= findViewById(R.id.input_log_email);
        inputPass= findViewById(R.id.input_log_pass);
        Button but_Log = findViewById(R.id.btn_log);
        fAuth=FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        but_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lEmail= Objects.requireNonNull(inputEmail.getEditText()).getText().toString().trim();
                String lPass= Objects.requireNonNull(inputPass.getEditText()).getText().toString().trim();
                if(!TextUtils.isEmpty(lEmail) && !TextUtils.isEmpty(lPass)){
                    logIn(lEmail,lPass);
                }

            }
        });
    }
    private void logIn(String email,String password){
      final ProgressDialog progressDialog=new ProgressDialog(this);
      progressDialog.setMessage("Logging in, please wait...");
      progressDialog.show();
      fAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              progressDialog.dismiss();
              if(task.isSuccessful()){
                  Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                  startActivity(mainIntent);
                    finish();
                  Toast.makeText(LoginActivity.this,"Signing Success",Toast.LENGTH_SHORT).show();
                  }
              else{
                  Toast.makeText(LoginActivity.this,"ERROR"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
              }
          }
      });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return  true;
    }
}
