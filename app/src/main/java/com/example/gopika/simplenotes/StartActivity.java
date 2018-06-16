package com.example.gopika.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gopika.simplenotes.user_sign.LoginActivity;
import com.example.gopika.simplenotes.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button btnReg = findViewById(R.id.start_reg_button);
        Button btnLog = findViewById(R.id.start_log_button);
        fAuth=FirebaseAuth.getInstance();
        updateUI();
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
    }
    private void Register(){
        Intent regIntent=new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(regIntent);

    }
    private void login(){
        Intent logIntent=new Intent(StartActivity.this,LoginActivity.class);
        startActivity(logIntent);

    }
    private void updateUI(){
        if(fAuth.getCurrentUser()!=null){
            Log.i("StartActivity","fAuth!=null");
            Intent startIntent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(startIntent);
            finish();

        }else{

            Log.i("StartActivity","fAuth==null");


        }


    }
}
