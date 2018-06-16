package com.example.gopika.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        updateUI();
    }
    private void updateUI(){
    if(fAuth.getCurrentUser()!=null){
        Log.i("MainActivity","fAuth!=null");

    }else{
        Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
        Log.i("MainActivity","fAuth==null");
        }
        }
}
