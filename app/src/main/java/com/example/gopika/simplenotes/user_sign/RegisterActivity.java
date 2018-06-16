package com.example.gopika.simplenotes.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inName,inEmail,inPass;
    private FirebaseAuth fAuth;
    private DatabaseReference fUsersDataBase;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnReg = findViewById(R.id.button_reg);
        inName= findViewById(R.id.input_reg_name);
        inEmail= findViewById(R.id.input_reg_email);
        inPass= findViewById(R.id.input_reg_password);
        fAuth=FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fUsersDataBase= FirebaseDatabase.getInstance().getReference().child("Users");
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= Objects.requireNonNull(inName.getEditText()).getText().toString().trim();
                String umail= Objects.requireNonNull(inEmail.getEditText()).getText().toString().trim();
                String upass= Objects.requireNonNull(inPass.getEditText()).getText().toString().trim();
                registerUser(uname,umail,upass);
            }
        });

    }
    private void registerUser(final String name, String email, String password){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing your request,please wait...");
        progressDialog.show();
        fAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    fUsersDataBase.child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())
                            .child("basic").child("name").setValue(name)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent mainIntent=new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"ERROR :"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"ERROR :"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
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
